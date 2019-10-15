package com.shiftweather.domain.model


data class Weather(
    val phenomenon: PhenomenonType,
    val tempmin: Double?,
    val tempmax: Double?
)

enum class PhenomenonType(val phenomenon : String){


    CLEAR("Clear"),

    FEWCLOUDS( "Few clouds"),
    CLOUDY("Cloudy"),
    VARIABLECLOUDS("Variable clouds"),


    CLOUDYWITHCLEARSPELLS("Cloudy with clear spells"),


    LIGHTSNOWSHOWER("Light snow shower"),
    MODERATESNOWSHOWER("Moderate snow shower"),
    HEAVYSNOWSHOWER("Heavy snow shower"),

    MODERATESHOWER("Moderate shower"),
    HEAVYSHOWER("Heavy shower"),
    LIGHTSHOWER("Light shower"),


    LIGHTRAIN("Light rain"),
    MODERATERAIN("Moderate rain"),
    HEAVYRAIN("Heavy rain"),

    RISKOFGLAZE("Risk of glaze"),

    LIGHTSLEET("Light sleet"),
    MODERATESLEET("Moderate sleet"),



    LIGHTSNOWFALL("Light snowfall"),
    MODERATESNOWFALL("Moderate snowfall"),
    HEAVYSNOWFALL("Heavy snowfall"),

    SNOWSTORM("Snowstorm"),
    DRIFTINGSNOW("Drifting snow"),





    HAIL("Hail"),
    MIST("Mist"),
    FOG("Fog"),
    THUNDER("Thunder"),
    THUNDERSTORM("Thunderstorm"),

    NONE("none");

    companion object {
        fun getValue(type: String): PhenomenonType {
            val values = values()
            for (i in values.iterator()) {
                if (i.phenomenon == type) {
                    return i
                }
            }
            return NONE
        }
    }
}
