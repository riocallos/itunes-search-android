package com.riocallos.itunessearch.local.search

import com.riocallos.itunessearch.domain.models.SearchResult
import io.reactivex.Single

interface SearchLocalSource {
    fun search(): Single<Array<SearchResult>>
    fun search(id: String): Single<SearchResult>
}