package com.aura.project.rickandmortywiki.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aura.project.rickandmortywiki.data.Character
import com.aura.project.rickandmortywiki.converters.EpisodeListConverter
import com.aura.project.rickandmortywiki.converters.LocationConverter
import com.aura.project.rickandmortywiki.converters.OriginConverter
import com.aura.project.rickandmortywiki.data.room.character.CharDao

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    OriginConverter::class,
    EpisodeListConverter::class,
    LocationConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charDao(): CharDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance
            ?: synchronized(LOCK) {
            instance
                ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(context, AppDatabase::class.java, "todo-itemList.db")
            .build()
    }
}