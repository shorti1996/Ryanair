package com.wojciszke.ryanair.networking

import com.wojciszke.ryanair.data.model.stations.Stations
import retrofit2.http.GET

interface StationsApi {
    @GET("static/stations.json")
    suspend fun getStations(): Stations
}