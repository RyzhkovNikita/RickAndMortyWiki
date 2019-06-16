package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.request.FailedRequest
import com.aura.project.rickandmortywiki.data.request.RepoRequest
import com.aura.project.rickandmortywiki.data.request.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService

class CharNetRepo(private val charApi: ApiService) :
    CharacterDataSource {
    override fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val response = charApi.getCharPageCall().execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!.characters)
        return FailedRequest()
    }

    override fun insertChars(chars: List<Character>) {
    }

    override fun clearAll() {
    }

    override fun getChars(ids: List<Int>): RepoRequest<List<Character>> {
        val charList = ids.map { id ->
            when (val result = getChar(id)) {
                is SuccessfulRequest -> return@map result.body
                else -> return FailedRequest()
            }
        }
        return SuccessfulRequest(charList)
    }


    override fun getChar(id: Int): RepoRequest<Character> {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!)
        return FailedRequest()
    }
}