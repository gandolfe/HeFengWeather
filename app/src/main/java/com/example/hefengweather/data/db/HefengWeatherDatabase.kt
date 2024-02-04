package com.example.hefengweather.data.db

object HefengWeatherDatabase {

    private var hefengDao: HefengDao? = null

    fun getHeFengDao(): HefengDao {
        if (hefengDao == null) hefengDao = HefengDao()
        // 由于这里定义返回的对象是非空的，但是hefengDao是一个可空的变量，因此这里使用!!把可空变量强制变成非空的变量
        return hefengDao!!
    }

}