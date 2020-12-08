package com.riocallos.itunessearch.di.modules

import android.app.Application
import android.content.Context
import com.riocallos.itunessearch.di.scopes.ApplicationContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {
    @ApplicationContext
    @Singleton
    @Binds
    abstract fun providesApplicationContext(app: Application): Context
}