package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class RepoFactory(private val apiService: ApiService, private val charDao: CharDao) {
    fun createLocalRepo(): CharLocalRepo = CharLocalRepo(charDao)
    fun createNetRepo(): CharNetRepo = CharNetRepo(apiService)
    fun createDefaultRepo(): DefaultRepo {
        return DefaultRepo(apiService, charDao)
    }

    fun createNameFilter(name: String): NameCharFilter {
        return NameCharFilter(name, apiService, charDao)
    }

    fun createStatusFilter(status: String): StatusCharFilter {
        return StatusCharFilter(status, apiService, charDao)
    }
}