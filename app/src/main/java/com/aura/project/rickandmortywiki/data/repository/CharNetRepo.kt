package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.request.FailedRequest
import com.aura.project.rickandmortywiki.data.request.RepoRequest
import com.aura.project.rickandmortywiki.data.request.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService

class CharNetRepo(private val charApi: ApiService) : CharacterDataSource {

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val response = charApi.getCharPageCall().execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!.characters)
        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>) {}

    override suspend fun clearAll() {}

    override suspend fun getChars(ids: List<Int>): RepoRequest<List<Character>> {
        val requestPath = ids.fold("", { path, id -> path.plus(",$id") })
        val response = charApi.getCharsById(requestPath).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!)
        return FailedRequest()
    }


    override suspend fun getChar(id: Int): RepoRequest<Character> {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(response.body()!!)
        return FailedRequest()
    }
}