package com.riocallos.itunessearch.di.builders

import com.riocallos.itunessearch.di.scopes.ActivityScope
import com.riocallos.itunessearch.features.main.MainActivity
import com.riocallos.itunessearch.features.search.views.SearchResultActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeSearchResultActivity(): SearchResultActivity

}