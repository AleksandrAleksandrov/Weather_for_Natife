package com.aleksandr.aleksandrov.weatherfornatife.base_classes

import android.widget.Toast
import com.aleksandr.aleksandrov.weatherfornatife.R

/**
 * Created by Alexandrov Alex on 2019-05-20.
 */
abstract class ListFragmentBase : FragmentBase() {

    protected val firstColor: Int = R.color.colorPrimary
    protected val secondColor: Int = R.color.materialGreen
    protected val thirdColor: Int = R.color.holoRedLight

    companion object {
        const val IS_REFRESHING: String = "isRefreshing"
        const val IS_REFRESH_ENABLED: String = "is_refresh_enabled"
    }

    protected var isRefreshing: Boolean = false
    protected var isRefreshEnable: Boolean = false

    protected fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

}
