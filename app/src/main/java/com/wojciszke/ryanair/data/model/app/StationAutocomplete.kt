package com.wojciszke.ryanair.data.model.app

import com.wojciszke.ryanair.data.model.api.Station

data class StationAutocomplete(val code: String?, val name: String?, val countryName: String?) {
    override fun toString(): String = "$name, $countryName ($code)"
}

fun Station.toStationAutocomplete() = StationAutocomplete(code, name, countryName)
