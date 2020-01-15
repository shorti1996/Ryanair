package com.wojciszke.ryanair.data.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FlightDate(
        @SerializedName("dateOut")
        @Expose
        val dateOut: String? = null,

        @SerializedName("flights")
        @Expose
        val flights: List<Flight>? = null
)