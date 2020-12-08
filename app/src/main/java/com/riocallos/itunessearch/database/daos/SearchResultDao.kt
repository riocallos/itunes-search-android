package com.riocallos.itunessearch.database.daos

import androidx.room.*
import com.riocallos.itunessearch.domain.models.SearchResult
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SearchResultDao {

    @Query("SELECT * FROM search_results")
    fun getAll(): Single<Array<SearchResult>>

    @Query("SELECT * FROM search_results WHERE id = :id")
    fun get(id: String): Single<SearchResult>

    @Query("DELETE FROM search_results")
    fun deleteAll(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(searchResult: SearchResult)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(searchResult: Array<SearchResult>)

}