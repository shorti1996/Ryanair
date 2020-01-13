package com.wojciszke.ryanair.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Stations(
    @SerializedName("stations")
    @Expose
    var stations: List<Station>? = null
)