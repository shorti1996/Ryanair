package com.wojciszke.ryanair.networking

import android.util.Log
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
    ) = flightsApi.getFlights(origin, destination, dateout, adults, teens, children).also { Log.e("DEBUG", "calling flights API") }

//    fun getFlights2(origin: String,
//                           destination: String,
//                           dateout: String,
//                           adults: Int,
//                           teens: Int,
//                           children: Int
//    ) = flightsApi.getFlights2(origin, destination, dateout, adults, teens, children).request().url().toString().let { Log.e("DEBUG", it) }
}