package com.danielvilha.openweather

import com.danielvilha.openweather.network.OpenWeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Test

/**
 * Created by danielvilha on 06/09/21
 * https://github.com/danielvilha
 */
class OpenWeatherTest {

    @Test
    fun open_weather_test() {
        CoroutineScope(Dispatchers.IO).launch {
            val test = OpenWeatherApi.retrofitService.getWeather().execute()
            val body = test.body()

            assert(body?.city?.name.isNullOrBlank())
        }
    }
}