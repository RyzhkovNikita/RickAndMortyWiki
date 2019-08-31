package com.aura.project.rickandmortywiki.data.repository.repo_factory

import com.aura.project.rickandmortywiki.data.repository.FilterParams
import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSource
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

interface RepoFactory {
    fun charApi(): ApiService
    fun charDao(): CharDao
    fun createLocalRepo(): CharacterDataSource
    fun createNetRepo(filterParams: FilterParams): CharacterDataSource
    fun createNetRepo(): CharacterDataSource
    fun createDefaultRepo(): CharacterDataSource
}