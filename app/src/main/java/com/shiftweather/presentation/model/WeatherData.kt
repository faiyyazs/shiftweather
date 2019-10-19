package com.shiftweather.presentation.model

import com.shiftweather.domain.model.Forecast

data class WeatherData(

    val date: String,
    val weather: BaseWeather,
    val text: String,
    val sea: String?,
    val peipsi: String?,
    val wind: List<WindData>?


)


fun Forecast.mapToDaysWeatherData(): WeatherData = WeatherData(
    date,
    BaseWeather(date, day.weather.phenomenon, day.weather.tempmin, day.weather.tempmax),
    day.text,
    day.sea,
    day.peipsi,
    day.winds?.mapToPresentation()
)


fun Forecast.mapToNightsWeatherData(): WeatherData = WeatherData(
    date,
    BaseWeather(date, night.weather.phenomenon, night.weather.tempmin, night.weather.tempmax),
    night.text,
    night.sea,
    night.peipsi,
    night.winds?.mapToPresentation()
)