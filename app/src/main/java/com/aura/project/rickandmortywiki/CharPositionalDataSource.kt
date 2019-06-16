package com.aura.project.rickandmortywiki

import androidx.paging.PositionalDataSource

class CharPositionalDataSource(private val charRepo: CharRepo): PositionalDataSource<Character>() {
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Character>) {

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Character>) {

    }
}