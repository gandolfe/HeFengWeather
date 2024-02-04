package com.example.hefengweather.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hefengweather.R

class HeFengAreaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.container, HeFengAreaFragment()).commit()
    }

}