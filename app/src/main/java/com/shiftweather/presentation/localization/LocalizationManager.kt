package com.shiftweather.presentation.localization

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import java.util.*

object LocalizationManager {

    private const val LANGUAGE = "language"
    private const val COUNTRY = "country"


    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            LocalizationManager::class.java.name,
            Context.MODE_PRIVATE
        )
    }

    fun onAttach(context: Context): Context {
        val locale = load(context)
        return setLocale(context, locale)
    }

    fun getLocale(context: Context): Locale {
        return load(context)
    }

    fun setLocale(context: Context, locale: Locale): Context {
        save(context, locale)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateContent(context, locale)
        } else updateContentBackwardCompat(context, locale)

    }

    private fun save(context: Context, locale: Locale?) {
        if (locale == null) return
        getPreferences(context)
            .edit()
            .putString(LANGUAGE, locale.language)
            .putString(COUNTRY, locale.country)
            .apply()
    }

    private fun load(context: Context): Locale {
        val preferences = getPreferences(context)
        val language = preferences.getString(LANGUAGE, Locale.getDefault().language)
        val country = preferences.getString(COUNTRY, Locale.getDefault().country)
        return Locale(language, country)
    }

    @SuppressWarnings("deprecation")
    private fun updateContentBackwardCompat(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateContent(context: Context, locale: Locale): Context {
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }


}