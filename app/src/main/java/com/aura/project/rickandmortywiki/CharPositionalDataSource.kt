package com.aura.project.rickandmortywiki

import androidx.paging.PositionalDataSource

class CharPositionalDataSource(private val _charRepo: CharRepo) : PositionalDataSource<Character>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {
        val idsList = (params.startPosition + 1..params.startPosition + params.loadSize + 1).toList()
        val repoRequest = _charRepo.getChars(idsList)
        if (repoRequest is SuccessfulRequest)
            callback.onResult(repoRequest.body)
        else
            callback.onResult(emptyList())
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Character>) {
        val idsList = (params.requestedStartPosition + 1..params.requestedStartPosition + params.requestedLoadSize + 1).toList()
        val repoRequest = _charRepo.getChars(idsList)
        if (repoRequest is SuccessfulRequest)
            callback.onResult(repoRequest.body, 0)
        else
            callback.onResult(emptyList(), 0)
    }
}