package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepo(private val apiService: ApiService) : EpisodeDataSource {
    private val scheme = "https://rickandmortyapi.com/api/episode/"
    override suspend fun getEpisodesFromUrls(urls: List<String>): RepoRequest<List<Episode>> =
        withContext(Dispatchers.IO) {
            val episodeIds = urls
                .map { url -> url.substringAfter(scheme, "") }
                .filter { idString -> idString.isNotEmpty() }
                .reduce{acc, id -> "$acc, $id"}
            val response = apiService.getEpisodes(episodeIds).execute()
            if (response.isSuccessful)
                SuccessfulRequest(response.body()!!, SuccessfulRequest.FROM_NET)
            else
                FailedRequest<List<Episode>>()
        }
}