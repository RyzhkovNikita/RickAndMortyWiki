package com.aura.project.rickandmortywiki

class CharRepo(charDao: CharDao, charApi: ApiService) : CharacterDataSource {
    private val netRepo = CharNetRepo(charApi)
    private val localRepo = CharLocalRepo(charDao)
    override fun getCharPage(page: Int): List<Character>? =
        localRepo.getCharPage(page) ?: netRepo.getCharPage(page)


    override fun insertChars(chars: List<Character>) {
        localRepo.insertChars(chars)
        netRepo.insertChars(chars)
    }

    override fun clearAll() {
        localRepo.clearAll()
        netRepo.clearAll()
    }

    override fun getChar(id: Int): Character? =
        localRepo.getChar(id) ?: netRepo.getChar(id)
}