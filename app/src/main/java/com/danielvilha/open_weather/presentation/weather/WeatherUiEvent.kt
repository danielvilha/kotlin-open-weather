package com.danielvilha.open_weather.presentation.weather

sealed class WeatherUiEvent {

    data object TryAgain: WeatherUiEvent()
}