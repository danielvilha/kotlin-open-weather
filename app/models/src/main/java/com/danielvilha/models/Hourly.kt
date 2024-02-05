package com.danielvilha.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Ferreira de Lima Vilha 27/01/2024.
 */
data class Hourly(
    val dt: Int,
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Int,
    val visibility: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Int,
    @SerializedName("wind_gust")
    val windGust: Double,
    val weather: List<Weather>,
    val pop: Double,
)
