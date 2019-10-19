package com.shiftweather.datasource.model

import com.shiftweather.datasource.entity.mapToDomain
import com.shiftweather.forecastEntity
import org.junit.Assert.assertTrue
import org.junit.Test

class ForecastEntityTest {


    @Test
    fun `map entity to domain`() {
        // given

        // when
        val model = forecastEntity.mapToDomain()

        // then
        assertTrue(model.date == forecastEntity.date)

    }
}