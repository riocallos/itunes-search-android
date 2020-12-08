package com.riocallos.itunessearch.di.modules

import com.riocallos.itunessearch.local.search.SearchLocalSource
import com.riocallos.itunessearch.network.remote.search.SearchRemoteSource
import com.riocallos.itunessearch.repositories.search.SearchRepository
import com.riocallos.itunessearch.repositories.search.SearchRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun providesSearchRepository(
            searchLocalSource: SearchLocalSource,
            searchRemoteSource: SearchRemoteSource,
    ): SearchRepository = SearchRepositoryImpl(searchLocalSource, searchRemoteSource)

}