package com.aura.project.rickandmortywiki

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Character::class],
    version = 1
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

        fun getInstance(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "todo-list.db"
        )
            .build()
    }
}