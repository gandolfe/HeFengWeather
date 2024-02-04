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