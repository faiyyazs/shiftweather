package com.shiftweather.datasource.entity

import com.shiftweather.domain.model.Forecast
import com.squareup.moshi.Json

/**
 *
 * Network response entity
 * */
data class ForecastEntity(
    @field:Json(name = "date") val date: String,
    @field:Json(name = "day") val day: PeriodEntity,
    @field:Json(name = "night") val night: PeriodEntity
)

fun ForecastEntity.mapToDomain(): Forecast =
    Forecast(date = date, day = day.mapToDomain(), night = night.mapToDomain())

fun List<ForecastEntity>.mapToDomain(): List<Forecast> = map { it.mapToDomain() }

