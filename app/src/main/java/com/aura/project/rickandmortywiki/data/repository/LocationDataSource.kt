package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.LocationPlain
import com.aura.project.rickandmortywiki.data.RepoRequest

interface LocationDataSource {
    suspend fun getLocation(id: Long): RepoRequest<LocationPlain>
    suspend fun getLocation(url: String): RepoRequest<LocationPlain>
}