package com.aura.project.rickandmortywiki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class MainShowViewModel : ViewModel() {
    private val _fragment = MutableLiveData<Int>()
    val fragment: LiveData<Int>
        get() = _fragment
    companion object{
        const val ALL_CHAR_FRAGMENT = 50
    }
    init {
        _fragment.value = ALL_CHAR_FRAGMENT
    }
}
