package com.riocallos.itunessearch.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riocallos.itunessearch.di.ViewModelFactory
import com.riocallos.itunessearch.di.scopes.ViewModelKey
import com.riocallos.itunessearch.features.main.MainViewModel
import com.riocallos.itunessearch.features.search.SearchResultViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchResultViewModel::class)
    abstract fun bindSearchResultViewModel(viewModel: SearchResultViewModel): ViewModel

}