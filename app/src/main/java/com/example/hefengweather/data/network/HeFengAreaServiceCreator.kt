package com.example.hefengweather.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object HeFengAreaServiceCreator {

    private const val BASE_AREA_URL = "https://restapi.amap.com/v3/config/"

    private val httpClient = OkHttpClient.Builder()

    private val builderArea = Retrofit.Builder()
        .baseUrl(BASE_AREA_URL)
        .client(httpClient.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofitArea = builderArea.build()

    fun <T> createArea(serviceClass: Class<T>): T = retrofitArea.create(serviceClass)

}