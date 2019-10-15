package com.shiftweather.core.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class LiveDataExtTest {

    private lateinit var liveData: MutableLiveData<Resource<Int>>
    private val data = 1
    private val errorMessage = "Error"

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        liveData = MutableLiveData()
    }

    @Test
    fun `loading to success`() {
        // given
        liveData.loading()

        // when
        liveData.success(data)

        // then
        assertEquals(Resource(ResourceState.SUCCESS, data, null), liveData.value)
    }

    @Test
    fun `loading to success to loading`() {
        // given
        liveData.loading()
        liveData.success(data)

        // when
        liveData.loading()

        // then
        assertEquals(Resource(ResourceState.LOADING, data, null), liveData.value)
    }

    @Test
    fun `loading to success to loading to error`() {
        // given
        liveData.loading()
        liveData.success(data)
        liveData.loading()

        // when
        liveData.error(errorMessage)

        // then
        assertEquals(Resource(ResourceState.ERROR, data, errorMessage), liveData.value)
    }

    @Test
    fun `loading to error`() {
        // given
        liveData.loading()

        // when
        liveData.error(errorMessage)

        // then
        assertEquals(Resource(ResourceState.ERROR, null, errorMessage), liveData.value)
    }

    @Test
    fun `loading to error to loading to success`() {
        // given
        liveData.loading()
        liveData.error(errorMessage)
        liveData.loading()

        // when
        liveData.success(data)

        // then
        assertEquals(Resource(ResourceState.SUCCESS, data, null), liveData.value)
    }

}