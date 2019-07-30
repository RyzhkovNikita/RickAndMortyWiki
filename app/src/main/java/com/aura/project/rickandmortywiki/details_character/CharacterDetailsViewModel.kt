package com.aura.project.rickandmortywiki.details_character

import android.app.Application
import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.*
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(var id: Long, app: Application) : AndroidViewModel(app) {

    private val _loading = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<Boolean>()
    private val _charLiveData = MutableLiveData<Character>()
    private val _episodes = MutableLiveData<List<Episode>>()
    private val _navDestination = MutableLiveData<Any>()
    private val _episodeRepo: EpisodeDataSource = EpisodeRepo(ApiService.getInstance())
    private val _charRepo: CharacterDataSource = CharRepo(
        CharNetRepo(
            ApiService.getInstance()
        ),
        CharLocalRepo(
            AppDatabase.getInstance(getApplication()).charDao()
        )
    )

    val loading: LiveData<Boolean>
        get() = _loading

    val error: LiveData<Boolean>
        get() = _error

    val navDestination: LiveData<Any>
        get() = _navDestination

    val name: LiveData<String> = Transformations.map(_charLiveData)
    { it.name }

    val gender: LiveData<String> = Transformations.map(_charLiveData)
    { it.gender }

    val location: LiveData<String> = Transformations.map(_charLiveData)
    { it.location.name }

    val episodes: LiveData<List<Episode>>
        get() = _episodes

    val image: LiveData<String> = Transformations.map(_charLiveData)
    { it.image }

    val origin: LiveData<String> = Transformations.map(_charLiveData)
    { it.origin.name }

    val isDead: LiveData<Boolean> = Transformations.map(_charLiveData)
    { it.status == "Dead" }

    init {
        loadCharacter()
    }

    private fun loadCharacter() = viewModelScope.launch {
        _loading.value = true
        _error.value = false
        when (val request = _charRepo.getChar(id)){
            is SuccessfulRequest -> {
                _charLiveData.value = request.body
                loadEpisodes(request.body.episode)
            }
            is FailedRequest -> {
                _error.value = true
            }
        }
        _loading.value = false
    }

    private fun loadEpisodes(episodeUrls: List<String>) = viewModelScope.launch {
        val episodes = _episodeRepo.getEpisodesFromUrls(episodeUrls)
        if (episodes is SuccessfulRequest)
            _episodes.value = episodes.body
    }

    fun originClicked() {
        _navDestination.value = _charLiveData.value!!.origin
    }

    fun locationClicked() {
        _navDestination.value = _charLiveData.value!!.location
    }

    fun errorButtonClicked() {
        loadCharacter()
    }

    fun episodeClickAt(position: Int) {
        _navDestination.value = _episodes.value!![position]
    }

    fun navigated() {
        _navDestination.value = null
    }

    class Factory(val id: Long, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java))
                return CharacterDetailsViewModel(id, app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
