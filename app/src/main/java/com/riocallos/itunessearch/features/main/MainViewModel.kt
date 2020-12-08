package com.riocallos.itunessearch.features.main

import android.content.SharedPreferences
import android.os.Bundle
import com.riocallos.itunessearch.base.BaseViewModel
import com.riocallos.itunessearch.database.AppDatabase
import com.riocallos.itunessearch.domain.models.SearchResult
import com.riocallos.itunessearch.repositories.search.SearchRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
        private val sharedPreferences: SharedPreferences,
        private val appDatabase: AppDatabase,
        private val searchRepository: SearchRepository,
) : BaseViewModel() {

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    private val _state: PublishSubject<MainState> by lazy {
        PublishSubject.create<MainState>()
    }

    val state: Observable<MainState> = _state

    fun getRetrieved() {
        val retrieved = sharedPreferences.getString("retrieved", "")

        if(retrieved!!.isNotEmpty()) {
            val sdf = SimpleDateFormat("MMM. d, yyyy h:mm a", Locale.ENGLISH)
            _state.onNext(MainState.ShowRetrieved("Retrieved ${sdf.format(Date(retrieved.toLong()))}"))
        }
    }

    fun getSearchResult() {
        val id = sharedPreferences.getString("search_result", "")
        if(id!!.isNotEmpty()) {
            _state.onNext(MainState.ShowSearchResult(id))
        }
    }

    fun localSearch() = searchRepository
        .local()
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSubscribe {
            _state.onNext(MainState.ShowProgressLoading)
        }
        .doOnSuccess {
            _state.onNext(MainState.HideProgressLoading)
        }
        .doOnError {
            _state.onNext(MainState.HideProgressLoading)
        }
        .subscribeBy {
            if(it.isNotEmpty()) {
                val list = mutableListOf<SearchResult>()
                list.addAll(it)
                _state.onNext(MainState.ShowSearchResults(list))
            }
        }

    fun remoteSearch(term: String, country: String, media: String) = searchRepository
        .remote(term, country, media)
        .subscribeOn(schedulers.io())
        .observeOn(schedulers.ui())
        .doOnSubscribe {
            _state.onNext(MainState.ShowProgressLoading)
        }
        .doOnSuccess {
            _state.onNext(MainState.HideProgressLoading)
        }
        .doOnError {
            _state.onNext(MainState.HideProgressLoading)
        }
        .subscribeBy {
            if(it.isSuccessful) {
                val list = mutableListOf<SearchResult>()
                list.addAll(it.value().results)
                if(list.isNotEmpty()) {
                    sharedPreferences.edit().putString("retrieved", Date().time.toString()).apply()
                    getRetrieved()
                    appDatabase.searchResultDao().insertAll(it.value().results)
                    _state.onNext(MainState.ShowSearchResults(list))
                } else {
                    localSearch()
                }
            } else {
                localSearch()
            }
        }

}