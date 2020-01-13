package com.wojciszke.ryanair.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.wojciszke.ryanair.data.model.Market


data class Station(
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("alternateName")
    @Expose
    var alternateName: Any? = null,

    @SerializedName("alias")
    @Expose
    var alias: List<String>? = null,

    @SerializedName("countryCode")
    @Expose
    var countryCode: String? = null,

    @SerializedName("countryName")
    @Expose
    var countryName: String? = null,

    @SerializedName("countryAlias")
    @Expose
    var countryAlias: Any? = null,

    @SerializedName("countryGroupCode")
    @Expose
    var countryGroupCode: String? = null,

    @SerializedName("countryGroupName")
    @Expose
    var countryGroupName: String? = null,

    @SerializedName("timeZoneCode")
    @Expose
    var timeZoneCode: String? = null,

    @SerializedName("latitude")
    @Expose
    var latitude: String? = null,

    @SerializedName("longitude")
    @Expose
    var longitude: String? = null,

    @SerializedName("mobileBoardingPass")
    @Expose
    var mobileBoardingPass: Boolean? = null,

    @SerializedName("markets")
    @Expose
    var markets: List<Market>? = null,

    @SerializedName("notices")
    @Expose
    var notices: Any? = null,

    @SerializedName("tripCardImageUrl")
    @Expose
    var tripCardImageUrl: String? = null
)