package com.example.hefengweather.ui.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.hefengweather.R
import com.example.hefengweather.databinding.ActivityHefengBinding
import com.example.hefengweather.util.InjectorUtil

class HefengActivity : AppCompatActivity() {

    private val binding by lazy {DataBindingUtil.setContentView<ActivityHefengBinding>(this, R.layout.activity_hefeng)}

    val viewModel by lazy { ViewModelProviders.of(this, InjectorUtil.getHeFengModelFactory()).get(HeFengViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        homeButton.setOnClickListener { hefengDrawer.openDrawer(GravityCompat.START) }

        viewModel.error.observe(this, Observer {
            temperature.text = it
        })

        viewModel.initView()
    }
}