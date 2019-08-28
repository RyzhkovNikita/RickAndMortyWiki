package com.aura.project.rickandmortywiki

sealed class State

class Showing<T>(val value: T) : State()

object Error : State()

object Loading : State()