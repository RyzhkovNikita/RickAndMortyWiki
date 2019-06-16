package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.character.CharDao
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.request.FailedRequest
import com.aura.project.rickandmortywiki.data.request.RepoRequest
import com.aura.project.rickandmortywiki.data.request.SuccessfulRequest

class CharRepo(charDao: CharDao, charApi: ApiService) :
    CharacterDataSource {
    private val netRepo = CharNetRepo(charApi)
    private val localRepo = CharLocalRepo(charDao)
    override fun getCharPage(page: Int): RepoRequest<List<Character>> =
        when (val localCharPage = localRepo.getCharPage(page)) {
            is SuccessfulRequest -> {
                localCharPage.body.sortedBy { character -> character.id }
                localCharPage
            }
            is FailedRequest -> netRepo.getCharPage(page)
            else -> FailedRequest()

        }


    override fun insertChars(chars: List<Character>) {
        localRepo.insertChars(chars)
        netRepo.insertChars(chars)
    }

    override fun clearAll() {
        localRepo.clearAll()
        netRepo.clearAll()
    }

    override fun getChar(id: Int): RepoRequest<Character> =
        when (val localRepoResult = localRepo.getChar(id)) {
            is SuccessfulRequest -> localRepoResult
            is FailedRequest -> netRepo.getChar(id)
            else -> FailedRequest()

        }

    override fun getChars(ids: List<Int>): RepoRequest<List<Character>> {
        val localResult = localRepo.getChars(ids)
        return if (localResult is SuccessfulRequest) {
            val idsNeedFromNet = ids.minus(localResult.body.map { char -> char.id })
            if (idsNeedFromNet.isEmpty()) {
                localResult
            } else {
                val netResult = netRepo.getChars(idsNeedFromNet)
                if (netResult is SuccessfulRequest) {
                    val sumResult = netResult.body.plus(localResult.body).sortedBy { char -> char.id }
                    SuccessfulRequest(sumResult)
                } else FailedRequest<List<Character>>()
            }
        } else FailedRequest()
    }
}