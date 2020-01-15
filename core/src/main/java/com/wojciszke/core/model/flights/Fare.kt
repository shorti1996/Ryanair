package com.wojciszke.core.model.flights

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Fare(
        @SerializedName("type")
        @Expose
        val type: String? = null,

        @SerializedName("amount")
        @Expose
        val amount: Double? = null,

        @SerializedName("count")
        @Expose
        val count: Int? = null,

        @SerializedName("hasDiscount")
        @Expose
        val hasDiscount: Boolean? = null,

        @SerializedName("publishedFare")
        @Expose
        val publishedFare: Double? = null,

        @SerializedName("discountInPercent")
        @Expose
        val discountInPercent: Int? = null,

        @SerializedName("hasPromoDiscount")
        @Expose
        val hasPromoDiscount: Boolean? = null
)