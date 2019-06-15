package com.aura.project.rickandmortywiki

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class AllCharViewModel(application: Application) : AndroidViewModel(application) {
    private val _charList = MutableLiveData<List<Character>>()
    val charList: LiveData<List<Character>>
        get() = _charList
    private val db = AppDatabase.getInstance(getApplication())
    private val net = ApiService.getInstance()
    private val charRepo = CharRepo(
        AppDatabase.getInstance(getApplication()).charDao(),
        ApiService.getInstance()
    )
    fun loadPage() {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                val result = net.getCharPageCall().getPage()
                result.logAll()
            }
        }
    }

    suspend fun Call<CharacterPage>.getPage(): CharacterPage? =
        withContext(Dispatchers.IO) {
            this@getPage.execute().body()
        }


    suspend fun CharacterPage?.logAll() {
        withContext(Dispatchers.IO) {
            this@logAll?.characters?.map {
                Log.d("tag", "${it.name}__${it.gender}__${it.status}")
            }
        }
    }
}
