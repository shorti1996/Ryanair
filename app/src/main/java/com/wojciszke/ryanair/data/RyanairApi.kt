package com.wojciszke.ryanair.data

import com.wojciszke.ryanair.data.model.Stations
import retrofit2.http.GET

interface RyanairApi {
    @GET("static/stations.json")
    suspend fun getStations(): Stations
}