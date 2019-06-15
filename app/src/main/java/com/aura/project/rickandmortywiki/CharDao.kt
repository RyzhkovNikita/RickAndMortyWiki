package com.aura.project.rickandmortywiki

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharDao {
    @Query("select * from character")
    fun getAll(): List<Character>

    @Query("select * from character where id between :id1 and :id2")
    fun getBetween(id1: Int, id2: Int): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChars(list: List<Character>)

    @Query("delete from character")
    fun clearAll()

    @Query("select 1 from character where id=:charId")
    fun getById(charId: Int): List<Character>
}