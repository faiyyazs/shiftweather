package com.shiftweather.core.network

import com.shiftweather.ShiftApplication
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


class RetrofitManager {


    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val myCache = Cache(ShiftApplication.applicationContext.cacheDir, cacheSize)
    private val CACHE_CONTROL = "Cache-Control"
    private val PRAGMA = "Pragma"

    private val logging by lazy {
        makeLoggingInterceptor()
    }
    private val offlineInterceptor by lazy {
        offlineInterceptor()
    }
    private val networkInterceptor by lazy {
        networkInterceptor()
    }
    private val okHttpClient by lazy {
        makeOkHttpClient()
    }

    companion object {

        @Volatile
        private var instance: RetrofitManager? = null

        fun getInstance(): RetrofitManager {

            val checkInstance = instance

            if (checkInstance != null) {
                return checkInstance
            }

            return synchronized(this) {
                val checkInstanceAgain = instance
                if (checkInstanceAgain != null) {
                    checkInstanceAgain
                } else {
                    instance = RetrofitManager()
                    instance!!

                }
            }
        }
    }

    private val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
    }

    /**
     * Uses single instance for Retrofit as multiple instances leads to loss in app performance.
     * @ref https://futurestud.io/tutorials/retrofit-2-share-okhttp-client-and-converters-between-retrofit-instances
     * */
    fun makeRetrofit(baseUrl: String): Retrofit {
        return retrofitBuilder
            .baseUrl(baseUrl)
            .build()
    }

    private fun makeOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor(networkInterceptor) // only used when network is on
            .addInterceptor(offlineInterceptor)
            .cache(myCache)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.apply { logging.level = HttpLoggingInterceptor.Level.BODY }
        return logging
    }


    /**
     * This interceptor will be called both if the network is available and if the network is not available
     */
    private fun offlineInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {

                var request = chain.request()

                if (!ShiftApplication.shiftApplication.hasNetwork()) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                    request = request.newBuilder()
                        .removeHeader(PRAGMA)
                        .removeHeader(CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                }

                return chain.proceed(request)
            }
        }
    }

    /**
     * This interceptor will be called ONLY if the network is available
     */
    private fun networkInterceptor(): Interceptor {
        return object : Interceptor {

            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {

                val response = chain.proceed(chain.request())

                val cacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()

                return response.newBuilder()
                    .removeHeader(PRAGMA)
                    .removeHeader(CACHE_CONTROL)
                    .header(CACHE_CONTROL, cacheControl.toString())
                    .build()
            }
        }
    }


}