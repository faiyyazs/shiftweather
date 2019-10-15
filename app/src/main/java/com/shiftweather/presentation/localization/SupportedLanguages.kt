package com.shiftweather.presentation.localization

import java.util.*

object SupportedLanguages {

    private val English: Locale by lazy { Locale.US }
    private val Estonian: Locale by lazy { Locale("et", "ET") }


    fun getList(): List<Locale>{
        return listOf(English, Estonian)
    }
}