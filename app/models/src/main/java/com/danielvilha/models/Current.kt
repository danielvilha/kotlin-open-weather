package com.danielvilha.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Daniel Ferreira de Lima Vilha 27/01/2024.
 */
data class Current(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    val pressure: Double,
    val humidity: Double,
    @SerializedName("dew_point")
    val dewPoint: Double,
    val uvi: Double,
    val clouds: Double,
    val visibility: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_deg")
    val windDeg: Double,
    @SerializedName("wind_gust")
    val windGust: Double,
    val weather: List<Weather>,
)
