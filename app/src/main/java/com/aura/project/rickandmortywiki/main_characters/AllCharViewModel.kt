package com.aura.project.rickandmortywiki.main_characters

import android.accounts.NetworkErrorException
import android.app.Application
import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.filters.NameCharFilter
import com.aura.project.rickandmortywiki.data.filters.StatusCharFilter
import com.aura.project.rickandmortywiki.data.repository.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.CharRepo
import com.aura.project.rickandmortywiki.data.repository.CharacterDataSource
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import kotlinx.coroutines.launch

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _INIT_PAGE_COUNT = 3
    private var _currentLoadedPages = 0
    private val _state = MutableLiveData<State>()
    private val _charList = MutableLiveData<List<Character>>()

    val charList: LiveData<List<ListItem>> = Transformations.map(_charList) { list ->
        return@map list.toCharToShowList()
    }

    val state: LiveData<State>
        get() = _state

    private val _charRepo: CharacterDataSource = CharRepo(
        CharNetRepo(
            ApiService.getInstance()
        ),
        CharLocalRepo(
            AppDatabase.getInstance(getApplication()).charDao()
        )
    )

    init {
        _charRepo.strategy = StatusCharFilter("dead", ApiService.getInstance())
        launchLoading {
            for (pageNum in 1.._INIT_PAGE_COUNT) {
                loadPage(pageNum)
            }
        }
    }

    private suspend fun loadPage(page: Int) {
        val request = _charRepo.getCharPage(page)
        if (request is SuccessfulRequest) {
            _charList.value = _charList.value?.plus(request.body) ?: request.body
            _charRepo.insertChars(request.body, page)
            _currentLoadedPages++
        } else {
            throw NetworkErrorException()
        }
    }

    fun listEndReached() {
        if (_state.value !is Loading) launchLoading { loadPage(_currentLoadedPages + 1) }
    }


    private fun launchLoading(block: suspend () -> Unit) =
        viewModelScope.launch {
            _state.value = Loading
            try {
                block()
                _state.value = Ready
            } catch (e: Exception) {
                _state.value = Error
            }
        }

    fun tryButtonClicked() {
        launchLoading { loadPage(_currentLoadedPages + 1) }
    }
}
