package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharRepo(charDao: CharDao, charApi: ApiService) :
    CharacterDataSource {
    private val netRepo = CharNetRepo(charApi)
    private val localRepo = CharLocalRepo(charDao)
    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val netCharPage = netRepo.getCharPage(page)) {
                is SuccessfulRequest -> netCharPage

                is FailedRequest -> localRepo.getCharPage(page)
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

    override suspend fun getChar(id: Int): RepoRequest<Character> =
        withContext(Dispatchers.IO) {
            when (val netResult = netRepo.getChar(id)) {
                is SuccessfulRequest -> netResult

                is FailedRequest -> localRepo.getChar(id)
            }
        }

    override suspend fun getChars(ids: IntArray): RepoRequest<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val netResult = netRepo.getChars(ids)) {
                is SuccessfulRequest -> netResult

                is FailedRequest -> localRepo.getChars(ids)
            }
        }
}