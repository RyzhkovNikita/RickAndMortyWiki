package com.aura.project.rickandmortywiki.main_characters

import android.accounts.NetworkErrorException
import android.app.Application
import androidx.lifecycle.*
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.data.SuccessfulRequest
import com.aura.project.rickandmortywiki.data.repository.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.CharRepo
import com.aura.project.rickandmortywiki.data.repository.CharacterDataSource
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase
import com.aura.project.rickandmortywiki.toCharToShowList
import kotlinx.coroutines.launch

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _INIT_PAGE_COUNT = 3
    private var _currentLoadedPages = 0
    private val _charList = MutableLiveData<List<Character>>()
    private val _inProgress = MutableLiveData<Boolean>()
    private val _showingError = MutableLiveData<Boolean>()
    val inProgress: LiveData<Boolean>
        get() = _inProgress
    val charList: LiveData<List<CharToShowItem>> = Transformations.map(_charList) { list ->
        return@map list.toCharToShowList()
    }

    val showingError: LiveData<Boolean>
        get() = _showingError

    private val _charRepo: CharacterDataSource = CharRepo(
        CharNetRepo(
            ApiService.getInstance()
        ),
        CharLocalRepo(
            AppDatabase.getInstance(getApplication()).charDao()
        )
    )

    init {
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
            _currentLoadedPages++
        } else {
            throw NetworkErrorException()
        }
    }


    private fun showError() {
        _showingError.value = true
    }


    fun listEndReached() {
        if (!_inProgress.value!!) launchLoading { loadPage(_currentLoadedPages + 1) }
    }


    private fun launchLoading(block: suspend () -> Unit) =
        viewModelScope.launch {
            _showingError.value = false
            _inProgress.value = true
            try {
                block()
            } catch (e: Exception) {
                showError()
            } finally {
                _inProgress.value = false
            }
        }

    fun tryButtonClicked() {
        launchLoading { loadPage(_currentLoadedPages + 1) }
    }
}
