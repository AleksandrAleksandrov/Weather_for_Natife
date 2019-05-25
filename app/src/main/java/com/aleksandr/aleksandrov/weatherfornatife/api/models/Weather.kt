package com.aleksandr.aleksandrov.weatherfornatife.api.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
class Weather {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("main")
    val main: String? = null

    @SerializedName("description")
    val description: String? = null

    @SerializedName("icon")
    val icon: String? = null

}
