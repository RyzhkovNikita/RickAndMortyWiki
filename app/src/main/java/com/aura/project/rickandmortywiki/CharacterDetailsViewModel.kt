package com.aura.project.rickandmortywiki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.aura.project.rickandmortywiki.data.Character

class CharacterDetailsViewModel : ViewModel() {

    private lateinit var character: Character

    private val _nameLiveData = MutableLiveData<String>()
    private val _isDeadLiveData = MutableLiveData<Boolean>()
    private val _locationLiveData = MutableLiveData<String>()
    private val _originLiveData = MutableLiveData<String>()
    private val _imageLiveData = MutableLiveData<String>()
    private val _genderLiveData = MutableLiveData<String>()
    private val _episodesLiveData = MutableLiveData<List<String>>()
    val name: LiveData<String>
        get() = _nameLiveData
    val gender: LiveData<String>
        get() = _genderLiveData
    val location: LiveData<String>
        get() = _locationLiveData
    val episodes: LiveData<List<String>>
        get() = _episodesLiveData
    val image: LiveData<String>
        get() = _imageLiveData
    val origin: LiveData<String>
        get() = _originLiveData
    val isDead: LiveData<Boolean>
        get() = _isDeadLiveData

    infix fun setupWith(character: Character) {
        this.character = character
        setValuesByCharacter()
    }

    private fun setValuesByCharacter() {
        _nameLiveData.value = character.name
        _isDeadLiveData.value = character.status == "Dead"
        _locationLiveData.value = character.location.name
        _originLiveData.value = character.origin.name
        _imageLiveData.value = character.image
        _genderLiveData.value = character.gender
    }

    fun locationClicked(){
        TODO("implement")
    }

    fun originClicked(){
        TODO("implement")
    }

}
