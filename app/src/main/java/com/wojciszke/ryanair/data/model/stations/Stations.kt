package com.wojciszke.ryanair.data.model.stations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Stations(
    @SerializedName("stations")
    @Expose
    val stations: List<Station>? = null
)