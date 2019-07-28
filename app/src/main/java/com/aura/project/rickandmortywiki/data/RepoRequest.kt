package com.aura.project.rickandmortywiki.data

sealed class RepoRequest<T>

class FailedRequest<T> : RepoRequest<T>()

class SuccessfulRequest<T>(val body: T, val source: Int): RepoRequest<T>(){
    companion object{
        const val FROM_LOCAL = 100
        const val FROM_NET = 101
    }
}