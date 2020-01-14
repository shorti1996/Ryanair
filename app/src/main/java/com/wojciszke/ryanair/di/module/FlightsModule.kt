package com.wojciszke.ryanair.di.module

import com.google.gson.Gson
import com.wojciszke.ryanair.BuildConfig
import com.wojciszke.ryanair.data.FlightsApi
import com.wojciszke.ryanair.data.FlightsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class FlightsModule {
    @Singleton
    @Provides
    @Named(RETROFIT_NAME)
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_FLIGHTS)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideFlightsApi(retrofit: Retrofit): FlightsApi = retrofit.create(FlightsApi::class.java)

    @Singleton
    @Provides
    fun provideStationsRepository(flightsApi: FlightsApi): FlightsRepository = FlightsRepository(flightsApi)

    companion object {
        const val RETROFIT_NAME = "FLIGHTS_RETROFIT"
    }
}