package com.wojciszke.ryanair.di.module

import com.wojciszke.ryanair.BuildConfig
import com.wojciszke.networking.FlightsApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class FlightsApiModule {
    @Singleton
    @Provides
    @Named(RETROFIT_NAME)
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_FLIGHTS)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideFlightsApi(@Named(RETROFIT_NAME) retrofit: Retrofit): FlightsApi = retrofit.create(FlightsApi::class.java)

    companion object {
        const val RETROFIT_NAME = "FLIGHTS_RETROFIT"
    }
}