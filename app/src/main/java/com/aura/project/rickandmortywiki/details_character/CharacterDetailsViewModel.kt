package com.aura.project.rickandmortywiki.details_character

import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.EpisodeRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(var character: Character) : ViewModel() {

    init {
        loadEpisodes(character.episode)
    }

    private val _charLiveData = MutableLiveData<Character>().apply { value = character }
    private val _episodes = MutableLiveData<List<Episode>>()
    private val _episodeRepo = EpisodeRepo(ApiService.getInstance())

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


    private fun loadEpisodes(episodeUrls: List<String>) = viewModelScope.launch {
        val episodes = _episodeRepo.getEpisodesFromUrls(episodeUrls)
        if (episodes is SuccessfulRequest)
            _episodes.value = episodes.body
    }

    fun originClicked() {

    }

    fun locationClicked() {

    }

    class Factory(val character: Character) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java))
                return CharacterDetailsViewModel(character) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
