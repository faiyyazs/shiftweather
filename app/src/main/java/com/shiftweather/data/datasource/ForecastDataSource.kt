package com.shiftweather.data.datasource

import com.shiftweather.domain.model.Forecasts
import io.reactivex.Single


/**
 *
 * Data source contracts for multiple data sources. It can be from remote, cache etc.
 * */


/**
 *
 * Forecast data source contract for remote interaction
 * */
interface IForecastRemoteDataSource {

    /**
     *
     * Fetches latest forecast information from remote.
     * */
    fun get(): Single<Forecasts>

}