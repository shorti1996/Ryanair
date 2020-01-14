package com.wojciszke.ryanair.data

import android.util.Log
import retrofit2.http.Query

class FlightsRepository(private val flightsApi: FlightsApi) {
    suspend fun getStations(origin: String,
                            destination: String,
                            dateout: String,
                            adults: Int,
                            teens: Int,
                            children: Int
    ) = flightsApi.getFlights(origin, destination, dateout, adults, teens, children).also { Log.e("DEBUG", "calling flights API") }
}