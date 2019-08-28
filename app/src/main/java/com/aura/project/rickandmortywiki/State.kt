package com.aura.project.rickandmortywiki

sealed class State
object Ready : State()
object Error : State()
object Loading : State()