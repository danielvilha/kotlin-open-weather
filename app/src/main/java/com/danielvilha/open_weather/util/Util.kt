package com.danielvilha.open_weather.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.danielvilha.models.Weather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by Daniel Ferreira de Lima Vilha 29/01/2024.
 */

fun convertTimestampToDateTime(timestamp: Long): String {
    val date = Date(timestamp * 1000L)
    val format = SimpleDateFormat("EEEE, h:mm a", Locale.getDefault())
    return format.format(date)
}

fun convertTimestampToTime(timestamp: Long): String {
    val date = Date(timestamp * 1000L)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

fun kelvinToCelsius(temperatureInKelvin: Double): String {
    return "${(temperatureInKelvin - 273.15).toInt()} °C"
}

fun dewPointToRainProbability(dewPoint: Double): String {
    val minValue = 0.0 // Minimum dew point value
    val maxValue = 100.0 // Maximum dew point value
    val minRainProbability = 0 // Minimal chance of rain (0%)
    val maxRainProbability = 100 // Maximum chance of rain (100%)

    // Using a linear mapping rule to calculate the probability of rain
    val rainProbability =
        ((dewPoint - minValue) / (maxValue - minValue)
                * (maxRainProbability - minRainProbability)).toInt()

    // Make sure the probability of rain is within limits (0-100)
    return when {
        rainProbability < minRainProbability -> "$minRainProbability %"
        rainProbability > maxRainProbability -> "$maxRainProbability %"
        else -> "$rainProbability %"
    }
}

fun getBackgroundColor(weather: Weather?) = when (weather?.id) {
    200, 201, 210, 211, 212, 221, 230, 231, 232 -> Color(0xFF303030)
    300, 301, 302, 310, 311, 312, 313, 314, 321 -> Color(0xFF9C9C9C)
    500, 501, 502, 503, 504 -> Color(0xFFCFD4D5)
    511 -> Color(0xFFE4F2F8)
    520, 521, 522, 531 -> Color(0xFF9C9C9C)
    600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622 -> Color(0xFFFFFAFA)
    701, 711, 721, 731, 741, 751, 761, 762, 771, 781 -> Color(0xFF303030)
    800 -> if (weather.icon == "01d") Color(0xFF86DCFF) else Color(0xFF182225)
    801 -> if (weather.icon == "02d") Color(0xFF99BDCC) else Color(0xFF393A3F)
    802 -> Color(0xFFD3D3D3)
    803, 804 -> Color(0xFF808080)
    else -> Color(0xFFD3D3D3)
}

fun getColorFilter(weather: Weather?) = when (weather?.id) {
    200, 201, 210, 211, 212, 221, 230, 231, 232,
    701, 711, 721, 731, 741, 751, 761, 762, 771, 781 ->
        ColorFilter.tint(Color(0xFFDCDCED))

    800 -> if (weather.icon == "01d")
        ColorFilter.tint(Color(0xFF202124))
    else
        ColorFilter.tint(Color(0xFFDCDCED))

    801 -> if (weather.icon == "02d")
        ColorFilter.tint(Color(0xFF202124))
    else
        ColorFilter.tint(Color(0xFFDCDCED))

    else -> ColorFilter.tint(Color(0xFF202124))
}

fun getTextColor(weather: Weather?)  = when (weather?.id) {
    200, 201, 210, 211, 212, 221, 230, 231, 232,
    701, 711, 721, 731, 741, 751, 761, 762, 771, 781 ->
        Color(0xFFDCDCED)

    800 -> if (weather.icon == "01d")
        Color(0xFF202124)
    else
        Color(0xFFDCDCED)

    801 -> if (weather.icon == "02d")
        Color(0xFF202124)
    else
        Color(0xFFDCDCED)

    else -> Color(0xFF202124)
}
