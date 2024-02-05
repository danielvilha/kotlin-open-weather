package com.danielvilha.open_weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.danielvilha.models.Weather
import com.danielvilha.open_weather.util.convertTimestampToDateTime
import com.danielvilha.open_weather.util.convertTimestampToTime
import com.danielvilha.open_weather.util.dewPointToRainProbability
import com.danielvilha.open_weather.util.getBackgroundColor
import com.danielvilha.open_weather.util.getColorFilter
import com.danielvilha.open_weather.util.getTextColor
import com.danielvilha.open_weather.util.kelvinToCelsius
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UtilTest {

    @Test
    fun testConvertTimestampToDateTime() {
        val timestamp = 1706990400L
        val expectedDateTime = "Saturday, 8:00 PM"

        val actualDateTime = convertTimestampToDateTime(timestamp)

        assertEquals(expectedDateTime, actualDateTime)
    }

    @Test
    fun testConvertTimestampToTime() {
        val timestamp = 1706990400L
        val expectedTime = "20:00"

        val actualTime = convertTimestampToTime(timestamp = timestamp)

        assertEquals(expectedTime, actualTime)
    }

    @Test
    fun testKelvinToCelsius() {
        val kelvin = 297.6
        val expectedCelsius = "24 °C"

        val actualCelsius = kelvinToCelsius(temperatureInKelvin = kelvin)

        assertEquals(expectedCelsius, actualCelsius)
    }

    @Test
    fun testDewPointToRainProbability() {
        val dewPoint = 293.72
        val expectedDewPoint = "100 %"

        val actualDewPoint = dewPointToRainProbability(dewPoint = dewPoint)

        assertEquals(expectedDewPoint, actualDewPoint)
    }

    @Test
    fun testBackgroundColor() {
        val expectedBackgroundColor = Color(0xFF808080)

        val actualBackgroundColor = getBackgroundColor(getWeather)

        assertEquals(expectedBackgroundColor, actualBackgroundColor)
    }

    @Test
    fun testColorFilter() {
        val expectedColorFilter = ColorFilter.tint(Color(0xFF202124))

        val actualColorFilter = getColorFilter(getWeather)

        assertEquals(expectedColorFilter, actualColorFilter)
    }

    @Test
    fun testTextColor() {
        val expectedColor = Color(0xFF202124)

        val actualColor = getTextColor(weather = getWeather)

        assertTrue(colorsAreEqual(expectedColor, actualColor))
    }

    private val getWeather = Weather(
        id = 803,
        main = "Clouds",
        description = "broken clouds",
        icon = "04n",
    )

    private fun colorsAreEqual(color1: Color, color2: Color): Boolean {
        return color1.red == color2.red &&
                color1.green == color2.green &&
                color1.blue == color2.blue &&
                color1.alpha == color2.alpha
    }
}