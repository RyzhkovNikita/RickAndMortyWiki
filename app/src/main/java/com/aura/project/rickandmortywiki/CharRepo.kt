package com.aura.project.rickandmortywiki

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CharRepo(charDao: CharDao, charApi: ApiService) : CharacterDataSource {
    private val netRepo = CharNetRepo(charApi)
    private val localRepo = CharLocalRepo(charDao)
    override suspend fun getCharPage(page: Int): RepoResult<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val localCharPage = localRepo.getCharPage(page)) {
                is SuccessfulRequest -> return@withContext localCharPage
                is FailedRequest -> return@withContext netRepo.getCharPage(page)
                else -> return@withContext FailedRequest<List<Character>>()
            }
        }


    override suspend fun insertChars(chars: List<Character>) {
        withContext(Dispatchers.IO) {
            localRepo.insertChars(chars)
            netRepo.insertChars(chars)
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            localRepo.clearAll()
            netRepo.clearAll()
        }
    }

    override suspend fun getChar(id: Int): RepoResult<Character> =
        withContext(Dispatchers.IO) {
            when (val localRepoResult = localRepo.getChar(id)) {
                is SuccessfulRequest -> return@withContext localRepoResult
                is FailedRequest -> return@withContext netRepo.getChar(id)
                else -> return@withContext FailedRequest<Character>()
            }
        }

    override suspend fun getChars(ids: List<Int>): RepoResult<List<Character>> =
        withContext(Dispatchers.IO) {
            when (val localResult = localRepo.getChars(ids)) {
                is SuccessfulRequest -> {

                    val idsNeedFromNet = ids.minus(localResult.body.map { char -> char.id })
                    when (val netResult = netRepo.getChars(idsNeedFromNet)){
                        is SuccessfulRequest -> {
                            val sumResult = netResult.body.plus(localResult.body).sortedBy { char -> char.id }
                            return@withContext SuccessfulRequest(sumResult)
                        }

                        else -> return@withContext netResult
                    }
                }

                else -> return@withContext FailedRequest<List<Character>>()
            }
        }

}