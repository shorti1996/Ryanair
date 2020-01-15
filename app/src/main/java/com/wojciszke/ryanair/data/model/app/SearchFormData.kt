package com.wojciszke.ryanair.data.model.app

data class SearchFormData(
        val origin: String,
        val destination: String,
        val dateOut:String,
        val adults: Int,
        val teens: Int,
        val children: Int
)