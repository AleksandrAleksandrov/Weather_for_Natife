package com.aleksandr.aleksandrov.weatherfornatife.activities

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.navigation.fragment.NavHostFragment
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseActivity
import com.aleksandr.aleksandrov.weatherfornatife.fragments.ThisWeekForecastFragment
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity
import android.content.Context


@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
open class MainActivity : BaseActivity() {

    @AfterViews
    fun setUpViews() {
        fragmentNavigatorController = R.id.fragment_container
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION -> {
                val thisWeekForecastFragment = getNavHost().childFragmentManager.fragments[0]
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    val mLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    (thisWeekForecastFragment as ThisWeekForecastFragment).fetchFiveDaysForecastByCoordinates(location.latitude, location.longitude)
                } else {
                    (thisWeekForecastFragment as ThisWeekForecastFragment).findPlace()
                }
                return
            }
        }
    }

    fun getNavHost(): NavHostFragment {
        return supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
    }
}
