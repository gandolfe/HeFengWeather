package com.coolweather.coolweatherjetpack.ui.area

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coolweather.coolweatherjetpack.data.HefengRepository
import com.example.hefengweather.ui.area.HeFengAreaModel


class HefengAreaModelFactory(private val repository: HefengRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeFengAreaModel(repository) as T
    }

}