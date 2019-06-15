package com.aura.project.rickandmortywiki

class CharLocalRepo(private val charDao: CharDao) : CharacterDataSource {

    private val _PAGE_SIZE = 20

    /*
    return empty list, if there is no this page
    return list of char by the page
     */
    override fun getCharPage(page: Int): List<Character> {
        val charList = charDao.getBetween((page - 1) * _PAGE_SIZE + 1, page * _PAGE_SIZE)
        if (charList.size < _PAGE_SIZE)
            return emptyList()
        return charList.sortedBy { character -> character.id }
    }

    override fun insertChars(chars: List<Character>) {
        charDao.insertChars(chars)
    }

    override fun clearAll() {
        charDao.clearAll()
    }

    /*
    return char if it is in DB
    return NULL if there is no char with the same id
     */
    override fun getChar(id: Int): Character? {
        val char = charDao.getById(id)
        if (char.isEmpty())
            return null
        return char[0]
    }

}