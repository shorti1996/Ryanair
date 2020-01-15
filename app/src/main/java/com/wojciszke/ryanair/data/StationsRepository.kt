package com.wojciszke.ryanair.data

import android.util.Log
import javax.inject.Inject

class StationsRepository @Inject constructor(private val stationsApi: StationsApi) {
    suspend fun getStations() = stationsApi.getStations().also { Log.e("DEBUG", "calling stations API") }
}