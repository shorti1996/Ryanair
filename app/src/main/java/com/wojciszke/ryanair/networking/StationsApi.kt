package com.wojciszke.ryanair.networking

import com.wojciszke.core.model.stations.Stations
import retrofit2.http.GET

interface StationsApi {
    @GET("static/stations.json")
    suspend fun getStations(): com.wojciszke.core.model.stations.Stations
}