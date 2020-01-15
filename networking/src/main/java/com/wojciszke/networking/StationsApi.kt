package com.wojciszke.networking

import retrofit2.http.GET

interface StationsApi {
    @GET("static/stations.json")
    suspend fun getStations(): com.wojciszke.core.model.stations.Stations
}