package com.example.hefengweather.data.model.weather

import com.google.gson.annotations.SerializedName

class HeFengNow {
    var updateTime = ""

    var now : Now? = null

    inner class Now {
        @SerializedName("temp")
        var temperature = "0"
            get() = "当前温度：$field ℃"

        @SerializedName("text")
        var descript = ""

        /**
         * 风向
         */
        var windDir = ""
        get() = "风向：$field"

        /**
         * 风力等级
         */
        var windScale = ""
        get() = "风力等级：$field"

        /**
         * 相对湿度，百分比数值
         */
        var humidity = ""
    }
}