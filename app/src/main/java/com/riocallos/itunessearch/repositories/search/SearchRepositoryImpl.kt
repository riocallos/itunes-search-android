package com.riocallos.itunessearch.repositories.search

import com.riocallos.itunessearch.domain.models.Result
import com.riocallos.itunessearch.domain.models.SearchResponse
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.local.search.SearchLocalSource
import com.riocallos.itunessearch.network.remote.search.SearchRemoteSource
import io.reactivex.Single
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
        private val searchLocalSource: SearchLocalSource,
        private val searchRemoteSource: SearchRemoteSource
) : SearchRepository {

    override fun local(): Single<Array<SearchResult>> {
        return searchLocalSource.search()
    }

    override fun local(id: String): Single<SearchResult> {
        return searchLocalSource.search(id)
    }

    override fun remote(
        term: String,
        country: String,
        media: String
    ): Single<Result<SearchResponse>> {
        return searchRemoteSource.search(term, country, media)
    }
}