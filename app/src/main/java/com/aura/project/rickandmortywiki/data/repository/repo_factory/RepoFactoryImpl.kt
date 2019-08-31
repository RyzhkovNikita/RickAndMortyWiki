package com.aura.project.rickandmortywiki.data.repository.repo_factory

import com.aura.project.rickandmortywiki.data.repository.FilterParams
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSource
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
    override fun createLocalRepo(): CharacterDataSource = CharLocalRepo(charDao)
    override fun createNetRepo(filterParams: FilterParams): CharacterDataSource = CharNetRepo(filterParams, apiService)
    override fun createNetRepo(): CharacterDataSource = createNetRepo(FilterParams())
    override fun createDefaultRepo(): CharacterDataSource = DefaultRepo(this)
}