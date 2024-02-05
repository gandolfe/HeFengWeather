package com.example.hefengweather.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hefengweather.data.HefengRepository
/**
 * 使用自定义工厂模式获取ViewModel，可以在实例化ViewModel的时候，注入对应的依赖
 * HefengRepository
 */
class HeFengModelFactory(private val repository : HefengRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HeFengViewModel(repository) as T
    }
}