package com.example.hefengweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class HeFengWeatherApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}