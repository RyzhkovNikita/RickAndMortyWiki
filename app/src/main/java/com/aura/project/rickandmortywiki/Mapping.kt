package com.aura.project.rickandmortywiki

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.main_characters.CharToShowItem

fun List<Character>.toCharToShowList(): List<CharToShowItem> {
    return this.map { CharToShowItem(it.id, it.name, it.image) }
}