package com.shiftweather.data.repository

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shiftweather.data.datasource.IForecastRemoteDataSource
import com.shiftweather.forecast
import com.shiftweather.forecasts
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class ForecastRepositoryImplTest {


    private lateinit var repository: ForecastRepositoryImpl

    private val mockRemoteDataSource: IForecastRemoteDataSource = mock()

    private val remoteItem =
        listOf(forecast.copy(date = "date"))  //forecasts.copy(forecasts = listOf(forecast))

    private val remoteList = forecasts.copy(forecasts = remoteItem)

    private val remoteThrowable = Throwable()


    @Before
    fun setUp() {
        repository = ForecastRepositoryImpl(mockRemoteDataSource)
    }


    @Test
    fun `get forecasts remote success`() {
        // given
        whenever(mockRemoteDataSource.get()).thenReturn(Single.just(remoteList))

        // when
        val test = repository.get().test()

        // then
        verify(mockRemoteDataSource).get()

        test.assertValue(remoteItem)
    }


    @Test
    fun `get forecasts remote fail`() {
        // given
        whenever(mockRemoteDataSource.get()).thenReturn(Single.error(remoteThrowable))

        // when
        val test = repository.get().test()

        // then
        verify(mockRemoteDataSource).get()
        test.assertError(remoteThrowable)
    }

}