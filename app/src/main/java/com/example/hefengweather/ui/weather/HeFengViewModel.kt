package com.example.hefengweather.ui.weather

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hefengweather.HeFengWeatherApplication
import com.example.hefengweather.data.HefengRepository
import com.example.hefengweather.data.model.weather.HeFengNow
import kotlinx.coroutines.launch

class HeFengViewModel(private val repository: HefengRepository) : ViewModel() {

    var heFengNow = MutableLiveData<HeFengNow>()

    var error = MutableLiveData<String>()

    var loading = MutableLiveData<Boolean>()

    var areaid = ""
    set(value) {
        field = value
        repository.caCheSelectedArea(value)
    }

    var currentAreaName = MutableLiveData<String>()

    fun initView() {
        currentAreaName.value = repository.getCurrentName()
        getHefengNow()
    }

    fun getHefengNow() {
        loading.value = true
        launch({
            if (areaid.isEmpty()) {
                areaid = repository.getSelectedArea()
            }
            Log.i("HeFengViewModel", "areaid:${areaid}")
            var heFengCity = repository.transPortAdCodeToLocationID(areaid)
            if (heFengCity.location.isNullOrEmpty()) {
                error.value = "城市编码转换失败"
                loading.value = false
                return@launch
            }

            var nowData = repository.getNow(heFengCity.location!![0].id)
            Log.i("HeFengViewModel", "nowData:${nowData.now}")
            if (nowData?.now == null || nowData.now?.temperature == null) {
                error.value = "天气获取失败"
                loading.value = false
                return@launch
            }
            heFengNow.value = repository.getNow(heFengCity.location!![0].id)
            loading.value = false
        }, {
            loading.value = false
            Toast.makeText(HeFengWeatherApplication.context, it.message, Toast.LENGTH_SHORT)
        })
    }

    // 这个函数的目的是把异步操作包装在协程中，并提供一个错误处理机制
    // 这是一个高阶函数
    // viewModelScope.launch 函数用于创建并启动一个新的协程，以异步执行一段代码块，不会阻塞当前主线程
    // viewModelScope是属于ViewModel的扩展属性，方便在ViewModel中使用协程，并且可以监测Activity或者Fragment的生命周期，然后自动取消，避免内存泄漏
    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) {
        viewModelScope.launch {
            try {
                block()
            }catch (e: Throwable) {
                error(e)
            }
        }
    }

}