package com.aura.project.rickandmortywiki.data.repository

data class FilterParams(
    val name: String? = null,
    val status: String? = null
) {
    fun isEmpty(): Boolean = name == null && status == null
}