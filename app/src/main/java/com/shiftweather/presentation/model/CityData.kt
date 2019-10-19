package com.shiftweather.presentation.model

import android.os.Parcelable
import com.shiftweather.domain.model.Forecast
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityData(

    val date: String,
    val cities: List<City>?

) : Parcelable


fun Forecast.mapToPlacesAroundme(): CityData = CityData(
    date,
    mapToCities()
)