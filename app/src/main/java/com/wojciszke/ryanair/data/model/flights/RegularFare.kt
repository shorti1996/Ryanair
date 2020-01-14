package com.wojciszke.ryanair.data.model.flights

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class RegularFare(
        @SerializedName("fareKey")
        @Expose
        val fareKey: String? = null,

        @SerializedName("fareClass")
        @Expose
        val fareClass: String? = null,

        @SerializedName("fares")
        @Expose
        val fares: List<Fare>? = null
)