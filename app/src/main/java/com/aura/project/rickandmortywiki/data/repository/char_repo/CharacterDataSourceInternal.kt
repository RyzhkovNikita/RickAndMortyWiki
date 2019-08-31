package com.aura.project.rickandmortywiki.data.repository.char_repo

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.RepoRequest

interface CharacterDataSourceInternal {
    suspend fun getCharPage(page: Int): RepoRequest<List<Character>>
    suspend fun insertChars(chars: List<Character>, page: Int)
    suspend fun clearAll()
    suspend fun getChar(id: Long): RepoRequest<Character>
    suspend fun getChars(ids: LongArray): RepoRequest<List<Character>>
    suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>>
}