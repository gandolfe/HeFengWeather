package com.example.hefengweather.data.network.api

import com.example.hefengweather.data.model.area.AreaData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaService {
    @GET("district")
    fun getDistrictByKeyName(@Query("keywords") province: String
                                  , @Query("subdistrict") subdistrict: Int = 1
                                  , @Query("key") key: String = "fb2413c74162fb19c00ff78ea862d2b4") : Call<AreaData>
}