package com.aura.project.rickandmortywiki

interface CharacterDataSource {
    suspend fun getCharPage(page: Int): RepoResult<List<Character>>
    suspend fun insertChars(chars: List<Character>)
    suspend fun clearAll()
    suspend fun getChar(id: Int): RepoResult<Character>
    suspend fun getChars(ids: List<Int>): RepoResult<List<Character>>
}