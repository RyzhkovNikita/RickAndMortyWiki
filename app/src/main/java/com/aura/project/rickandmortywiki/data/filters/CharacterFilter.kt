package com.aura.project.rickandmortywiki.data.filters

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.RepoFactoryImpl
import com.aura.project.rickandmortywiki.data.repository.char_repo.DefaultRepo


class NameCharFilter(
    val name: String,
    factory: RepoFactoryImpl
) :
    DefaultRepo(factory) {

    private val apiService = factory.apiService

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val response = apiService.getCharPageByName(page, name).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )
        return FailedRequest()
    }
}

class StatusCharFilter(
    private val status: String,
    factory: RepoFactoryImpl
) :
    DefaultRepo(factory) {

    private val apiService = factory.apiService

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val response = apiService.getCharPageByStatus(page, status).execute()
        if (response.isSuccessful)
            return SuccessfulRequest(
                body = response.body()!!.characters,
                source = SuccessfulRequest.FROM_NET
            )
        return FailedRequest()
    }
}
