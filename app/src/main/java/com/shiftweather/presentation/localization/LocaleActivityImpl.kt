package com.shiftweather.presentation.localization

import android.app.Activity
import android.content.Context
import java.util.*

class LocaleActivityImpl : ILocaleActivity {

    private var locale: Locale = Locale.getDefault()

    override fun setLocale(activity: Activity, newLocale: Locale) {
        LocalizationManager.setLocale(activity, newLocale)
        locale = newLocale
        activity.recreate()
    }

    override fun attachBaseContext(newBase: Context): Context {
        locale = LocalizationManager.getLocale(newBase)
        return LocalizationManager.onAttach(newBase)
    }

    override fun onPaused() {
        locale = Locale.getDefault()
    }

    override fun onResumed(activity: Activity) {
     if (locale == Locale.getDefault()) {
          return
     }
     activity.recreate()
    }
}