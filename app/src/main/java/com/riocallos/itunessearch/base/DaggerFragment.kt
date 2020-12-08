package com.riocallos.itunessearch.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.riocallos.itunessearch.di.Injectable
import javax.inject.Inject

abstract class DaggerFragment<DB : ViewDataBinding> : BaseFragment<DB>(), Injectable {

    @Inject
    lateinit var schedulers: BaseSchedulerProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}