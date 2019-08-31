package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSourceInternal
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

interface RepoFactory {
    fun charApi(): ApiService
    fun charDao(): CharDao
    fun createLocalRepo(): CharacterDataSourceInternal
    fun createNetRepo(): CharacterDataSourceInternal
    fun createDefaultRepo(): CharacterDataSourceInternal
    fun createNameFilter(name: String): CharacterDataSourceInternal
    fun createStatusFilter(status: String): CharacterDataSourceInternal
}