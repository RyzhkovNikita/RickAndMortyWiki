package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.filters.CharacterFilter

interface CharacterDataSource {
    var strategy: CharacterFilter
    suspend fun getCharPage(page: Int): RepoRequest<List<Character>>
    suspend fun insertChars(chars: List<Character>)
    suspend fun clearAll()
    suspend fun getChar(id: Long): RepoRequest<Character>
    suspend fun getChars(ids: LongArray): RepoRequest<List<Character>>
    suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>>
}