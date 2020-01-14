package com.wojciszke.ryanair.data

import android.telecom.Call
import com.wojciszke.ryanair.data.model.flights.Flights
import retrofit2.http.GET
import retrofit2.http.Query

interface FlightsApi {
    @GET("Availability")
    suspend fun getFlights(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("dateout") dateout: String,
        @Query("adt") adults: Int,
        @Query("teen") teens: Int,
        @Query("chd") children: Int,
        @Query("roundtrip") roundTrip: Boolean = true,
        @Query("ToUs") toUs: String = "AGREED"
    ): Flights

//    @GET("Availability")
//    fun getFlights2(
//            @Query("origin") origin: String,
//            @Query("destination") destination: String,
//            @Query("dateout") dateout: String,
//            @Query("adt") adults: Int,
//            @Query("teen") teens: Int,
//            @Query("chd") children: Int,
//            @Query("roundtrip") roundTrip: Boolean = true,
//            @Query("ToUs") toUs: String = "AGREED"
//    ): retrofit2.Call<Flights>
}