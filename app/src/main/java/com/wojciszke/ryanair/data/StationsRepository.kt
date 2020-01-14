package com.wojciszke.ryanair.data

import android.util.Log

class StationsRepository(private val stationsApi: StationsApi) {
    suspend fun getStations() = stationsApi.getStations().also { Log.e("DEBUG", "calling stations API") }
}