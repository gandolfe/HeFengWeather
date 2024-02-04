package com.example.hefengweather.data

import android.util.Log
import com.example.hefengweather.data.db.HefengDao
import com.example.hefengweather.data.model.area.AreaData
import com.example.hefengweather.data.model.weather.HeFengCity
import com.example.hefengweather.data.model.weather.HeFengNow
import com.example.hefengweather.data.network.HeFengNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class HefengRepository private constructor(private val hefengDao: HefengDao, private val hefengNetWork: HeFengNetwork){

    suspend fun getNow(city: String) : HeFengNow = withContext(Dispatchers.IO) {
      Log.i("HefengRepository", "city:$city")
      hefengNetWork.fetchHefengNow(city)
    }

    suspend fun transPortAdCodeToLocationID(adcode: String) : HeFengCity = withContext(Dispatchers.IO) {
       var city = hefengNetWork.fetchFefengLocation(adcode)
        caCheCurrentName(city?.location?.get(0)?.name)
        city
    }

    suspend fun getAreaData(keyword: String) : AreaData = withContext(Dispatchers.IO) {
        hefengNetWork.fetchAreaData(keyword)
    }

    fun caCheCurrentName(name : String?) {
        hefengDao.cacheCurrentName(name)
    }

    fun getCurrentName() : String {
        return hefengDao.getCurrentName()
    }

    fun caCheSelectedArea(adcode : String) {
        hefengDao.cacheSelectArea(adcode)
    }

    fun getSelectedArea() : String {
        return hefengDao.getSelectedAreaAdCode()
    }



    companion object {

        private lateinit var instance: HefengRepository

        fun getInstance(hefengDao: HefengDao, hefengNetWork: HeFengNetwork): HefengRepository {
            if (!::instance.isInitialized) {
                synchronized(HefengRepository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = HefengRepository(hefengDao, hefengNetWork)
                    }
                }
            }
            return instance
        }

    }

}