package com.aura.project.rickandmortywiki.details_location

import android.app.Application
import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.LocationPlain
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.*
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import com.aura.project.rickandmortywiki.main_characters.ListItem
import com.aura.project.rickandmortywiki.toCharToShowList
import kotlinx.coroutines.launch

class LocationViewModel(val id: Long, app: Application) : AndroidViewModel(app) {

    private val _charRepo: CharacterDataSource = MainCharRepo(
        ApiService.getInstance(),
        AppDatabase.getInstance(getApplication()).charDao()
    )

    private val _locationRepo: LocationDataSource = LocationRepo(ApiService.getInstance())

    private val _location = MutableLiveData<LocationPlain>()
    private val _chars = MutableLiveData<List<Character>>()

    val location: LiveData<LocationPlain>
        get() = _location

    val chars: LiveData<List<ListItem>> = Transformations.map(_chars) { list ->
        return@map list.toCharToShowList()
    }


    private var currentLocation: LocationPlain? = null
        set(value) {
            value?.let {
                _location.value = value
                field = value
                loadResidents()
            }
        }

    init {
        loadEpisode()
    }

    private fun loadEpisode() = viewModelScope.launch {
        val request = _locationRepo.getLocation(id)
        if (request is SuccessfulRequest) {
            currentLocation = request.body
        } //else TODO: show error
    }

    private fun loadResidents() = viewModelScope.launch {
        val location = currentLocation!!
        val request = _charRepo.getCharsFromUrl(location.residents)
        if (request is SuccessfulRequest)
            _chars.value = request.body
    }


    class Factory(val id: Long, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LocationViewModel::class.java))
                return LocationViewModel(id, app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
