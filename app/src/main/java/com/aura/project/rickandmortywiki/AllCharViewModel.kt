package com.aura.project.rickandmortywiki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;

class AllCharViewModel : ViewModel() {
    val _charList = MutableLiveData<List<Character>>()
    val charList : LiveData<List<Character>> = _charList
}
