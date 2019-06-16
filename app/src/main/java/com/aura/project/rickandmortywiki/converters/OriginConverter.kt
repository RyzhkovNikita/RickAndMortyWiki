package com.aura.project.rickandmortywiki.converters

import androidx.room.TypeConverter
import com.aura.project.rickandmortywiki.data.Origin

class OriginConverter {
    private val separator = ";:;"
    @TypeConverter
    fun fromOrigin(origin: Origin) = "${origin.name}$separator${origin.url}"

    @TypeConverter
    fun toOrigin(originString: String): Origin {
        val params = originString.split(separator)
        return Origin(params[0], params[1])
    }
}