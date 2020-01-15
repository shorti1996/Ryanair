package com.wojciszke.ryanair.data.model.app

import com.wojciszke.ryanair.data.model.flights.FlightsAvailability

data class SearchResult(
        val flightDate: String,
        val flightNumber: String,
        val flightDuration: String,
        val regularFarePrice: String,
        val currency: String
)

fun fromFlights(flightsAvailability: FlightsAvailability): List<SearchResult> = flightsAvailability.let { availability ->
    availability.trips?.flatMap { trip ->
        trip.dates?.flatMap { date ->
            date.flights?.flatMap { flight ->
                flight.regularFare?.let { regularFare ->
                    regularFare.fares?.map { fare ->
                        SearchResult(
                                date.dateOut ?: "",
                                flight.flightNumber ?: "",
                                flight.duration ?: "",
                                fare.amount?.toString() ?: "",
                                availability.currency ?: ""
                        )
                    } ?: listOf()
                } ?: listOf()
            } ?: listOf()
        } ?: listOf()
    } ?: listOf()
}