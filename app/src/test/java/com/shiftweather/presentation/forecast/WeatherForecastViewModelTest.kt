package com.shiftweather.presentation.forecast

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.shiftweather.core.presentation.Resource
import com.shiftweather.core.presentation.ResourceState
import com.shiftweather.domain.usecase.ForecastUseCase
import com.shiftweather.forecast
import com.shiftweather.presentation.RxSchedulersOverrideRule
import com.shiftweather.presentation.model.mapToPresentationData
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify

class WeatherForecastViewModelTest {


    private lateinit var viewModel: WeatherForecastViewModel
    private val mockUseCase: ForecastUseCase = mock()
    private val application: Application = mock()

    private val listOfForecast = listOf(forecast)

    @Rule
    @JvmField
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val throwable = Throwable()

    @Before
    fun setUp() {
        viewModel = WeatherForecastViewModel(application, mockUseCase)
    }

    @Test
    fun `get forecast item list succeeds`() {
        // given
        whenever(mockUseCase.get()).thenReturn(Single.just(listOfForecast))

        // when
        viewModel.getForecastData()

        // then
        verify(mockUseCase).get()
        assertEquals(
            Resource(ResourceState.SUCCESS, listOfForecast.mapToPresentationData(), null),
            viewModel.weatherForecast.value
        )
    }


    @Test
    fun `get forecast item list fails`() {
        // given
        whenever(mockUseCase.get()).thenReturn(Single.error(throwable))

        // when
        viewModel.getForecastData()

        // then
        verify(mockUseCase).get()
        assertEquals(
            Resource(ResourceState.ERROR, null, throwable.message),
            viewModel.weatherForecast.value
        )
    }

}