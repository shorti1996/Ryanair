package com.wojciszke.ryanair.repository

import com.wojciszke.networking.NetworkFail
import com.wojciszke.networking.NetworkSuccess
import java.io.IOException
import javax.inject.Inject

class StationsRepository @Inject constructor(private val stationsApi: com.wojciszke.networking.StationsApi) {
    suspend fun getStations() = try {
        val stations = stationsApi.getStations()
        NetworkSuccess(stations)
    } catch (e: IOException) {
        NetworkFail(e.message ?: "network error")
    }
}