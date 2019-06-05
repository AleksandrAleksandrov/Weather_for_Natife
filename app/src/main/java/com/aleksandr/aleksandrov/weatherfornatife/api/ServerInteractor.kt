package com.aleksandr.aleksandrov.weatherfornatife.api

import com.aleksandr.aleksandrov.weatherfornatife.Utils.WeatherDataHelper
import com.aleksandr.aleksandrov.weatherfornatife.api.models.City
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.OnResultListener
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Alexandrov Alex on 2019-06-04.
 */
open class ServerInteractor {

    var rest: Rest = Rest()

    fun fetchFiveDaysForecastByCoordinates(lat: Double, lon: Double, resultList: OnResultListener<MutableList<Day>>) {
        rest.getAPI().getFiveDaysForecastByCoordinates(lat, lon).enqueue(object : ResponseCallback<City>() {

            override fun onSuccess(call: Call<City>, response: Response<City>) {
                val days: MutableList<Day> = WeatherDataHelper.sortDays(response.body()!!.list!!)
                days.iterator().forEach {
                    it.cityName = response.body()!!.city!!.name
                }
                resultList.onResult(days)

            }

            override fun onError(call: Call<City>, response: Response<City>) {
                resultList.onError(response.message())
            }

            override fun onFailure(call: Call<City>, t: Throwable) {
                resultList.onError(t.message.toString())
            }
        })
    }
}
