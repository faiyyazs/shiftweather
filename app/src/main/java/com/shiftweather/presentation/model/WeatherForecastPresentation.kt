package com.shiftweather.presentation.model

import com.shiftweather.domain.model.Forecast


data class WeatherForecastPresentation(
    val dates: List<WeatherDate>,
    val days: List<WeatherData>,
    val nights: List<WeatherData>,
    val cities: List<CityData>?
)


fun List<Forecast>.mapToPresentationData(): WeatherForecastPresentation =  WeatherForecastPresentation(

        dates = map {
            forecast -> forecast.mapToWeatherDate()
        },

        days = map {
                forecast ->
            forecast.mapToDaysWeatherData()
        },
        nights = map {
                forecast ->
            forecast.mapToNightsWeatherData()
        },
        cities = map {
                forecast ->
            forecast.mapToPlacesAroundme()
        }
)













