package com.aleksandr.aleksandrov.weatherfornatife.base_classes

import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        const val RESULT_MAP = 1000
        const val RESULT_PLACE_AUTOCOMPLETE = 1100
        const val PERMISSIONS_REQUEST_LOCATION = 1110
    }

    protected var fragmentNavigatorController: Int = 0

    fun navigateTo(res: Int) {
        Navigation.findNavController(this, fragmentNavigatorController).navigate(res)
    }

}
