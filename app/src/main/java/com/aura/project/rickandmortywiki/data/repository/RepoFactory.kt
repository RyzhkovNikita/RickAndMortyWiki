package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSourceInternal
import com.aura.project.rickandmortywiki.data.repository.char_repo.DefaultRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class RepoFactory(val apiService: ApiService, val charDao: CharDao) {
    fun createLocalRepo(): CharacterDataSourceInternal = CharLocalRepo(charDao)
    fun createNetRepo(): CharacterDataSourceInternal = CharNetRepo(apiService)
    fun createDefaultRepo(): DefaultRepo {
        return DefaultRepo(this)
    }
    fun createNameFilter(name: String): NameCharFilter {
        return NameCharFilter(name, this)
    }
    fun createStatusFilter(status: String): StatusCharFilter {
        return StatusCharFilter(status, this)
    }
}