package com.aleksandr.aleksandrov.weatherfornatife.fragments

import android.Manifest
import android.app.Activity.RESULT_OK
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.activities.MapActivity.Companion.RESULT_EXTRA_COORDINATES
import com.aleksandr.aleksandrov.weatherfornatife.activities.MapActivity_
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeatherAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.ServerInteractor
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseActivity.Companion.PERMISSIONS_REQUEST_LOCATION
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

    //TODO include into view model
    var lat: Double = 0.0
    var lon: Double = 0.0

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
                    if (weatherAdapter.getAdapterData().size == 0) getLocation()
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

        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(activity!!,
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
        }
    }

    fun getLocation() {
        val permission = ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission == PackageManager.PERMISSION_GRANTED) {
            val mLocationManager = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                fetchFiveDaysForecastByCoordinates(location!!.latitude, location!!.longitude)
            } else {
                findPlace()
            }
        }
    }

    override fun onRefresh() {
        weatherViewModel.setRefreshEnable(false)
        fetchFiveDaysForecastByCoordinates(lat, lon)
    }

    fun fetchFiveDaysForecastByCoordinates(lat: Double, lon: Double) = runBlocking {
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
        val fields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG)

        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(context!!)
        startActivityForResult(intent, RESULT_PLACE_AUTOCOMPLETE)
    }

    @OnActivityResult(RESULT_MAP)
    internal fun resultFromMap(resultCode: Int, @OnActivityResult.Extra(value = RESULT_EXTRA_COORDINATES) value: LatLng?) {
        if (resultCode == RESULT_OK) {
            value?.let {
                lat = it.latitude
                lon = it.longitude
                fetchFiveDaysForecastByCoordinates(lat, lon)
            }
        }
    }

    @OnActivityResult(RESULT_PLACE_AUTOCOMPLETE)
    internal fun resultFromCityAutocomplete(resultCode: Int, value: Intent) {
        if (resultCode == RESULT_OK) {
            value?.let {
                lat = Autocomplete.getPlaceFromIntent(value).latLng!!.latitude
                lon = Autocomplete.getPlaceFromIntent(value).latLng!!.longitude
                fetchFiveDaysForecastByCoordinates(lat, lon)
            }
        }
    }

}
