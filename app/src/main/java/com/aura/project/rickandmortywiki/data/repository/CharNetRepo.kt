package com.aura.project.rickandmortywiki.data.repository

import android.util.SparseArray
import com.aura.project.rickandmortywiki.UrlTransformer
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService

class CharNetRepo(private val charApi: ApiService) : CharacterDataSourceInternal {

    private val _cache = SparseArray<List<Character>>()

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val response = charApi.getCharPage(page).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )
        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>, page: Int) {
        _cache.put(page, chars)
    }

    override suspend fun clearAll() {}

    override suspend fun getChars(ids: LongArray): RepoRequest<List<Character>> {
        val requestPath = UrlTransformer.getRequestPath(ids)
        val response = charApi.getCharsById(requestPath).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(body = response.body()!!, source = SuccessfulRequest.FROM_NET)
        return FailedRequest()
    }


    override suspend fun getChar(id: Long): RepoRequest<Character> {
        val response = charApi.getCharById(id).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(body = response.body()!!, source = SuccessfulRequest.FROM_NET)
        return FailedRequest()
    }

    override suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>> {
        val idArray = UrlTransformer.urlsToIdArray(ids)
        return getChars(idArray)
    }
}