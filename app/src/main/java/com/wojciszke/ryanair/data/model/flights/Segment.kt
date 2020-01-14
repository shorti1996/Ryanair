package com.wojciszke.ryanair.data.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Segment(
        @SerializedName("segmentNr")
        @Expose
        val segmentNr: Int? = null,

        @SerializedName("origin")
        @Expose
        val origin: String? = null,

        @SerializedName("destination")
        @Expose
        val destination: String? = null,

        @SerializedName("flightNumber")
        @Expose
        val flightNumber: String? = null,

        @SerializedName("time")
        @Expose
        val time: List<String>? = null,

        @SerializedName("timeUTC")
        @Expose
        val timeUTC: List<String>? = null,

        @SerializedName("duration")
        @Expose
        val duration: String? = null
)