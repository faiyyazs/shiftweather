package com.shiftweather.datasource.remote

import com.shiftweather.data.datasource.IForecastRemoteDataSource
import com.shiftweather.datasource.entity.mapToDomain
import com.shiftweather.datasource.remote.forecast.ForecastRequest
import com.shiftweather.domain.model.Forecasts
import io.reactivex.Single


/**
 *
 * Forecast remote data source implementation.
 * */
class ForecastRemoteDataSourceImpl constructor(
    private val request: ForecastRequest
) : IForecastRemoteDataSource {

    /**
     *
     * Fetches latest forecast information from remote.
     * */
    override fun get(): Single<Forecasts> {
        return request.getForecastData().map { it.mapToDomain() }
    }


}