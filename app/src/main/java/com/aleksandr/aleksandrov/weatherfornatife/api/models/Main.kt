package com.aleksandr.aleksandrov.weatherfornatife.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
class Main {

    @SerializedName("temp")
    val temp: Double? = null

    @SerializedName("temp_min")
    val tempMin: Double? = null

    @SerializedName("temp_max")
    val tempMax: Double? = null

    @SerializedName("pressure")
    val pressure: Double? = null

    @SerializedName("sea_level")
    val seaLevel: Double? = null

    @SerializedName("grnd_level")
    val grndLevel: Double? = null

    @SerializedName("humidity")
    val humidity: Int? = null

    @SerializedName("temp_kf")
    val tempKf: Double? = null

}
