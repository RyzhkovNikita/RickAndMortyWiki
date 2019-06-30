package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class CharLocalRepo(private val charDao: CharDao) :
    CharacterDataSource {

    private val _PAGE_SIZE = 20

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val charList = charDao.getBetween((page - 1) * _PAGE_SIZE + 1, page * _PAGE_SIZE)
        if (charList.size == _PAGE_SIZE) {
            val result = charList.sortedBy { character -> character.id }
            return SuccessfulRequest(body = result, source = SuccessfulRequest.FROM_LOCAL)
        }
        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>) {
        charDao.insertChars(chars)
    }

    override suspend fun clearAll() {
        charDao.clearAll()
    }

    override suspend fun getChar(id: Int): RepoRequest<Character> {
        val char = charDao.getById(id)
        if (char.isNotEmpty())
            return SuccessfulRequest(body = char[0], source = SuccessfulRequest.FROM_LOCAL)
        return FailedRequest()
    }

    override suspend fun getChars(ids: IntArray): RepoRequest<List<Character>> =
        SuccessfulRequest(
            body = charDao.getById(ids).sortedBy { character -> character.id }
            , source = SuccessfulRequest.FROM_LOCAL
        )
}