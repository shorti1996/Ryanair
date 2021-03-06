package com.wojciszke.core.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class FlightsAvailability(
    @SerializedName("termsOfUse")
        @Expose
        val termsOfUse: String? = null,

    @SerializedName("currency")
        @Expose
        val currency: String? = null,

    @SerializedName("currPrecision")
        @Expose
        val currPrecision: Int? = null,

    @SerializedName("trips")
        @Expose
        val trips: List<Trip>? = null,

    @SerializedName("serverTimeUTC")
        @Expose
        val serverTimeUTC: String? = null
)

