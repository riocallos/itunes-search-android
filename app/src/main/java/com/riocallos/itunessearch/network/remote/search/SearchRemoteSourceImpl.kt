package com.riocallos.itunessearch.network.remote.search

import com.riocallos.itunessearch.domain.core.ErrorHandler
import com.riocallos.itunessearch.domain.models.Result
import com.riocallos.itunessearch.domain.models.SearchResponse
import com.riocallos.itunessearch.network.api.ApiServices
import io.reactivex.Single
import javax.inject.Inject

class SearchRemoteSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : SearchRemoteSource {
    override fun search(term: String, country: String, media: String): Single<Result<SearchResponse>> {
        return apiServices.search(term, country, media).map { response ->
            run {
                Result.success(response)
            }
        }.onErrorReturn {
            Result.error(ErrorHandler.handleError(it))
        }
    }
}