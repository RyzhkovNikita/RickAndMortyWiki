package com.aura.project.rickandmortywiki.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CharacterPage(
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)

@Entity
data class Character(
    var created: String,
    var episode: List<String>,
    var gender: String,
    @PrimaryKey val id: Long,
    var image: String,
    var location: Location,
    var name: String,
    var origin: Origin,
    var species: String,
    var status: String,
    var type: String,
    var url: String
)

data class Origin(
    val name: String,
    val url: String
)

data class Location(
    val name: String,
    val url: String
)

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String
)

data class Episode(
    val id: Long,
    @SerializedName("name")
    val title: String,
    @SerializedName("air_date")
    val date: String,
    @SerializedName("episode")
    val seasonAndNum: String,
    @SerializedName("characters")
    val characterUrls: List<String>,
    val url: String,
    val created: String
)
