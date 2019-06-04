package com.aleksandr.aleksandrov.weatherfornatife.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.activities.MapActivity.Companion.RESULT_EXTRA_COORDINATES
import com.aleksandr.aleksandrov.weatherfornatife.activities.MapActivity_
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeatherAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.ServerInteractor
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseActivity.Companion.RESULT_MAP
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.ListFragmentBase
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.OnResultListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.androidannotations.annotations.*


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

    val serverInteractor = ServerInteractor()

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

        weatherAdapter.setSelectCoordinate(View.OnClickListener {
            openMap()
        })
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
            serverInteractor.fetchFiveDaysForecast(object : OnResultListener<MutableList<Day>> {

                override fun onResult(result: MutableList<Day>) {
                    setRefreshEnabled(true)
                    weatherAdapter.setAdapterData(result)
                    rvThisWeekForecast.adapter = weatherAdapter
                }

                override fun onError(error: String) {
                    setRefreshEnabled(false)
                    showMessage(error)
                }

            })
        }
        job.join()
    }

    private fun fetchFiveDaysForecastByCoordinates(lat: Double, lon: Double) = runBlocking {
        val job = launch {
            serverInteractor.fetchFiveDaysForecastByCoordinates(lat, lon, object : OnResultListener<MutableList<Day>> {

                override fun onResult(result: MutableList<Day>) {
                    setRefreshEnabled(true)
                    weatherAdapter.setAdapterData(result)
                    rvThisWeekForecast.adapter = weatherAdapter
                }

                override fun onError(error: String) {
                    setRefreshEnabled(false)
                    showMessage(error)
                }

            })
        }
        job.join()
    }

    fun openMap() {
        val intent = Intent(context, MapActivity_::class.java)
        startActivityForResult(intent, RESULT_MAP)
    }

    @OnActivityResult(RESULT_MAP)
    internal fun resultFromMap(resultCode: Int, @OnActivityResult.Extra(value = RESULT_EXTRA_COORDINATES) value: LatLng?) {
        if (resultCode == RESULT_OK) {
            val lat = value!!.latitude
            val lon = value!!.longitude
            fetchFiveDaysForecastByCoordinates(lat, lon)
        }
    }

}
