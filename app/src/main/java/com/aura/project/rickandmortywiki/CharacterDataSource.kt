package com.aura.project.rickandmortywiki

interface CharacterDataSource {
    fun getCharPage(page: Int): List<Character>?
    fun insertChars(chars: List<Character>)
    fun clearAll()
    fun getChar(id: Int): Character?
}