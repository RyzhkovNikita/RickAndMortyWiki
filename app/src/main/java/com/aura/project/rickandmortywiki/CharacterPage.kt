package com.aura.project.rickandmortywiki

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
    val created: String,
    val episode: List<String>,
    val gender: String,
    @PrimaryKey val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) {
    companion object {
        fun emptyChar(): Character {
            val emptyStr = ""
            val origin = Origin(emptyStr, emptyStr)
            val location = Location(emptyStr, emptyStr)
            return Character(
                emptyStr,
                emptyList(),
                emptyStr,
                -1,
                emptyStr,
                location,
                emptyStr,
                origin,
                emptyStr,
                emptyStr,
                emptyStr,
                emptyStr
            )
        }
    }
}

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