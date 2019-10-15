package com.shiftweather.datasource.remote.forecast

import com.shiftweather.datasource.entity.ForecastsEntity
import io.reactivex.Single
import retrofit2.http.GET

interface ForecastApi {

    @GET("forecast")
    fun getForecasts(): Single<ForecastsEntity>


}