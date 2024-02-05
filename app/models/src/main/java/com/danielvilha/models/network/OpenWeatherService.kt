package com.danielvilha.models.network

import com.danielvilha.models.OpenWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Daniel Ferreira de Lima Vilha 27/01/2024.
 */
interface OpenWeatherService {

    @GET("onecall")
    fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("dt") time: String,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String,
    ): Call<OpenWeather>
}
