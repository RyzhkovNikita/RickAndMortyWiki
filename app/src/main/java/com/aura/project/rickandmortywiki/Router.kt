package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Location
import com.aura.project.rickandmortywiki.data.Origin

interface Router {
    fun openCharacter(character: Character)
    fun openLocation(location: Location)
    fun openOrigin(origin: Origin)
    fun openEpisode(episode: String)
}