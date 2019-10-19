package com.shiftweather.presentation.utils

import android.content.Context
import com.shiftweather.R
import com.shiftweather.domain.model.PhenomenonType
import com.shiftweather.presentation.model.WindDirection
import java.text.SimpleDateFormat
import java.util.*

/**
 * Helper method to provide the icon resource id according to the phenomena.
 */
fun PhenomenonType.icon(): Int {

    return when (this) {

        PhenomenonType.CLEAR -> R.drawable.weathercons_clear
        PhenomenonType.FEWCLOUDS, PhenomenonType.VARIABLECLOUDS -> R.drawable.weathercons_partly_cloudy
        PhenomenonType.CLOUDY -> R.drawable.weathercons_cloudy
        PhenomenonType.CLOUDYWITHCLEARSPELLS -> R.drawable.weathercons_partly_cloudy
        PhenomenonType.LIGHTSHOWER, PhenomenonType.LIGHTRAIN, PhenomenonType.MODERATERAIN, PhenomenonType.HEAVYRAIN -> R.drawable.weathercons_rain
        PhenomenonType.LIGHTSNOWFALL, PhenomenonType.MODERATESNOWFALL, PhenomenonType.HEAVYSNOWFALL -> R.drawable.weathercons_snowfall
        PhenomenonType.RISKOFGLAZE -> R.drawable.weathercons_snow
        PhenomenonType.SNOWSTORM, PhenomenonType.DRIFTINGSNOW -> R.drawable.weathercons_snowfall
        PhenomenonType.LIGHTSNOWSHOWER, PhenomenonType.MODERATESNOWSHOWER, PhenomenonType.HEAVYSNOWSHOWER, PhenomenonType.HEAVYSHOWER, PhenomenonType.MODERATESHOWER, PhenomenonType.LIGHTSLEET, PhenomenonType.MODERATESLEET -> R.drawable.weathercons_shower
        PhenomenonType.HAIL -> R.drawable.weathercons_hail
        PhenomenonType.MIST -> R.drawable.weathercons_mist
        PhenomenonType.FOG -> R.drawable.weathercons_fog
        PhenomenonType.THUNDER -> R.drawable.weathercons_thunder
        PhenomenonType.THUNDERSTORM -> R.drawable.weathercons_thunderstorm
        else -> R.drawable.weathercons_none
    }
}


/**
 *
 * Helper method to provide the icon resource id according to the weather directions.
 *
 * */
fun WindDirection.icon(): Int {

    return when (this) {
        WindDirection.SOUTH -> 270
        WindDirection.SOUTHWEST -> 315
        WindDirection.WEST -> 0
        WindDirection.NORTHWEST -> 45
        WindDirection.NORTH -> 90
        WindDirection.NORTHEAST -> 135
        WindDirection.EAST -> 180
        WindDirection.SOUTHEAST -> 225
        else -> 0
    }
}


private val responseDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
private val titleDateFormat = SimpleDateFormat("E, dd MMM", Locale.US)
private val titleForecastDateformat = SimpleDateFormat("dd/MM", Locale.US)

fun getForecastDateformat(date: String): String {
    return titleForecastDateformat.format(responseDateFormat.parse(date))
}

fun getTitleDateFormat(date: String): String {
    return titleDateFormat.format(responseDateFormat.parse(date))
}

fun getWindsRepresentation(context: Context, speedMin: Double, speedMax: Double): String {
    return String.format(context.getString(R.string.format_winds), speedMin, speedMax)
}

fun getDegreesRepresentation(context: Context, temperature: Double): String {
    return String.format(context.getString(R.string.format_temperature), temperature)
}

