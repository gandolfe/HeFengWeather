package com.example.hefengweather.ui.area

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hefengweather.HeFengWeatherApplication
import com.example.hefengweather.data.HefengRepository
import com.example.hefengweather.data.model.area.AreaData
import kotlinx.coroutines.launch
import java.lang.Exception

class HeFengAreaModel(val repository: HefengRepository) : ViewModel() {

    private val TAG = "HeFengAreaModel"

    var areaData = MutableLiveData<AreaData>()

    var dataChange = MutableLiveData<Boolean>()

    var isShowBack = MutableLiveData<Boolean>()

    var districts : ArrayList<AreaData> = java.util.ArrayList()

    var selectedArea = MutableLiveData<AreaData>()

    fun getAreaDataOfProvince() {
        getAreaDataOfName("中国")
    }

    fun back() {
        areaData.value = areaData.value?.parent
        if (areaData.value?.parent == null) {
            isShowBack.value = false
        }
        var tempdistricts = areaData.value?.districts
        if (tempdistricts != null && tempdistricts.size > 0) {
            districts.clear()
            districts.addAll(tempdistricts!!)
        }
        dataChange.value = true
    }

    fun onItemClick(position: Int) {
        Log.d(TAG, "onItemClick:$position")
        if (districts.isNullOrEmpty()) {
            return
        }
        var select = districts[position]
        if (select.level == "district") {
            // 选择对应的街道
            selectedArea.value = select
            return
        }
        getAreaDataOfName(select.name)
    }

    private fun getAreaDataOfName(name: String) {
        if (areaData.value == null) {
            var default = AreaData()
            areaData.value = default
        }
        viewModelScope.launch {
            try {
                var area = repository.getAreaData(name)
                if (TextUtils.isEmpty(area.adcode) && !area.districts.isNullOrEmpty()) {
                    area  = area.districts!![0]
                }
                indexAreaData(area)
                areaData.value = area

                var tempdistricts = areaData.value?.districts
                if (tempdistricts != null && tempdistricts.size > 0) {
                    districts.clear()
                    districts.addAll(tempdistricts!!)
                }

                Log.d(TAG,"当前线程：${Thread.currentThread().name}")
                Log.d(TAG, "当前区域：${areaData.value!!.name} , 下属列表长度：${districts.size}")
                dataChange.value = true

                isShowBack.value = area.level != "country"

            }catch (e: Exception) {
                Toast.makeText(HeFengWeatherApplication.context, e.message, Toast.LENGTH_SHORT)
            }
        }
    }

    private fun indexAreaData(newData: AreaData) {
        var currentData: AreaData? = areaData.value ?: return
        var currentLevel = currentData?.level
        if (currentLevel == "country") {

        }
        when(currentLevel) {
            "country" -> newData.parent = currentData
            "province" -> if (newData.level != "country") newData.parent = currentData
            "city" -> if (newData.level != "country" && newData.level != "province") newData.parent = currentData
        }
    }
}