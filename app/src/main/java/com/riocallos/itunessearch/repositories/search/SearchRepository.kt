package com.riocallos.itunessearch.repositories.search

import com.riocallos.itunessearch.domain.models.Result
import com.riocallos.itunessearch.domain.models.SearchResponse
import com.riocallos.itunessearch.domain.models.SearchResult
import io.reactivex.Single

interface SearchRepository {

    fun local(): Single<Array<SearchResult>>

    fun local(id: String): Single<SearchResult>

    fun remote(term: String, country: String, media: String): Single<Result<SearchResponse>>

}