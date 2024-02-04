package com.example.hefengweather.util

import com.coolweather.coolweatherjetpack.ui.area.HefengAreaModelFactory
import com.example.hefengweather.data.HefengRepository
import com.example.hefengweather.data.db.HefengWeatherDatabase
import com.example.hefengweather.data.network.HeFengNetwork
import com.example.hefengweather.ui.weather.HeFengModelFactory

object InjectorUtil {

    private fun getHeFengRepository() = HefengRepository.getInstance(HefengWeatherDatabase.getHeFengDao(), HeFengNetwork)

    fun getHeFengModelFactory() = HeFengModelFactory(getHeFengRepository())

    fun getHeFengAreaModelFactory() = HefengAreaModelFactory(getHeFengRepository())

}