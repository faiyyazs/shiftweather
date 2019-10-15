package com.shiftweather.domain.model


data class Forecast(
    val date: String,
    val day: Period,
    val night: Period
)


