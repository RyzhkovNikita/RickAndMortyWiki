package com.aura.project.rickandmortywiki.data.repository

import com.aura.project.rickandmortywiki.UrlTransformer
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.FailedRequest
import com.aura.project.rickandmortywiki.data.RepoRequest
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.filters.CharacterFilter
import com.aura.project.rickandmortywiki.data.filters.NoFilter
import com.aura.project.rickandmortywiki.data.room.character.CharDao

class CharLocalRepo(private val charDao: CharDao) :
    CharacterDataSource {

    override var strategy: CharacterFilter = NoFilter

    private val _PAGE_SIZE = 20

    override suspend fun getCharPage(page: Int): RepoRequest<List<Character>> {
        val charList = charDao.getBetween((page - 1) * _PAGE_SIZE + 1, page * _PAGE_SIZE)
        if (charList.size == _PAGE_SIZE) {
            return SuccessfulRequest(body = charList, source = SuccessfulRequest.FROM_LOCAL)
        }
        return FailedRequest()
    }

    override suspend fun insertChars(chars: List<Character>, page: Int) {
        charDao.insertChars(chars)
    }

    override suspend fun clearAll() {
        charDao.clearAll()
    }

    override suspend fun getChar(id: Long): RepoRequest<Character> {
        val char = charDao.getById(id)
        if (char.isNotEmpty())
            return SuccessfulRequest(body = char[0], source = SuccessfulRequest.FROM_LOCAL)
        return FailedRequest()
    }

    override suspend fun getChars(ids: LongArray): RepoRequest<List<Character>> {
        val characters = charDao.getById(ids)
        return if (characters.size == ids.size)
            SuccessfulRequest(
                body = characters.sortedBy { character -> character.id }
                , source = SuccessfulRequest.FROM_LOCAL
            )
        else FailedRequest()
    }


    override suspend fun getCharsFromUrl(ids: List<String>): RepoRequest<List<Character>> {
        val idArray = UrlTransformer.urlsToIdArray(ids)
        return getChars(idArray)
    }
}