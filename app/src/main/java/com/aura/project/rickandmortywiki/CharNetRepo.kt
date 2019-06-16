package com.aura.project.rickandmortywiki

class CharNetRepo(private val charApi: ApiService) : CharacterDataSource {
    override suspend fun getCharPage(page: Int): RepoResult<List<Character>> {
        val response = charApi.getCharPageCall().execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!.characters)
        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>) {
    }

    override suspend fun clearAll() {
    }

    override suspend fun getChars(ids: List<Int>): RepoResult<List<Character>> {
        val charList = ids.map { id ->
            when (val result = getChar(id)) {
                is SuccessfulRequest -> return@map result.body
                else -> return FailedRequest()
            }
        }
        return SuccessfulRequest(charList)
    }


    override suspend fun getChar(id: Int): RepoResult<Character> {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!)
        return FailedRequest()
    }
}