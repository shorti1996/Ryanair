package com.wojciszke.core.model.stations

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class Station(
    @SerializedName("code")
    @Expose
    val code: String? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("alternateName")
    @Expose
    val alternateName: Any? = null,

    @SerializedName("alias")
    @Expose
    val alias: List<String>? = null,

    @SerializedName("countryCode")
    @Expose
    val countryCode: String? = null,

    @SerializedName("countryName")
    @Expose
    val countryName: String? = null,

    @SerializedName("countryAlias")
    @Expose
    val countryAlias: Any? = null,

    @SerializedName("countryGroupCode")
    @Expose
    val countryGroupCode: String? = null,

    @SerializedName("countryGroupName")
    @Expose
    val countryGroupName: String? = null,

    @SerializedName("timeZoneCode")
    @Expose
    val timeZoneCode: String? = null,

    @SerializedName("latitude")
    @Expose
    val latitude: String? = null,

    @SerializedName("longitude")
    @Expose
    val longitude: String? = null,

    @SerializedName("mobileBoardingPass")
    @Expose
    val mobileBoardingPass: Boolean? = null,

    @SerializedName("markets")
    @Expose
    val markets: List<Market>? = null,

    @SerializedName("notices")
    @Expose
    val notices: Any? = null,

    @SerializedName("tripCardImageUrl")
    @Expose
    val tripCardImageUrl: String? = null
)