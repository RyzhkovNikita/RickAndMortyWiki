package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.UrlTransformer
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.LocationPlain
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocationRepo(private val apiService: ApiService) : LocationDataSource {
    override suspend fun getLocation(id: Long): RepoRequest<LocationPlain> = withContext(Dispatchers.IO){
        try {
            val response = apiService.getLocation(id).execute()
            if (response.isSuccessful)
                SuccessfulRequest(response.body()!!, SuccessfulRequest.FROM_NET)
            else
                FailedRequest<LocationPlain>()
        } catch (e: Exception){
            FailedRequest<LocationPlain>()
        }
    }

    override suspend fun getLocation(url: String): RepoRequest<LocationPlain> = withContext(Dispatchers.IO) {
        val id = UrlTransformer.extractIdFromLocationUrl(url)
        getLocation(id)
    }
}