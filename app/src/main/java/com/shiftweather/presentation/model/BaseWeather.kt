package com.shiftweather.presentation.model

import android.os.Parcelable
import com.shiftweather.domain.model.PhenomenonType
import com.shiftweather.domain.model.Weather
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseWeather(
    val date: String,
    val phenomenon: PhenomenonType,
    val tempmin: Double?,
    val tempmax: Double?
): Parcelable


fun Weather.mapToPresentation(date : String)= BaseWeather(
    date =  date,
    phenomenon = phenomenon,
    tempmax = tempmax,
    tempmin = tempmin
)

