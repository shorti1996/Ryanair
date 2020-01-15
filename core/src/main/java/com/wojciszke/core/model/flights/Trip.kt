package com.wojciszke.core.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Trip(
        @SerializedName("origin")
        @Expose
        val origin: String? = null,

        @SerializedName("originName")
        @Expose
        val originName: String? = null,

        @SerializedName("destination")
        @Expose
        val destination: String? = null,

        @SerializedName("destinationName")
        @Expose
        val destinationName: String? = null,

        @SerializedName("dates")
        @Expose
        val dates: List<FlightDate>? = null
)