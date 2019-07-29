package com.aura.project.rickandmortywiki.details_episode

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aura.project.rickandmortywiki.data.repository.CharLocalRepo
import com.aura.project.rickandmortywiki.data.repository.CharNetRepo
import com.aura.project.rickandmortywiki.data.repository.CharRepo
import com.aura.project.rickandmortywiki.data.retrofit.ApiService
import com.aura.project.rickandmortywiki.data.room.AppDatabase

class EpisodeViewModel(val id: Long, app: Application) : AndroidViewModel(app) {

    private val _charRepo = CharRepo(
        CharNetRepo(
            ApiService.getInstance()
        ),
        CharLocalRepo(
            AppDatabase.getInstance(getApplication()).charDao()
        )
    )

    init {

    }

    class Factory(val id: Long, val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EpisodeViewModel::class.java))
                return EpisodeViewModel(id, app) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
