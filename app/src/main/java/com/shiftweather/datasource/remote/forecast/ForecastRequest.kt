package com.shiftweather.datasource.remote.forecast

import com.shiftweather.core.network.BaseNetworkRequest
import com.shiftweather.datasource.entity.ForecastsEntity
import io.reactivex.Single


/**
 * A class used to make remote api requests
 *
 * */
class ForecastRequest : BaseNetworkRequest<ForecastApi>(ForecastApi::class.java) {


    fun getForecastData(): Single<ForecastsEntity> {
        return makeRequest().getForecasts()
    }

}