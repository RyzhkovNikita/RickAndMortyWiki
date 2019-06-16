package com.aura.project.rickandmortywiki

class CharLocalRepo(private val charDao: CharDao) : CharacterDataSource {

    private val _PAGE_SIZE = 20

    override fun getCharPage(page: Int): RepoResult<List<Character>> {
        val charList = charDao.getBetween((page - 1) * _PAGE_SIZE + 1, page * _PAGE_SIZE)
        if (charList.size == _PAGE_SIZE) {
            val result = charList.sortedBy { character -> character.id }
            return SuccessfulRequest(result)
        }
        return FailedRequest()
    }

    override fun insertChars(chars: List<Character>) {
        charDao.insertChars(chars)
    }

    override fun clearAll() {
        charDao.clearAll()
    }

    override fun getChar(id: Int): RepoResult<Character> {
        val char = charDao.getById(id)
        if (char.isNotEmpty())
            return SuccessfulRequest(char[0])
        return FailedRequest()
    }

    override fun getChars(ids: List<Int>): RepoResult<List<Character>> =
            SuccessfulRequest(charDao.getById(ids))
}