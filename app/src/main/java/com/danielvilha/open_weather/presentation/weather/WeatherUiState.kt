package com.danielvilha.open_weather.presentation.weather

import com.danielvilha.models.OpenWeather
import com.danielvilha.models.WeatherStatus

/**
 * Created by Daniel Ferreira de Lima Vilha 28/01/2024.
 */
data class WeatherUiState(
    val status: WeatherStatus,
    val throwable: String? = null,
    val openWeather: OpenWeather? = null
)