package com.riocallos.itunessearch.di.modules

import com.riocallos.itunessearch.network.api.ApiServices
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiServiceModule {

    @Provides
    @Singleton
    fun providesApiServices(retrofit: Retrofit): ApiServices = retrofit.create(ApiServices::class.java)

}