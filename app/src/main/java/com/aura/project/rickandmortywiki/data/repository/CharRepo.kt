package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.filters.CharacterFilter
import com.aura.project.rickandmortywiki.data.filters.NoFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharRepo(private val netRepo: CharNetRepo, private val localRepo: CharLocalRepo) :
    CharacterDataSource {
    override var strategy: CharacterFilter = NoFilter
        set(value) {
            field = value
            localRepo.strategy = value
            netRepo.strategy = value
        }

    //TODO: write lastPageNumber to SharedPref, make class to deal with sharedPref

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val local = localRepo.getCharPage(page)) {
                is SuccessfulRequest -> {
                    local
                }
                is FailedRequest -> {
                    val net = netRepo.getCharPage(page)
                    if (net is SuccessfulRequest) {
                        insertChars(net.body)
                        net
                    } else
                        FailedRequest<List<Character>>()
                }
            }
        }


    override suspend fun insertChars(chars: List<Character>) =
        withContext(Dispatchers.IO) {
            localRepo.insertChars(chars)
            netRepo.insertChars(chars)
        }

    override suspend fun clearAll() =
        withContext(Dispatchers.IO) {
            localRepo.clearAll()
            netRepo.clearAll()
        }

    override suspend fun getChar(id: Long): RepoRequest<Character> =
        withContext(Dispatchers.IO) {
            when (val local = localRepo.getChar(id)) {
                is SuccessfulRequest -> local
                is FailedRequest -> netRepo.getChar(id)
            }
        }

    override suspend fun getChars(ids: LongArray): RepoRequest<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val local = localRepo.getChars(ids)) {
                is SuccessfulRequest -> local
                is FailedRequest -> netRepo.getChars(ids)
            }
        }

    override suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val local = localRepo.getCharsFromUrl(ids)) {
                is SuccessfulRequest -> local
                is FailedRequest -> netRepo.getCharsFromUrl(ids)
            }
        }
}