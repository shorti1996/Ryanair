package com.wojciszke.ryanair.networking

import com.wojciszke.ryanair.utils.NetworkFail
import com.wojciszke.ryanair.utils.NetworkSuccess
import java.io.IOException
import javax.inject.Inject

class StationsRepository @Inject constructor(private val stationsApi: StationsApi) {
    suspend fun getStations() = try {
        val stations = stationsApi.getStations() // you know how it is... .also { Log.e("DEBUG", "calling stations API") }
        NetworkSuccess(stations)
    } catch (e: IOException) {
        NetworkFail(e.message ?: "network error")
    }
}