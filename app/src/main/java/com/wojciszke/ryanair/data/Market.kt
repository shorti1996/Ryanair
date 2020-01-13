package com.wojciszke.ryanair.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Market(
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("group")
    @Expose
    var group: Any? = null
)