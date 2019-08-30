package com.aura.project.rickandmortywiki.data.repository

import android.util.SparseArray
import com.aura.project.rickandmortywiki.UrlTransformer
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.filters.CharacterFilter
import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.NoFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.retrofit.ApiService

class CharNetRepo(private val charApi: ApiService) : CharacterDataSource {

    override var strategy: CharacterFilter = NoFilter

    private val _cache = SparseArray<List<Character>>()

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> =
        when (val strategyOnStart = strategy) {
            is NoFilter -> noFilterRequest(page)
            is NameCharFilter -> nameFilteredPageRequest(page, strategyOnStart.name)
            is StatusCharFilter -> statusFilteredPageRequest(page, strategyOnStart.status)
        }

    override suspend fun insertChars(chars: List<Character>) {
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

    private fun noFilterRequest(page: Int): RepoRequest<List<Character>> {
        val charPage: List<Character>? = _cache.get(page)
        if (charPage != null)
            return SuccessfulRequest(body = charPage, source = SuccessfulRequest.FROM_NET)

        val response = charApi.getCharPage(page).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )

        return FailedRequest()
    }

    private fun nameFilteredPageRequest(page: Int, name: String): RepoRequest<List<Character>> {
        //TODO: create fun in api to get the page and change THIS method
        val response = charApi.getCharPage(page).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )
        return FailedRequest()
    }

    private fun statusFilteredPageRequest(page: Int, status: String): RepoRequest<List<Character>> {
        //TODO: create fun in api to get the page and change THIS method
        val response = charApi.getCharPage(page).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )
        return FailedRequest()
    }
}