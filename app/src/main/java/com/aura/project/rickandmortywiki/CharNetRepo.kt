package com.aura.project.rickandmortywiki

class CharNetRepo(private val charApi: ApiService) : CharacterDataSource {
    override fun getCharPage(page: Int): List<Character>? {
        val response = charApi.getCharPageCall().execute()
        if (response.isSuccessful)
            return response.body()?.characters
        return null
    }

    override fun insertChars(chars: List<Character>) {
    }

    override fun clearAll() {
    }

    override fun getChar(id: Int): Character? {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return response.body()
        return null
    }
}