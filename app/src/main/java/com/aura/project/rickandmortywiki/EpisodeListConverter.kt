package com.aura.project.rickandmortywiki

import androidx.room.TypeConverter

class EpisodeListConverter {
    private val separator = ","
    @TypeConverter
    fun fromEpisodes(episodeList: List<String>) = episodeList.reduce { acc, str -> acc.plus(separator + str) }

    @TypeConverter
    fun toEpisodes(episodeString: String): List<String> = episodeString.split(separator)
}