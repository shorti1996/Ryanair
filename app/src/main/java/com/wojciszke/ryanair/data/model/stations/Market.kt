package com.wojciszke.ryanair.data.model.stations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Market(
    @SerializedName("code")
    @Expose
    val code: String? = null,

    @SerializedName("group")
    @Expose
    val group: Any? = null
)