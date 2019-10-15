package com.shiftweather.presentation.model

enum class WeatherTab(val position: Int) {

    DAY(0),
    NIGHT(1),
    NONE(-1);

    companion object {
        fun getValue(pos: Int): WeatherTab {
            val values = values()
            for (i in values.iterator()) {
                if (i.position == pos) {
                    return i
                }
            }
            return NONE
        }
    }
}


