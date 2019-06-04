package com.aleksandr.aleksandrov.weatherfornatife.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

/**
 * Created by Alexandrov Alex on 2019-06-03.
 */
@SuppressLint("Registered")
@EActivity(R.layout.activity_map)
open class MapActivity : FragmentActivity(), OnMapReadyCallback {

    companion object {
        const val RESULT_EXTRA_COORDINATES = "result_extra_coordinates"
    }

    @AfterViews
    fun initViews() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.let {
            it.setOnMapClickListener {
                val result = Intent()
                result.putExtra(RESULT_EXTRA_COORDINATES, it)
                setResult(Activity.RESULT_OK, result)
                finish()
            }
        }
    }
}
