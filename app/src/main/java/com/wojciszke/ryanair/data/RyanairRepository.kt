package com.wojciszke.ryanair.data

class RyanairRepository(private val ryanairApi: RyanairApi) {
    suspend fun getStations() = ryanairApi.getStations()
}