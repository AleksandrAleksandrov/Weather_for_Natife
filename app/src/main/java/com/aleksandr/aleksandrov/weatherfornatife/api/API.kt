package com.aleksandr.aleksandrov.weatherfornatife.api

import com.aleksandr.aleksandrov.weatherfornatife.api.models.City
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
interface API {

    @GET("data/2.5/forecast?q=kiev,ua&mode=json&&units=metric&appid=" + APIConstants.APP_ID)
    fun getFiveDaysForecast(): Call<City>

    @GET("data/2.5/forecast?mode=json&&units=metric&appid=" + APIConstants.APP_ID)
    fun getFiveDaysForecastByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<City>

}
