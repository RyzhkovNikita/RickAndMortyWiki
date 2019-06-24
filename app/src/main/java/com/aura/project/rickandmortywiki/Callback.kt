package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Character

interface Callback {
    fun onCharacterCardClicked(character: Character)
}