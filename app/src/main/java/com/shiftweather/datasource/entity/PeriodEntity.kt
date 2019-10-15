package com.shiftweather.datasource.entity

import com.shiftweather.domain.model.Period
import com.shiftweather.domain.model.PhenomenonType

import com.shiftweather.domain.model.Weather
import com.squareup.moshi.Json

/**
 *
 * Network response entity
 * */
data class PeriodEntity(

    @field:Json(name = "description") val description: String,
    @field:Json(name = "phenomenon") val phenomenon: String,
    @field:Json(name = "tempmin") val tempmin: Double,
    @field:Json(name = "tempmax") val tempmax: Double,
    @field:Json(name = "text") val text: String,
    @field:Json(name = "sea") val sea: String?,
    @field:Json(name = "peipsi") val peipsi: String?,
    @field:Json(name = "places") val places: List<PlaceEntity>?,
    @field:Json(name = "winds") val winds: List<WindEntity>?

)


fun PeriodEntity.mapToDomain(): Period =
    Period(
        Weather(PhenomenonType.getValue(phenomenon), tempmin, tempmax),
        text,
        sea,
        peipsi,
        cities = places?.mapToDomain(),
        winds = winds?.mapToDomain()
    )

fun List<PeriodEntity>.mapToDomain(): List<Period> = map { it.mapToDomain() }

