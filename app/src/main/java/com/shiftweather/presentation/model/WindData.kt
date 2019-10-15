package com.shiftweather.presentation.model

import com.shiftweather.domain.model.Wind

data class WindData(

    val name: String,
    val direction: WindDirection,
    val speedmin: Double?,
    val speedmax: Double?

)


fun Wind.mapToPresentation(): WindData = WindData(
    name = name,
    speedmin = speedmin,
    speedmax = speedmax,
    direction = WindDirection.getValue(direction)
)

fun List<Wind>.mapToPresentation(): List<WindData> = map {
    it.mapToPresentation()
}


enum class WindDirection(val direction: String) {

    SOUTH("South wind"),
    SOUTHWEST("Southwest wind"),
    WEST("West wind"),
    NORTHWEST("Northwest wind"),
    NORTH("North wind"),
    NORTHEAST("Northeast wind"),
    EAST("East wind"),
    SOUTHEAST("Southeast wind"),
    NONE("");

    companion object {
        fun getValue(type: String): WindDirection {
            val values = values()
            for (i in values.iterator()) {
                if (i.direction == type) {
                    return i
                }
            }
            return NONE
        }
    }
}

