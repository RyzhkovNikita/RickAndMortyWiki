package com.aura.project.rickandmortywiki

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aura.project.rickandmortywiki.data.Character
import java.lang.IllegalArgumentException

class CharacterDetailsViewModelFactory(val character: Character): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterDetailsViewModel::class.java))
            return CharacterDetailsViewModel() as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}