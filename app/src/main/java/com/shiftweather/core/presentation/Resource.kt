package com.shiftweather.core.presentation

/**
 * A generic class to hold its own loading status
 * @param <T>
</T> */
data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val message: String? = null
)