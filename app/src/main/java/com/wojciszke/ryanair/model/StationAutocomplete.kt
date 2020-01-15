package com.wojciszke.ryanair.model

import com.wojciszke.core.model.stations.Station

data class StationAutocomplete(val code: String?, val name: String?, val countryName: String?) {
    override fun toString(): String = "$name, $countryName ($code)"
}

fun Station.toStationAutocomplete() =
    StationAutocomplete(code, name, countryName)
