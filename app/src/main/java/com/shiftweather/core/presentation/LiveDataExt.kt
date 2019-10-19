package com.shiftweather.core.presentation

import androidx.lifecycle.MutableLiveData


/**
 * LiveData Extensions to load data with its corresponding status
 * */

fun <T> MutableLiveData<Resource<T>>.success(data: T) =
    postValue(Resource(ResourceState.SUCCESS, data))

fun <T> MutableLiveData<Resource<T>>.loading() =
    postValue(Resource(ResourceState.LOADING, value?.data))

fun <T> MutableLiveData<Resource<T>>.error(message: String? = null) =
    postValue(Resource(ResourceState.ERROR, value?.data, message))