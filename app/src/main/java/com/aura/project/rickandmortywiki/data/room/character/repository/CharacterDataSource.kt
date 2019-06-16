package com.aura.project.rickandmortywiki.data.room.character.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.request.RepoRequest

interface CharacterDataSource {
    fun getCharPage(page: Int): RepoRequest<List<Character>>
    fun insertChars(chars: List<Character>)
    fun clearAll()
    fun getChar(id: Int): RepoRequest<Character>
    fun getChars(ids: List<Int>): RepoRequest<List<Character>>
}