package com.aleksandr.aleksandrov.weatherfornatife.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
class City {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("coord")
    val coord: Coord? = null

    @SerializedName("country")
    val country: String? = null

    @SerializedName("cod")
    val cod: String? = null

    @SerializedName("message")
    val message: Double? = null

    @SerializedName("cnt")
    val cnt: Int? = null

    @SerializedName("list")
    val list: List<DayInfo>? = null

}
