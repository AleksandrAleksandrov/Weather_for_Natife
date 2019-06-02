package com.aleksandr.aleksandrov.weatherfornatife.fragments

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.Utils.WeatherDataHelper
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeatherAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.ResponseCallback
import com.aleksandr.aleksandrov.weatherfornatife.api.Rest
import com.aleksandr.aleksandrov.weatherfornatife.api.models.City
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.ListFragmentBase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.Bean
import org.androidannotations.annotations.EFragment
import org.androidannotations.annotations.ViewById
import retrofit2.Call
import retrofit2.Response

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
@EFragment(R.layout.fragment_this_week_forecast)
open class ThisWeekForecastFragment : ListFragmentBase(), SwipeRefreshLayout.OnRefreshListener {

    @ViewById(R.id.swipeRefresh)
    lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    @ViewById(R.id.rvThisWeekForecast)
    lateinit var rvThisWeekForecast: RecyclerView

    @Bean
    lateinit var weatherAdapter: WeatherAdapter

    var rest: Rest = Rest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isRefreshing = savedInstanceState.getBoolean(IS_REFRESHING)
            isRefreshEnable = savedInstanceState.getBoolean(IS_REFRESH_ENABLED)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        mSwipeRefreshLayout?.let {
            outState.putBoolean(IS_REFRESHING, it.isRefreshing)
            outState.putBoolean(IS_REFRESH_ENABLED, it.isEnabled)
        }
        super.onSaveInstanceState(outState)
    }

    @AfterViews
    fun setUpViews() {
        mSwipeRefreshLayout?.let {
            it.setOnRefreshListener(this)
            it.setColorSchemeResources(firstColor, secondColor, thirdColor)
            it.isRefreshing = isRefreshing
            it.isEnabled = isRefreshEnable
        }

        fetchFiveDaysForecast()
    }

    fun setRefreshEnabled(isRefreshEnabled: Boolean) {
        if (mSwipeRefreshLayout == null) return
        mSwipeRefreshLayout?.let {
            it.isEnabled = isRefreshEnabled
            it.isRefreshing = false
        }
    }

    override fun onRefresh() {
        fetchFiveDaysForecast()
        mSwipeRefreshLayout?.let {
            it.isRefreshing = true
//            setRefreshEnabled(false)
        }
    }

    private fun fetchFiveDaysForecast() = runBlocking {
        val job = launch {
            rest.getAPI().getFiveDaysForecast().enqueue(object : ResponseCallback<City>() {

                override fun onSuccess(call: Call<City>, response: Response<City>) {
                    setRefreshEnabled(true)
                    var days: MutableList<Day> = WeatherDataHelper.sortDays(response.body()!!.list!!)

                    weatherAdapter.setAdapterData(days)
                    rvThisWeekForecast.adapter = weatherAdapter
                    Log.d("tag", response.body()?.cod)
                }

                override fun onError(call: Call<City>, response: Response<City>) {
                    setRefreshEnabled(false)
                    Log.d("tag", "error")
                }

                override fun onFailure(call: Call<City>, t: Throwable) {
                    setRefreshEnabled(false)
                    Log.d("tag", t.toString())
                }
            })
        }
        job.join()
    }

}
