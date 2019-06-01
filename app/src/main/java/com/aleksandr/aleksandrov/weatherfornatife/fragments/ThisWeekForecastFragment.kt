package com.aleksandr.aleksandrov.weatherfornatife.fragments

import android.support.v7.widget.RecyclerView
import android.util.Log
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.Utils.WeatherDataHelper
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeatherAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.ResponseCallback
import com.aleksandr.aleksandrov.weatherfornatife.api.Rest
import com.aleksandr.aleksandrov.weatherfornatife.api.models.City
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.api.models.DayInfo
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.ListFragmentBase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
@EFragment(R.layout.fragment_this_week_forecast)
open class ThisWeekForecastFragment : ListFragmentBase() {

    @ViewById(R.id.rvThisWeekForecast)
    lateinit var rvThisWeekForecast: RecyclerView

    @Bean
    lateinit var weatherAdapter: WeatherAdapter

    var rest: Rest = Rest()

    @AfterViews
    fun setUpViews() {
        fetchFiveDaysForecast()
    }

    private fun fetchFiveDaysForecast() = runBlocking {
        val job = launch {
            rest.getAPI().getFiveDaysForecast().enqueue(object : ResponseCallback<City>() {

                override fun onSuccess(call: Call<City>, response: Response<City>) {

                    var days: MutableList<Day> = WeatherDataHelper.sortDays(response.body()!!.list!!)

                    weatherAdapter.setAdapterData(days)
                    rvThisWeekForecast.adapter = weatherAdapter
                    Log.d("tag", response.body()?.cod)
                }

                override fun onError(call: Call<City>, response: Response<City>) {
                    Log.d("tag", "error")
                }

                override fun onFailure(call: Call<City>, t: Throwable) {
                    Log.d("tag", t.toString())
                }
            })
        }
        job.join()
    }


}
