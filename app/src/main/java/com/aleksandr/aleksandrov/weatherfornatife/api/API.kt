package com.aleksandr.aleksandrov.weatherfornatife.api

import com.aleksandr.aleksandrov.weatherfornatife.api.models.City
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
interface API {

    @GET("data/2.5/forecast?q=London,us&mode=json&appid=cc6b06d93f29fe8e82886a5fe07c1bb5")
    fun getFiveDaysForecast(): Call<City>

}
