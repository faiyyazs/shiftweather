package com.shiftweather

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.shiftweather.presentation.localization.LocalizationManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class ShiftApplication: Application() {

    init { shiftApplication = this }

    override fun onCreate() {
        super.onCreate()
        startKoin { androidContext(this@ShiftApplication) }
    }

    companion object {
       lateinit var shiftApplication: ShiftApplication
            private set

        val applicationContext: Context get() { return shiftApplication.applicationContext }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalizationManager.onAttach(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalizationManager.onAttach(this)
    }

    fun hasNetwork(): Boolean {
        return isNetworkAvailable()
    }

    private fun isNetworkAvailable(): Boolean {
        var isConnected = false
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}