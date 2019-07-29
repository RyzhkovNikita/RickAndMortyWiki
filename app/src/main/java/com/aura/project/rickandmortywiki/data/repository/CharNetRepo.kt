package com.aura.project.rickandmortywiki.data.repository

import android.util.SparseArray
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService

class CharNetRepo(private val charApi: ApiService) : CharacterDataSource {

    private val _cache = SparseArray<List<Character>>()

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {

        val charPage: List<Character>? = _cache.get(page)
        if (charPage != null)
            return SuccessfulRequest(body = charPage, source = SuccessfulRequest.FROM_NET)

        val response = charApi.getCharPage(page).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(body = response.body()!!.characters, source = SuccessfulRequest.FROM_NET)

        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>) {
    }

    override suspend fun clearAll() {}

    override suspend fun getChars(ids: IntArray): RepoRequest<List<Character>> {
        val requestPath = ids.joinToString(separator = ",", transform = { it.toString() })
        val response = charApi.getCharsById(requestPath).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(body = response.body()!!, source = SuccessfulRequest.FROM_NET)
        return FailedRequest()
    }


    override suspend fun getChar(id: Int): RepoRequest<Character> {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(body = response.body()!!, source = SuccessfulRequest.FROM_NET)
        return FailedRequest()
    }
}