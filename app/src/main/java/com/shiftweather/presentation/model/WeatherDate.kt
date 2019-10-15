package com.shiftweather.presentation.model

import com.shiftweather.domain.model.Forecast

data class WeatherDate(
    val date: String = "date"
)

fun Forecast.mapToWeatherDate(): WeatherDate = WeatherDate(date)