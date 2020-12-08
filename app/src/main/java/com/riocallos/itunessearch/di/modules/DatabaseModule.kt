package com.riocallos.itunessearch.di.modules

import android.app.Application
import android.content.Context
import com.riocallos.itunessearch.database.AppDatabase
import com.riocallos.itunessearch.local.search.SearchLocalSource
import com.riocallos.itunessearch.local.search.SearchLocalSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(application: Application): AppDatabase {
        return AppDatabase.buildDatabase(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideSearchLocalSource(
        appDatabase: AppDatabase
    ): SearchLocalSource = SearchLocalSourceImpl(appDatabase)

}