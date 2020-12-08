package com.riocallos.itunessearch.network.remote.search

import com.riocallos.itunessearch.domain.models.Result
import com.riocallos.itunessearch.domain.models.SearchResponse
import io.reactivex.Single

interface SearchRemoteSource {

    fun search(term: String, country: String, media: String): Single<Result<SearchResponse>>

}