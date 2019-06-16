package com.aura.project.rickandmortywiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import java.util.concurrent.Executors

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _charList = MutableLiveData<PagedList<Character>>()
    val charList: LiveData<PagedList<Character>>
        get() = _charList
    private val _charRepo = CharRepo(
        AppDatabase.getInstance(getApplication()).charDao(),
        ApiService.getInstance()
    )
    init {
        val config = PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(false).build()
        val dataSource = CharPositionalDataSource(_charRepo)
        val pagedList = PagedList.Builder<Int, Character>(dataSource, config)
            .setFetchExecutor(Executors.newSingleThreadExecutor())
            .setNotifyExecutor(MainThreadExecutor())
            .build()
        _charList.value = pagedList
    }
}
