package com.aura.project.rickandmortywiki

interface Router {
    fun openCharacter(charId: Long)
    fun openLocation(locationId: Long)
    fun openEpisode(episodeId: Long)
}