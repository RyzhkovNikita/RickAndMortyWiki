package com.aura.project.rickandmortywiki.data.repository.char_repo

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.repository.repo_factory.RepoFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainCharRepo(private val factory: RepoFactory) :
    CharacterDataSource {

    private var repo: CharacterDataSourceInternal = factory.createDefaultRepo()

    private val dispatcher = Dispatchers.IO

    override var strategy: Strategy = DefaultStrategy
        set(value) {
            field = value
            repo = when (value) {
                is DefaultStrategy -> factory.createDefaultRepo()
                is NameFilterStrategy -> factory.createNameFilter(value.name)
                is StatusFilterStrategy -> factory.createStatusFilter(value.status)
            }
        }

    override suspend fun getCharPage(page: Int) = withContext(dispatcher) { repo.getCharPage(page) }

    override suspend fun insertChars(chars: List<Character>, page: Int) = withContext(dispatcher) {
        repo.insertChars(chars, page)
    }

    override suspend fun clearAll() = withContext(dispatcher) { repo.clearAll() }

    override suspend fun getChar(id: Long) = withContext(dispatcher) { repo.getChar(id) }

    override suspend fun getChars(ids: LongArray) = withContext(dispatcher) { repo.getChars(ids) }

    override suspend fun getCharsFromUrl(ids: List<String>) =
        withContext(dispatcher) { repo.getCharsFromUrl(ids) }
}