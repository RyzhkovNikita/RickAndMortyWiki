package com.aura.project.rickandmortywiki.data.repository.char_repo

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.repository.FilterParams
import com.aura.project.rickandmortywiki.data.repository.repo_factory.RepoFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainCharRepo(private val factory: RepoFactory) :
    CharacterDataSource {
    private var repo: CharacterDataSource = factory.createDefaultRepo()
    private val dispatcher = Dispatchers.IO
    override var filterParams: FilterParams = FilterParams()
        set(value) {
            field = value
            repo = if (value.isEmpty())
                factory.createDefaultRepo()
            else
                factory.createNetRepo(value)
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