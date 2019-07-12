package com.aura.project.rickandmortywiki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel;
import com.aura.project.rickandmortywiki.data.Character

class CharacterDetailsViewModel : ViewModel() {

    private val _charLiveData = MutableLiveData<Character>()

    val name: LiveData<String> = Transformations.map(_charLiveData)
    { it.name }

    val gender: LiveData<String> = Transformations.map(_charLiveData)
    { it.gender }

    val location: LiveData<String> = Transformations.map(_charLiveData)
    { it.location.name }

    val episodes: LiveData<List<String>> = Transformations.map(_charLiveData)
    { it.episode }

    val image: LiveData<String> = Transformations.map(_charLiveData)
    { it.image }

    val origin: LiveData<String> = Transformations.map(_charLiveData)
    { it.origin.name }

    val isDead: LiveData<Boolean> = Transformations.map(_charLiveData)
    { it.status == "Dead" }

    infix fun setupWith(character: Character) {
        _charLiveData.value = character
    }
}
