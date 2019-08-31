package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSourceInternal
import com.aura.project.rickandmortywiki.data.repository.char_repo.DefaultRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class RepoFactoryImpl(val apiService: ApiService, val charDao: CharDao) : RepoFactory {
    override fun createLocalRepo(): CharacterDataSourceInternal = CharLocalRepo(charDao)
    override fun createNetRepo(): CharacterDataSourceInternal = CharNetRepo(apiService)
    override fun createDefaultRepo(): CharacterDataSourceInternal = DefaultRepo(this)
    override fun createNameFilter(name: String): CharacterDataSourceInternal = NameCharFilter(name, this)
    override fun createStatusFilter(status: String): CharacterDataSourceInternal = StatusCharFilter(status, this)
}