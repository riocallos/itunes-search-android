package com.riocallos.itunessearch.network.api

import com.riocallos.itunessearch.domain.models.SearchResponse
import io.reactivex.Single
import retrofit2.http.*

interface ApiServices {

    /**
     * Search API endpoint.
     *
     * @property term [String] search query.
     * @property country [String] location filter.
     * @property media [String] type filter.
     */
    @GET("search")
    fun search(@Query("term") term: String, @Query("country") country: String, @Query("media") media: String): Single<SearchResponse>

}