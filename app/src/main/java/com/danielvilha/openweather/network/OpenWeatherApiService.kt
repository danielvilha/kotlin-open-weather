package com.danielvilha.openweather.network

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by danielvilha on 06/09/21
 * https://github.com/danielvilha
 */
interface OpenWeatherApiService {

    @GET("forecast?q=Dublin,IE&APPID=1af9031b36210770055780c9e6c073f7")
    fun getWeather(): Call<OpenWeatherProperty>
}