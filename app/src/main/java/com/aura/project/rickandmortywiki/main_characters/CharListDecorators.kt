package com.aura.project.rickandmortywiki.main_characters

object ErrorDecorator {
    fun decorate(list: List<ListItem>): List<ListItem> {
        return list.also { it.toMutableList().add(ErrorItem()) }
    }

    fun remove(list: List<ListItem>): List<ListItem> {
        return list.filterNot { it is ErrorItem }
    }
}

object LoadingDecorator {

}