package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.Location
import com.aura.project.rickandmortywiki.data.Origin

interface OnCharacterClickListener {
    fun onCharacterCardClick(character: Character)
}

interface OnLocationClickListener {
    fun onLocationClick(location: Location)
}

interface OnOriginClickListener {
    fun onOriginClick(origin: Origin)
}

interface OnEpisodeClickListener {
    fun onEpisodeClick(episode: String)
}