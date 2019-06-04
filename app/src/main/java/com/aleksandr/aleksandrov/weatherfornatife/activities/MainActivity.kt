package com.aleksandr.aleksandrov.weatherfornatife.activities

import android.annotation.SuppressLint
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseActivity
import org.androidannotations.annotations.AfterViews
import org.androidannotations.annotations.EActivity

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
open class MainActivity : BaseActivity() {

    @AfterViews
    fun setUpViews() {
        fragmentNavigatorController = R.id.fragment_container
    }

}
