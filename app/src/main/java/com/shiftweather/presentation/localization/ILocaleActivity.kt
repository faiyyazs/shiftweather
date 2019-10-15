package com.shiftweather.presentation.localization

import android.app.Activity
import android.content.Context
import java.util.*

interface ILocaleActivity {
    fun setLocale(activity: Activity, newLocale: Locale)
    fun attachBaseContext(newBase: Context): Context
    fun onPaused()
    fun onResumed(activity: Activity)
}