package com.wojciszke.ryanair.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wojciszke.ryanair.data.model.api.Station


data class Stations(
    @SerializedName("stations")
    @Expose
    var stations: List<Station>? = null
)