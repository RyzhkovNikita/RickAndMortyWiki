package com.aura.project.rickandmortywiki

import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.EpisodeRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.launch

class CharacterDetailsViewModel : ViewModel() {

    private val _charLiveData = MutableLiveData<Character>()
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

    infix fun setupWith(character: Character) {
        _charLiveData.value = character
        loadEpisodes(character.episode)
    }

    private fun loadEpisodes(episodeUrls: List<String>) = viewModelScope.launch {
        val episodes = _episodeRepo.getEpisodesFromUrls(episodeUrls)
        if (episodes is SuccessfulRequest)
            _episodes.value = episodes.body
    }

    fun originClicked() {

    }

    fun locationClicked() {

    }
}
