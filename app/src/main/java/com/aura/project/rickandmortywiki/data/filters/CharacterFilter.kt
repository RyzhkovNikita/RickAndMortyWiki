package com.aura.project.rickandmortywiki.data.filters

sealed class CharacterFilter

object NoFilter : CharacterFilter()

class NameCharFilter(val name: String) : CharacterFilter()

class StatusCharFilter(val status: String) : CharacterFilter()
