package com.aura.project.rickandmortywiki.main_characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.request.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import com.aura.project.rickandmortywiki.data.repository.CharRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _INIT_PAGE_COUNT = 3
    private var currentLoadedPages = 0
    private lateinit var currentList: List<Character>
    private val _charList = MutableLiveData<List<Character>>()
    val charList: LiveData<List<Character>>
        get() = _charList

    private val _charRepo = CharRepo(
        AppDatabase.getInstance(getApplication()).charDao(),
        ApiService.getInstance()
    )

    init {
        currentList = ArrayList()
        initialLoad()
    }

    private fun initialLoad() {
        viewModelScope.launch {
            val list = getList()
            _charList.value = list
        }
    }

    private suspend fun loadStartPages() =
        withContext(Dispatchers.IO) {
            for (pageNumber in 1.._INIT_PAGE_COUNT) {
                val request = _charRepo.getCharPage(pageNumber)
                if (request is SuccessfulRequest)
                    currentList.plus(request.body)
                else {
                    showError()
                }
            }
        }

    private suspend fun getList(): List<Character> =
        withContext(Dispatchers.IO){
            val request = _charRepo.getCharPage(0)
            if (request is SuccessfulRequest)
                request.body
            else
                emptyList()
        }

    private fun showError() {

    }
}
