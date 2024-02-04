package com.example.hefengweather.data.network.api

import com.example.hefengweather.data.model.weather.HeFengCity
import com.example.hefengweather.data.model.weather.HeFengNow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HefengService {
    @GET("v7/weather/now")
    fun getNow(@Query("location") locationID : String, @Query("key") key : String) : Call<HeFengNow>

    @GET("v2/city/lookup")
    fun getLocationId(@Query("location") adcode : String, @Query("key") key : String) : Call<HeFengCity>
}