package com.example.hefengweather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hefengweather.data.HefengRepository

class HeFengModelFactory(val repository : HefengRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeFengViewModel(repository) as T
    }
}