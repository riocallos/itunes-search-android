package com.riocallos.itunessearch.features.search

import com.riocallos.itunessearch.domain.models.SearchResult

sealed class SearchResultState {

    object ShowProgressLoading : SearchResultState()

    object HideProgressLoading : SearchResultState()

    data class ShowSearchResult(val searchResult: SearchResult) : SearchResultState()

}