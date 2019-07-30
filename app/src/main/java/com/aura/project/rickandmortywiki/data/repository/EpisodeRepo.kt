package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.UrlTransformer
import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepo(private val apiService: ApiService) : EpisodeDataSource {
    override suspend fun getEpisodesFromUrls(urls: List<String>): RepoRequest<List<Episode>> =
        withContext(Dispatchers.IO) {
            val requestPath = UrlTransformer.extractEpisodeRequestPath(urls)
            try {
                if (urls.size > 1) {
                    val response = apiService.getEpisodes(requestPath).execute()
                    if (response.isSuccessful)
                        return@withContext SuccessfulRequest(response.body()!!, SuccessfulRequest.FROM_NET)
                } else {
                    val response = apiService.getEpisode(requestPath.toLong()).execute()
                    if (response.isSuccessful)
                        return@withContext SuccessfulRequest(listOf(response.body()!!), SuccessfulRequest.FROM_NET)
                }
            } catch (e: Exception) {
                return@withContext FailedRequest<List<Episode>>()
            }
            return@withContext FailedRequest<List<Episode>>()
        }

    override suspend fun getEpisodeById(id: Long): RepoRequest<Episode> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getEpisode(id).execute()
            if (response.isSuccessful)
                return@withContext SuccessfulRequest(response.body()!!, SuccessfulRequest.FROM_NET)
            else
                return@withContext FailedRequest<Episode>()
        } catch (e: Exception) {
            return@withContext FailedRequest<Episode>()
        }
    }
}