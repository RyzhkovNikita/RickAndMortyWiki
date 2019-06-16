package com.aura.project.rickandmortywiki

interface CharacterDataSource {
    fun getCharPage(page: Int): RepoResult<List<Character>>
    fun insertChars(chars: List<Character>)
    fun clearAll()
    fun getChar(id: Int): RepoResult<Character>
    fun getChars(ids: List<Int>): RepoResult<List<Character>>
}