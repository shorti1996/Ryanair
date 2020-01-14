package com.wojciszke.ryanair.data.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Flight(
        @SerializedName("faresLeft")
        @Expose
        val faresLeft: Int? = null,

        @SerializedName("flightKey")
        @Expose
        val flightKey: String? = null,

        @SerializedName("infantsLeft")
        @Expose
        val infantsLeft: Int? = null,

        @SerializedName("regularFare")
        @Expose
        val regularFare: RegularFare? = null,

        @SerializedName("operatedBy")
        @Expose
        val operatedBy: String? = null,

        @SerializedName("segments")
        @Expose
        val segments: List<Segment>? = null,

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