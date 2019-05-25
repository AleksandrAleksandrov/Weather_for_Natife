package com.aleksandr.aleksandrov.weatherfornatife.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
class DayInfo {

    @SerializedName("dt")
    val dt: Int? = null

    @SerializedName("main")
    val main: Main? = null

    @SerializedName("weather")
    val weather: List<Weather>? = null

    @SerializedName("clouds")
    val clouds: Clouds? = null

    @SerializedName("wind")
    val wind: Wind? = null

    @SerializedName("sys")
    val sys: Sys? = null

    @SerializedName("dt_txt")
    val dtTxt: String? = null

}