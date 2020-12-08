package com.riocallos.itunessearch.local.search

import com.riocallos.itunessearch.database.AppDatabase
import com.riocallos.itunessearch.domain.models.SearchResult
import io.reactivex.Single
import javax.inject.Inject

class SearchLocalSourceImpl @Inject constructor(
        private val appDatabase: AppDatabase
) : SearchLocalSource {

    override fun search(): Single<Array<SearchResult>> {
        return appDatabase.searchResultDao().getAll()
    }

    override fun search(id: String): Single<SearchResult> {
        return appDatabase.searchResultDao().get(id)
    }

}