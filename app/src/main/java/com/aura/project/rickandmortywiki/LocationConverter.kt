package com.aura.project.rickandmortywiki

import androidx.room.TypeConverter

class LocationConverter {
    private val separator = ";:;"
    @TypeConverter
    fun fromLocation(location: Location) = "${location.name}$separator${location.url}"

    @TypeConverter
    fun toLocation(locationString: String): Location {
        val params = locationString.split(separator)
        return Location(params[0], params[1])
    }
}