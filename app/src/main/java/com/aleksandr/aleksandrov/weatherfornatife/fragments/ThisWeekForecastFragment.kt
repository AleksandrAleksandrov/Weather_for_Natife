package com.aleksandr.aleksandrov.weatherfornatife.fragments

import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProvider
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
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseActivity.Companion.RESULT_PLACE_AUTOCOMPLETE
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.ListFragmentBase
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.OnResultListener
import com.aleksandr.aleksandrov.weatherfornatife.view_models.WeatherViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.androidannotations.annotations.*
import java.util.*


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

    lateinit var weatherViewModel: WeatherViewModel

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
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
        weatherViewModel = ViewModelProvider(this, factory).get(WeatherViewModel::class.java)
        weatherViewModel.getData().observe(this, android.arch.lifecycle.Observer {
            it?.let {
                rvThisWeekForecast.adapter = weatherAdapter
                weatherAdapter.setAdapterData(it)
            }

        })

        weatherViewModel.isDataFetching().observe(this, android.arch.lifecycle.Observer {
            it?.let {
                mSwipeRefreshLayout.isRefreshing = it
                if (!it) {
                    if (weatherAdapter.getAdapterData().size == 0) fetchFiveDaysForecast()
                }
            }
        })

        weatherViewModel.isRefreshEnabled().observe(this, android.arch.lifecycle.Observer {
            it?.let {
//                mSwipeRefreshLayout.isEnabled = it
            }
        })

        mSwipeRefreshLayout?.let {
            mSwipeRefreshLayout.setOnRefreshListener(this)
            mSwipeRefreshLayout.setColorSchemeResources(firstColor, secondColor, thirdColor)
        }

        weatherAdapter.setSelectCoordinate(View.OnClickListener {
            openMap()
        })

        weatherAdapter.setFindPlaceListener(View.OnClickListener {
            findPlace()
        })
    }

    override fun onRefresh() {
        weatherViewModel.setRefreshEnable(false)
        fetchFiveDaysForecast()
    }


    private fun fetchFiveDaysForecast() = runBlocking {
        weatherViewModel.setDataFetching(true)
        val job = launch {
            serverInteractor.fetchFiveDaysForecast(object : OnResultListener<MutableList<Day>> {

                override fun onResult(result: MutableList<Day>) {
                    weatherViewModel.setData(result)
                    weatherViewModel.setDataFetching(false)
                    weatherViewModel.setRefreshEnable(true)
                }

                override fun onError(error: String) {
                    weatherViewModel.setDataFetching(false)
                    weatherViewModel.setRefreshEnable(true)
                    showMessage(error)
                }

            })
        }
        job.join()
    }

    private fun fetchFiveDaysForecastByCoordinates(lat: Double, lon: Double) = runBlocking {
        weatherViewModel.setDataFetching(true)
        val job = launch {
            serverInteractor.fetchFiveDaysForecastByCoordinates(lat, lon, object : OnResultListener<MutableList<Day>> {

                override fun onResult(result: MutableList<Day>) {
                    weatherViewModel.setData(result)
                    weatherViewModel.setDataFetching(false)
                    weatherViewModel.setRefreshEnable(true)
                }

                override fun onError(error: String) {
                    weatherViewModel.setDataFetching(false)
                    weatherViewModel.setRefreshEnable(true)
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

    fun findPlace() {
        if (!Places.isInitialized()) {
            Places.initialize(context!!, context!!.resources.getString(R.string.google_maps_key))
        }
        val fields = Arrays.asList(Place.Field.ID, Place.Field.NAME)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(context!!)
        startActivityForResult(intent, RESULT_PLACE_AUTOCOMPLETE)
    }

    @OnActivityResult(RESULT_MAP)
    internal fun resultFromMap(resultCode: Int, @OnActivityResult.Extra(value = RESULT_EXTRA_COORDINATES) value: LatLng?) {
        if (resultCode == RESULT_OK) {
            value?.let {
                val lat = it.latitude
                val lon = it.longitude
                fetchFiveDaysForecastByCoordinates(lat, lon)
            }
        }
    }

    @OnActivityResult(RESULT_PLACE_AUTOCOMPLETE)
    internal fun resultFromCityAutocomplete(resultCode: Int, value: Intent) {
        if (resultCode == RESULT_OK) {
            showMessage(Autocomplete.getPlaceFromIntent(value).name!!)
        }
    }

}
