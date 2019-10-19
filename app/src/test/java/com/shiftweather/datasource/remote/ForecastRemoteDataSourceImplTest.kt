package com.shiftweather.datasource.remote

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shiftweather.datasource.entity.mapToDomain
import com.shiftweather.datasource.remote.forecast.ForecastRequest
import com.shiftweather.forecastEntity
import com.shiftweather.forecastsEntity
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ForecastRemoteDataSourceImplTest {

    private lateinit var dataSource: ForecastRemoteDataSourceImpl


    private val forecastRequest: ForecastRequest = mock()

    private val remoteItem = forecastsEntity.copy(forecasts = listOf(forecastEntity))

    private val throwable = Throwable()

    @Before
    fun setUp() {
        dataSource = ForecastRemoteDataSourceImpl(forecastRequest)
    }

    @Test
    fun `get forecast remote success`() {
        // given
        whenever(forecastRequest.getForecastData()).thenReturn(Single.just(remoteItem))

        // when
        val test = dataSource.get().test()

        // then
        verify(forecastRequest).getForecastData()

        test.assertValue(remoteItem.mapToDomain())
    }


    @Test
    fun `get forecast remote fail`() {
        // given
        whenever(forecastRequest.getForecastData()).thenReturn(Single.error(throwable))

        // when
        val test = dataSource.get().test()

        // then
        verify(forecastRequest).getForecastData()
        test.assertError(throwable)
    }

}