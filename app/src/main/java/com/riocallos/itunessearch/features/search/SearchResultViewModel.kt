package com.riocallos.itunessearch.features.search

import android.content.SharedPreferences
import android.os.Bundle
import com.riocallos.itunessearch.base.BaseViewModel
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.features.main.MainState
import com.riocallos.itunessearch.repositories.search.SearchRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class SearchResultViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val searchRepository: SearchRepository
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<SearchResultState> by lazy {
        PublishSubject.create<SearchResultState>()
    }

    val state: Observable<SearchResultState> = _state

    var searchResult = SearchResult()

    fun removeSearchResult() {
        sharedPreferences.edit().putString("search_result", "").apply()
    }

    fun localSearchResult(id: String) = searchRepository
        .local(id)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSubscribe {
            _state.onNext(SearchResultState.ShowProgressLoading)
        }
        .doOnSuccess {
            _state.onNext(SearchResultState.HideProgressLoading)
        }
        .doOnError {
            _state.onNext(SearchResultState.HideProgressLoading)
        }
        .subscribeBy {
            searchResult = it
            sharedPreferences.edit().putString("search_result", searchResult.id).apply()
            _state.onNext(SearchResultState.ShowSearchResult(it))
        }

}