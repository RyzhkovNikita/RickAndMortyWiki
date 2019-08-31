package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class MainCharRepo(apiService: ApiService, charDao: CharDao) : CharacterDataSource {

    private val factory: RepoFactory = RepoFactory(apiService, charDao)

    private lateinit var strategyImpl: CharacterDataSourceInternal

    override var strategy: Strategy = DefaultStrategy
        set(value) {
            field = value
            strategyImpl = when (value) {
                is DefaultStrategy -> factory.createDefaultRepo()
                is NameFilterStrategy -> factory.createNameFilter(value.name)
                is StatusFilterStrategy -> factory.createStatusFilter(value.status)
            }
        }

    override suspend fun getCharPage(page: Int) = strategyImpl.getCharPage(page)

    override suspend fun insertChars(chars: List<Character>, page: Int) =
        strategyImpl.insertChars(chars, page)

    override suspend fun clearAll() = strategyImpl.clearAll()

    override suspend fun getChar(id: Long) = strategyImpl.getChar(id)

    override suspend fun getChars(ids: LongArray) = strategyImpl.getChars(ids)

    override suspend fun getCharsFromUrl(ids: List<String>) = strategyImpl.getCharsFromUrl(ids)
}