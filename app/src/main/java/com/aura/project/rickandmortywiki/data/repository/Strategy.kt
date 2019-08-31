package com.aura.project.rickandmortywiki.data.repository

sealed class Strategy

object DefaultStrategy : Strategy()

class NameFilterStrategy(val name: String) : Strategy()

class StatusFilterStrategy(val status: String) : Strategy()