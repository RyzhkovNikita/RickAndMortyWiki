package com.aura.project.rickandmortywiki.data.repository.char_repo

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.RepoFactory

open class DefaultRepo(factory: RepoFactory) : CharacterDataSourceInternal {

    private val localRepo = factory.createLocalRepo()
    private val netRepo = factory.createNetRepo()

    //TODO: write lastPageNumber to SharedPref, make class to deal with sharedPref

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> =
        when (val localRequest = localRepo.getCharPage(page)) {
            is SuccessfulRequest -> localRequest
            is FailedRequest -> netRepo.getCharPage(page)
        }

    override suspend fun insertChars(chars: List<Character>, page: Int) {
            localRepo.insertChars(chars, page)
            netRepo.insertChars(chars, page)
        }

    override suspend fun clearAll() {
            localRepo.clearAll()
            netRepo.clearAll()
        }

    override suspend fun getChar(id: Long): RepoRequest<Character> =
        when (val local = localRepo.getChar(id)) {
            is SuccessfulRequest -> local
            is FailedRequest -> netRepo.getChar(id)
        }


    override suspend fun getChars(ids: LongArray): RepoRequest<List<Character>> =
            when (val local = localRepo.getChars(ids)) {
                is SuccessfulRequest -> local
                is FailedRequest -> netRepo.getChars(ids)
            }

    override suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>> =
            when (val local = localRepo.getCharsFromUrl(ids)) {
                is SuccessfulRequest -> local
                is FailedRequest -> netRepo.getCharsFromUrl(ids)
            }
}