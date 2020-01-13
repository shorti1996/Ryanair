package com.wojciszke.ryanair.data

import com.wojciszke.ryanair.data.model.api.Stations
import retrofit2.http.GET

interface StationsApi {
    @GET("static/stations.json")
    suspend fun getStations(): Stations
}