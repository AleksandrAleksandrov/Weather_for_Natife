package com.aleksandr.aleksandrov.weatherfornatife.base_classes

/**
 * Created by Alexandrov Alex on 2019-06-04.
 */
interface OnResultListener<T> {

    fun onResult(result: T)

    fun onError(error: String)

}
