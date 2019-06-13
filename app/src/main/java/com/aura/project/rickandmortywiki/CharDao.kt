package com.aura.project.rickandmortywiki

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CharDao {
    @Query("select * from character")
    fun getAll(): List<Character>

    @Insert
    fun insertChars(list: List<Character>)
}