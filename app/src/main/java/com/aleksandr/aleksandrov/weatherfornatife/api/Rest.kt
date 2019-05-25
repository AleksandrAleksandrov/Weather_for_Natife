package com.aleksandr.aleksandrov.weatherfornatife.api

import com.aleksandr.aleksandrov.weatherfornatife.api.APIConstants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Alexandrov Alex on 2019-05-25.
 */
class Rest {

    fun getAPI() : API {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS)
        clientBuilder.readTimeout(30, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(30, TimeUnit.SECONDS)

        val builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBuilder.build())

        return builder.build().create(API::class.javaObjectType)
    }
}
