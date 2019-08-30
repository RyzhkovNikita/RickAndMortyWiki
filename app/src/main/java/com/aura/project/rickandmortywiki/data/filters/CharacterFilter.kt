package com.aura.project.rickandmortywiki.data.filters

sealed class CharacterFilter

object NoFilter : CharacterFilter()

class NameCharFilter(name: String) : CharacterFilter()

class StatusCharFilter(status: String) : CharacterFilter()
