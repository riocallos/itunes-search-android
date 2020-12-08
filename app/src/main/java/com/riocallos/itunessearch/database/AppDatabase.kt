package com.riocallos.itunessearch.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.riocallos.itunessearch.database.daos.SearchResultDao
import com.riocallos.itunessearch.domain.models.SearchResult

@Database(version = 1, exportSchema = false, entities = [(SearchResult::class)])
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchResultDao(): SearchResultDao

    companion object {

         fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "itunes.db")
            .allowMainThreadQueries()
            .build()
    }

}
