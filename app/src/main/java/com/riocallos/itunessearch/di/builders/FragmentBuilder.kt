package com.riocallos.itunessearch.di.builders

import com.riocallos.itunessearch.features.search.views.SearchResultFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun contributeSearchResultFragment(): SearchResultFragment

}