package com.danielvilha.openweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielvilha.openweather.network.OpenWeatherApi
import com.danielvilha.openweather.network.OpenWeatherProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by danielvilha on 06/09/21
 * https://github.com/danielvilha
 */
class HomeViewModel : ViewModel() {

    private val _openWeather = MutableLiveData<OpenWeatherProperty>()
    val openWeather: LiveData<OpenWeatherProperty>
        get() = _openWeather

    init {
        getWeather()
    }

    fun dateFormatter(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()
        return formatter.format(calendar.time)
    }

    private fun getWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val service = OpenWeatherApi.retrofitService.getWeather().execute()

            _openWeather.postValue(service.body())
        }
    }
}