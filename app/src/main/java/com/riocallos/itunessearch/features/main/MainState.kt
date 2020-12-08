package com.riocallos.itunessearch.features.main

import com.riocallos.itunessearch.domain.models.SearchResult

sealed class MainState {

    object ShowProgressLoading : MainState()

    object HideProgressLoading : MainState()

    data class ShowRetrieved(val retrieved: String) : MainState()

    data class ShowSearchResults(val searchResults: List<SearchResult>) : MainState()

    data class ShowSearchResult(val searchResult: String) : MainState()

}