package com.example.hefengweather.data.network

import android.util.Log
import com.example.hefengweather.data.network.api.AreaService
import com.example.hefengweather.data.network.api.HefengService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object HeFengNetwork {

    private const val KEY = "fccc86fd2a0b4143b478bcb730df8720"

    private val heFengService = HeFengServiceCreator.create(HefengService::class.java)

    private val areaService = HeFengAreaServiceCreator.createArea(AreaService::class.java)

    private val heFengGeoService = HeFengServiceCreator.createGeo(HefengService::class.java)

    suspend fun fetchHefengNow(locationID: String) = heFengService.getNow(locationID, KEY).await()

    suspend fun fetchAreaData(keyWord: String) = areaService.getDistrictByKeyName(keyWord).await()

    suspend fun fetchFefengLocation(adcode: String) = heFengGeoService.getLocationId(adcode, KEY).await()

    // 此扩展函数的目的，是让Retrofit的Call对象放在一个协程中处理，在协程中处理网络异步请求，不阻塞线程
    // suspendCoroutine函数是Kotlin协程库提供的一个函数，用来挂起当前协程，并通过内部的参数continuation来恢复协程
    private suspend fun <T> Call<T>.await() : T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    Log.d("HeFengNetwork", "body:$body")
                    if (body == null) it.resumeWithException(RuntimeException("body null"))
                    else it.resume(body)
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.e("HeFengNetwork", "onFailure:$t")
                    it.resumeWithException(RuntimeException("call failure"))
                }

            })
        }
    }
}