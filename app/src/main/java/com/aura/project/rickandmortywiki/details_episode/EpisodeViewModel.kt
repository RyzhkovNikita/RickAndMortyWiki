package com.aura.project.rickandmortywiki.details_episode

import android.app.Application
import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.*
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import kotlinx.coroutines.launch

class EpisodeViewModel(val id: Long, app: Application) : AndroidViewModel(app) {

    private val _charRepo: CharacterDataSource = CharRepo(
        CharNetRepo(
            ApiService.getInstance()
        ),
        CharLocalRepo(
            AppDatabase.getInstance(getApplication()).charDao()
        )
    )

    private val _episode = MutableLiveData<EpisodeModel>()
    private val _chars = MutableLiveData<List<Character>>()

    private var currentEpisode: Episode? = null
        set(value) {
            value?.let {
                _episode.value = value.asModel()
                field = value
                loadChars()
            }
        }

    val episode: LiveData<EpisodeModel>
        get() = _episode

    val chars: LiveData<List<Character>>
        get() = _chars

    private val _episodeRepo: EpisodeDataSource = EpisodeRepo(
        ApiService.getInstance()
    )

    init {
        loadEpisode()
    }

    private fun loadEpisode() = viewModelScope.launch {
        val request = _episodeRepo.getEpisodeById(id)
        if (request is SuccessfulRequest) {
            currentEpisode = request.body
        }//else TODO: show error
    }

    data class EpisodeModel(
        val title: String,
        val shortTitle: String,
        val date: String
    )

    private fun Episode.asModel(): EpisodeModel = EpisodeModel(title, seasonAndNum, date)

    private fun loadChars() = viewModelScope.launch {
        val episode = currentEpisode!!
        val request = _charRepo.getCharsFromUrl(episode.characterUrls)
        if (request is SuccessfulRequest)
            _chars.value = request.body
    }

    class Factory(val id: Long, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EpisodeViewModel::class.java))
                return EpisodeViewModel(id, app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
