package com.wojciszke.ryanair.di.module

import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wojciszke.ryanair.data.StationsApi
import com.wojciszke.ryanair.data.StationsRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [StationsModule::class, FlightsModule::class])
class RyanairRepositioryModule {
    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().setLenient().create()
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.API_BASE_URL_STATIONS)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
//    @Singleton
//    @Provides
//    fun provideStationsApi(retrofit: Retrofit): StationsApi = retrofit.create(StationsApi::class.java)
//
//    @Singleton
//    @Provides
//    fun provideStationsRepository(stationsStationsApi: StationsApi): StationsRepository = StationsRepository(stationsStationsApi)
}