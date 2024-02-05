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


    // 通过伴身对象实现静态变量
    // 这里为什么不用by lazy委托延迟初始化的方式？
    // 这是因为by lazy的方式只适用于不带参数的构造函数的单例模式
    companion object {
        // lateinit只能使用于var定义的非空类型的变量；by lazy只能适用于val定义的常量
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