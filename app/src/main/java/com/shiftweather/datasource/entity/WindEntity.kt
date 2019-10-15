package com.shiftweather.datasource.entity


import com.shiftweather.domain.model.Wind
import com.squareup.moshi.Json


/**
 *
 * Network response entity
 * */
data class WindEntity(

    @field:Json(name = "name") val name: String,
    @field:Json(name = "direction") val direction: String,
    @field:Json(name = "speedmin") val speedmin: Double?,
    @field:Json(name = "speedmax") val speedmax: Double?
)


fun WindEntity.mapToDomain(): Wind =
    Wind(name, direction, speedmin, speedmax)

fun List<WindEntity>.mapToDomain(): List<Wind> = map { it.mapToDomain() }
