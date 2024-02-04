package com.example.hefengweather.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object HeFengServiceCreator {

    private const val BASE_URL = "https://devapi.qweather.com/"

    private const val BASE_URL_GEO = "https://geoapi.qweather.com/"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    fun <T> createGeo(serviceClass: Class<T>): T {
        builder.baseUrl(BASE_URL_GEO)
        var retrofitGeo = builder.build()
       return retrofitGeo.create(serviceClass)
    }

}