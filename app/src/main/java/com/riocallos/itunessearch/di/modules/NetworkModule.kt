package com.riocallos.itunessearch.di.modules

import com.riocallos.itunessearch.BuildConfig
import com.riocallos.itunessearch.network.api.ApiServices
import com.riocallos.itunessearch.network.remote.search.SearchRemoteSource
import com.riocallos.itunessearch.network.remote.search.SearchRemoteSourceImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(logging)
        }

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    fun providesSearchRemoteSource(
            apiServices: ApiServices
    ): SearchRemoteSource = SearchRemoteSourceImpl(apiServices)

}