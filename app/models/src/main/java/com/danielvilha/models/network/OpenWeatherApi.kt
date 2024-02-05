package com.danielvilha.models.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Daniel Ferreira de Lima Vilha 27/01/2024.
 */
private const val BASE_URL = "https://api.openweathermap.org/data/3.0/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

object OpenWeatherApi {
    val retrofitService: OpenWeatherService by lazy {
        retrofit.create(OpenWeatherService::class.java)
    }
}