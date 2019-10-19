package com.shiftweather.core.network
import retrofit2.Retrofit


abstract class BaseNetworkRequest<T>(private val baseUrl: String, private var clazz: Class<T>) {

    constructor(clazz: Class<T>) : this("https://weather.aw.ee/api/estonia/", clazz)

    protected fun makeRequest(): T {
        return makeRetrofitService(baseUrl).create(this.clazz)
    }

    private fun makeRetrofitService(baseUrl: String): Retrofit {
        return RetrofitManager.getInstance().makeRetrofit(baseUrl)
    }

}