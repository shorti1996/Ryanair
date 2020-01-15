package com.wojciszke.ryanair.repository

import com.wojciszke.networking.FlightsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FlightsRepository @Inject constructor(private val flightsApi: FlightsApi) {
    suspend fun getFlights(origin: String,
                           destination: String,
                           dateout: String,
                           adults: Int,
                           teens: Int,
                           children: Int
    ) = flightsApi.getFlights(origin, destination, dateout, adults, teens, children)
}