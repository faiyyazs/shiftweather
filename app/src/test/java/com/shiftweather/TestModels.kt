package com.shiftweather

import com.shiftweather.datasource.entity.*
import com.shiftweather.domain.model.*


val wind = Wind(name = "name", direction = "direction", speedmin = 1.0, speedmax = 1.0)
val weather = Weather(phenomenon = PhenomenonType.RISKOFGLAZE, tempmin = 10.0, tempmax = 15.0)
val place = Place(name = "place", weather = weather)
val dayPeriod = Period(weather, "text", "sea", "peipsi", listOf(place), listOf(wind))
val nightPeriod = Period(weather, "text", "sea", "peipsi", listOf(place), listOf(wind))


val forecast = Forecast("date", dayPeriod, nightPeriod)
val forecasts = Forecasts(listOf(forecast))


val windEntity = WindEntity("name", "direction", 1.0, 1.0)
val placeEntity = PlaceEntity("name", "phenomenon", 1.0, 1.0)
val periodEntity = PeriodEntity(
    "description", "phenomenon",
    1.0, 1.0, "text", "sea", "peipso", listOf(placeEntity), listOf(windEntity)
)
val forecastEntity = ForecastEntity("date", periodEntity, periodEntity)
val forecastsEntity = ForecastsEntity(listOf(forecastEntity))




