package com.example.hefengweather.data.model.area

import android.text.TextUtils

class AreaData {
    var parent : AreaData? = null
    var name: String = ""
    get() {
        if (TextUtils.isEmpty(field)) {
            return "默认地名"
        } else {
            return field
        }
    }
    var adcode = ""
    var level = ""
    var districts : ArrayList<AreaData>? = null
}