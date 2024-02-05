package com.danielvilha.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Ferreira de Lima Vilha 27/01/2024.
 */
data class OpenWeather(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int,
    val current: Current,
    val hourly: List<Hourly>,
    val daily: List<Daily>,
)
