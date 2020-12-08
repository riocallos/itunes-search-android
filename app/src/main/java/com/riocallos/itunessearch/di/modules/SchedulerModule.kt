package com.riocallos.itunessearch.di.modules

import com.riocallos.itunessearch.base.BaseSchedulerProvider
import com.riocallos.itunessearch.di.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {
    @Provides
    @Singleton
    fun providesSchedulerSource(): BaseSchedulerProvider = SchedulerProvider.getInstance()
}