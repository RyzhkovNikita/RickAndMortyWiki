package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Location
import com.aura.project.rickandmortywiki.data.Origin

interface Router {
    fun openCharacter(charId: Long)
    fun openLocation(location: Location)
    fun openOrigin(origin: Origin)
    fun openEpisode(episodeId: Long)
}