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
import com.aura.project.rickandmortywiki.data.room.character.repository.CharRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _INIT_PAGE_COUNT = 3
    private var currentLoadedPages = 0
    private val currentList: List<Character> = ArrayList()
    private val _charList = MutableLiveData<List<Character>>()
    val charList: LiveData<List<Character>>
        get() = _charList

    private val _charRepo = CharRepo(
        AppDatabase.getInstance(getApplication()).charDao(),
        ApiService.getInstance()
    )

    init {
        initialLoad()
    }

    private fun initialLoad() {
        viewModelScope.launch {
            _charList.value = getStartPages()
        }
    }

    private suspend fun getStartPages(): List<Character> =
        withContext(Dispatchers.IO) {
            for (pageNumber in 1.._INIT_PAGE_COUNT) {
                val request = _charRepo.getCharPage(pageNumber)
                if (request is SuccessfulRequest)
                    currentList.plus(request.body)
                else {
                    showError()
                    return@withContext currentList
                }
            }
            currentList
        }

    private fun showError() {

    }
}
