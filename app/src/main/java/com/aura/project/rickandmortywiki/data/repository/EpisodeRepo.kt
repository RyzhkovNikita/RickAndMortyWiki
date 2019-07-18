package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EpisodeRepo(val apiService: ApiService) : EpisodeDataSource {
    private val scheme = "https://rickandmortyapi.com/api/episode/"
    override suspend fun getEpisodesFromUrls(urls: List<String>): RepoRequest<List<Episode>> =
        withContext(Dispatchers.IO) {
            val episodes = urls
                .asSequence()
                .map { url -> url.substringAfter(scheme, "") }
                .filter { idString -> idString.isNotEmpty() }
                .mapNotNull { it.toIntOrNull() }
                .mapNotNull { id -> apiService.getEpisode(id).execute().body() }
                .toList()
            SuccessfulRequest(episodes, SuccessfulRequest.FROM_NET)
        }
}