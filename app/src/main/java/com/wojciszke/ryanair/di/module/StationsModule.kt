package com.wojciszke.ryanair.di.module

import com.google.gson.Gson
import com.wojciszke.ryanair.BuildConfig
import com.wojciszke.ryanair.data.StationsApi
import com.wojciszke.ryanair.data.StationsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class StationsModule {
//    @Singleton
//    @Provides
//    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    @Named(RETROFIT_NAME)
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL_STATIONS)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Singleton
    @Provides
    fun provideStationsApi(@Named(RETROFIT_NAME) retrofit: Retrofit): StationsApi = retrofit.create(StationsApi::class.java)

    @Singleton
    @Provides
    fun provideStationsRepository(stationsApi: StationsApi): StationsRepository = StationsRepository(stationsApi)

    companion object{
        const val RETROFIT_NAME = "STATIONS_RETROFIT"
    }
}