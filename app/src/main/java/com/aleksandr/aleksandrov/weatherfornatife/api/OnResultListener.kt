package com.aleksandr.aleksandrov.weatherfornatife.api

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
interface OnResultListener<T> {

    fun onResult(result: T)

    fun onError(error: String)

}
