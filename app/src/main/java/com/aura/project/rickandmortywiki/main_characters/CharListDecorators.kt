package com.aura.project.rickandmortywiki.main_characters

object ErrorDecorator {
    fun decorate(list: List<ListItem>): List<ListItem> {
        return list.toMutableList() + ErrorItem
    }
}

object LoadingDecorator {
    fun decorate(list: List<ListItem>): List<ListItem> {
        return list.toMutableList() + LoadingItem
    }
}