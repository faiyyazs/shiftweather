package com.shiftweather.domain.model

data class Period(

    val weather: Weather,
    val text: String,
    val sea: String?,
    val peipsi: String?,


    val cities: List<Place>?,
    val winds: List<Wind>?

)

