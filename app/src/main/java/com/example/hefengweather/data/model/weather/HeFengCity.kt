package com.example.hefengweather.data.model.weather

class HeFengCity {
    var code = ""

    var location : List<City>? = null

    inner class City {
        var name = ""
        var id = ""
    }
}