package com.example.hefengweather.data.db

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.hefengweather.HeFengWeatherApplication
import com.example.hefengweather.data.model.weather.HeFengNow
import com.google.gson.Gson

class HefengDao {

    fun cacheHefengNowData(now: HeFengNow.Now?) {
        if (now == null) return
        PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context).edit() {
            val nowString = Gson().toJson(now)
            putString("hefengnow", nowString)
        }
    }

    fun getHefengNowData() : HeFengNow.Now?{
        val nowString = PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context)
            .getString("hefengnow", null)
        if (nowString != null) {
           val now = Gson().fromJson(nowString,HeFengNow.Now::class.java)
        }
        return null
    }

    fun cacheSelectArea(adcode : String) {
        PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context).edit() {
            putString("adcode", adcode)
        }
    }

    fun getSelectedAreaAdCode() : String {
        return PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context)
            .getString("adcode", "500107")!!
    }

    fun cacheCurrentName(name : String?) {
        PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context).edit() {
            putString("currentname", name)
        }
    }

    fun getCurrentName() : String {
        return PreferenceManager.getDefaultSharedPreferences(HeFengWeatherApplication.context)
            .getString("currentname", "北京")!!
    }

    // 定义扩展高阶函数
    fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        var editor = edit()
        action(editor)
        editor.apply()
    }
}