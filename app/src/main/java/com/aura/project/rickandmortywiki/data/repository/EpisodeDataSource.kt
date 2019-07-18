package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Episode
import com.aura.project.rickandmortywiki.data.RepoRequest

interface EpisodeDataSource {
    suspend fun getEpisodesFromUrls(urls: List<String>): RepoRequest<List<Episode>>
}