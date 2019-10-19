package com.shiftweather.domain.usecases

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shiftweather.domain.repository.IForecastRepository
import com.shiftweather.domain.usecase.ForecastUseCase
import com.shiftweather.forecast
import com.shiftweather.forecasts
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ForecastUseCaseTest {


    private lateinit var forecastUseCase: ForecastUseCase

    private val mockForecastRepository: IForecastRepository = mock()

    private val remoteItem = listOf(forecast)

    private val forecastList = forecasts.copy(forecasts = remoteItem)

    @Before
    fun setup() {
        forecastUseCase = ForecastUseCase(mockForecastRepository)
    }

    @Test
    fun `repository get success`() {
        // given

        whenever(mockForecastRepository.get()).thenReturn(Single.just(remoteItem))

        // when
        val test = forecastUseCase.get().test()

        // then

        verify(mockForecastRepository).get()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)

    }


    @Test
    fun `repository get fail`() {
        // given
        val throwable = Throwable()

        whenever(mockForecastRepository.get()).thenReturn(Single.error(throwable))

        // when
        val test = forecastUseCase.get().test()

        // then
        verify(mockForecastRepository).get()

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

}