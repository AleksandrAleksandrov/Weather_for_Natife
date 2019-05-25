package com.aleksandr.aleksandrov.weatherfornatife.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
abstract class ResponseCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {

        if (response.isSuccessful) {
            onSuccess(call, response)
            return
        }

        onError(call, response)

    }


    abstract fun onSuccess(call: Call<T>, response: Response<T>)

    abstract fun onError(call: Call<T>, response: Response<T>)

}