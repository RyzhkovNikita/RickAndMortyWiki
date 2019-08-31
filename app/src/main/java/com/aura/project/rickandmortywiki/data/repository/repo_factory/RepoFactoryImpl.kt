package com.aura.project.rickandmortywiki.data.repository.repo_factory

import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSourceInternal
import com.aura.project.rickandmortywiki.data.repository.char_repo.DefaultRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class RepoFactoryImpl(
    private val apiService: ApiService,
    private val charDao: CharDao
) :
    RepoFactory {
    override fun charApi(): ApiService = apiService
    override fun charDao(): CharDao = charDao
    override fun createLocalRepo(): CharacterDataSourceInternal = CharLocalRepo(charDao)
    override fun createNetRepo(): CharacterDataSourceInternal = CharNetRepo(apiService)
    override fun createDefaultRepo(): CharacterDataSourceInternal = DefaultRepo(this)
    override fun createNameFilter(name: String): CharacterDataSourceInternal = NameCharFilter(name, this)
    override fun createStatusFilter(status: String): CharacterDataSourceInternal = StatusCharFilter(status, this)
}