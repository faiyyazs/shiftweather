package com.shiftweather.datasource.entity

import com.shiftweather.domain.model.PhenomenonType
import com.shiftweather.domain.model.Place
import com.shiftweather.domain.model.Weather
import com.squareup.moshi.Json


/**
 *
 * Network response entity
 * */
data class PlaceEntity(
    @field:Json(name = "name") val name: String,
    @field:Json(name = "phenomenon") val phenomenon: String,
    @field:Json(name = "tempmin") val tempmin: Double?,
    @field:Json(name = "tempmax") val tempmax: Double?
)


fun PlaceEntity.mapToDomain(): Place =
    Place(name, Weather(PhenomenonType.getValue(phenomenon), tempmin, tempmax))

fun List<PlaceEntity>.mapToDomain(): List<Place> = map { it.mapToDomain() }

