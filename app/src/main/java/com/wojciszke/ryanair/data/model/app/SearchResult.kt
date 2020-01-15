package com.wojciszke.ryanair.data.model.app

import com.wojciszke.ryanair.data.model.flights.FlightsAvailability

data class SearchResult(
        val flightKey: String,
        val flightDate: String,
        val flightNumber: String,
        val flightDuration: String,
        val regularFarePrice: Double,
        val currency: String,
        val origin: String,
        val destination: String,
        val infantsLeft: Int,
        val fareClass: String,
        val discountInPercent: Int
)

fun fromFlights(flightsAvailability: FlightsAvailability): List<SearchResult> = flightsAvailability.let { availability ->
    availability.trips?.flatMap { trip ->
        trip.dates?.flatMap { date ->
            date.flights?.flatMap { flight ->
                flight.regularFare?.let { regularFare ->
                    regularFare.fares?.map { fare ->
                        SearchResult(
                                flight.flightKey ?: "",
                                date.dateOut ?: "",
                                flight.flightNumber ?: "",
                                flight.duration ?: "",
                                fare.amount ?: -1.0,
                                availability.currency ?: "",
                                trip.origin ?: "",
                                trip.destination ?: "",
                                flight.infantsLeft ?: -1,
                                regularFare.fareClass ?: "",
                                fare.discountInPercent ?: 0
                        )
                    } ?: listOf()
                } ?: listOf()
            } ?: listOf()
        } ?: listOf()
    } ?: listOf()
}