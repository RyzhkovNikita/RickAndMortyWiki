package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.repository.char_repo.CharacterDataSourceInternal

interface RepoFactory {
    fun createLocalRepo(): CharacterDataSourceInternal
    fun createNetRepo(): CharacterDataSourceInternal
    fun createDefaultRepo(): CharacterDataSourceInternal
    fun createNameFilter(name: String): CharacterDataSourceInternal
    fun createStatusFilter(status: String): CharacterDataSourceInternal
}