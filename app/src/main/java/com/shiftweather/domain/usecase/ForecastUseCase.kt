package com.shiftweather.domain.usecase

import com.shiftweather.domain.model.Forecast
import com.shiftweather.domain.repository.IForecastRepository
import io.reactivex.Single


/**
 *
 * Fetches information from forecast repository to match further consumed by presentation layer.
 *
 * */
class ForecastUseCase constructor(private val forecastRepository: IForecastRepository) {


    fun get(): Single<List<Forecast>> =
        forecastRepository.get()

}






