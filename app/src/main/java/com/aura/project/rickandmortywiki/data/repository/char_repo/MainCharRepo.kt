package com.aura.project.rickandmortywiki.data.repository.char_repo

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.repository.RepoFactory
import com.aura.project.rickandmortywiki.data.repository.RepoFactoryImpl

class MainCharRepo(private val factory: RepoFactory) :
    CharacterDataSource {

    private lateinit var strategyImpl: CharacterDataSourceInternal

    override var strategy: Strategy =
        DefaultStrategy
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