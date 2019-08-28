package com.aura.project.rickandmortywiki.main_characters

interface ListItem

data class CharToShowItem(
    val id: Long,
    val name: String,
    val imageUrl: String
) : ListItem

class ErrorItem : ListItem

class LoadingItem : ListItem