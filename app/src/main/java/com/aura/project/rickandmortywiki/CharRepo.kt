package com.aura.project.rickandmortywiki

class CharRepo(charDao: CharDao, charApi: ApiService): CharacterDataSource {
    val netRepo = CharNetRepo(charApi)
    val localRepo = CharLocalRepo(charDao)
    override fun getCharPage(page: Int): List<Character> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insertChars(chars: List<Character>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChar(id: Int): Character? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}