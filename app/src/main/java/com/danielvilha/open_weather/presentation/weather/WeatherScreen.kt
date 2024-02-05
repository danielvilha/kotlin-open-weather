package com.danielvilha.open_weather.presentation.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.danielvilha.models.Current
import com.danielvilha.models.Daily
import com.danielvilha.models.FeelsLike
import com.danielvilha.models.Hourly
import com.danielvilha.models.OpenWeather
import com.danielvilha.models.Temp
import com.danielvilha.models.Weather
import com.danielvilha.models.WeatherStatus
import com.danielvilha.open_weather.R
import com.danielvilha.open_weather.theme.OpenWeatherTheme
import com.danielvilha.open_weather.util.ExcludeFromJacocoGeneratedReport
import com.danielvilha.open_weather.util.LightDarkPreview
import com.danielvilha.open_weather.util.convertTimestampToDateTime
import com.danielvilha.open_weather.util.convertTimestampToTime
import com.danielvilha.open_weather.util.dewPointToRainProbability
import com.danielvilha.open_weather.util.getBackgroundColor
import com.danielvilha.open_weather.util.getColorFilter
import com.danielvilha.open_weather.util.getTextColor
import com.danielvilha.open_weather.util.kelvinToCelsius

/**
 * Created by Daniel Ferreira de Lima Vilha 28/01/2024.
 */
@Composable
@LightDarkPreview
@ExcludeFromJacocoGeneratedReport
private fun Preview(
    @PreviewParameter(WeatherScreenProvider::class)
    state: WeatherUiState
) {
    OpenWeatherTheme {
        Content(state)
    }
}

@Composable
fun WeatherScreen(state: WeatherUiState) {
    OpenWeatherTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Content(state = state)
                }
            }
        )
    }
}

@Composable
private fun Content(
    state: WeatherUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when(state.status) {
            WeatherStatus.LOADING -> LoadingContent()
            WeatherStatus.ERROR -> ErrorContent(state = state)
            WeatherStatus.DONE -> WeatherContent(state = state)
        }
    }
}

@Composable
private fun ErrorContent(state: WeatherUiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = state.throwable ?:
            stringResource(id = R.string.error_has_occurred),
            color = Color(0xFFDCDCED),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Button(onClick = { /*TODO*/ }) {
            Text(
                text = stringResource(id = R.string.try_again),
                fontSize = 22.sp
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        CircularProgressIndicator(
//            modifier = Modifier.size(60.dp),
//            color = Color.LightGray
//        )
    }
}

@Composable
private fun WeatherContent(state: WeatherUiState) {
    state.openWeather?.let {
        val weather = state.openWeather.current.weather.firstOrNull()

        val backgroundColor = getBackgroundColor(weather)
        val colorFilter = getColorFilter(weather)
        val textColor = getTextColor(weather)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                TextWeather(
                    text = it.timezone,
                    textColor = textColor,
                )
                Spacer(modifier = Modifier.height(24.dp))
                TextWeather(
                    text = convertTimestampToDateTime(it.current.dt.toLong()),
                    textColor = textColor,
                )
                WeatherImage(
                    url = stringResource(
                        id = R.string.weather_image_link,
                        it.current.weather.firstOrNull()?.icon ?: "01d",
                    ),
                    modifier = Modifier.size(200.dp)
                )
                Text(
                    text = kelvinToCelsius(it.current.temp),
                    color = textColor,
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wind_speed),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        colorFilter = colorFilter
                    )
                    Column(modifier = Modifier.padding(start = 4.dp)) {
                        Text(
                            text = stringResource(id = R.string.kph, it.current.windSpeed),
                            color = textColor,
                            fontSize = 15.sp,
                        )
                        TextWeather(
                            text = stringResource(id = R.string.wind_speed),
                            textColor = textColor,
                        )
                    }

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(2.dp)
                            .height(44.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_humidity),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        colorFilter = colorFilter
                    )
                    Column(modifier = Modifier.padding(start = 4.dp)) {
                        Text(
                            text = it.current.humidity.toString(),
                            color = textColor,
                            fontSize = 15.sp,
                        )
                        TextWeather(
                            text = stringResource(id = R.string.humidity),
                            textColor = textColor,
                        )
                    }

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp)
                            .width(2.dp)
                            .height(44.dp),
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_probability_rain),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp),
                        colorFilter = colorFilter
                    )
                    Column(modifier = Modifier.padding(start = 4.dp)) {
                        Text(
                            text = dewPointToRainProbability(it.current.windSpeed),
                            color = textColor,
                            fontSize = 15.sp,
                        )
                        TextWeather(
                            text = stringResource(id = R.string.chance),
                            textColor = textColor,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
            ) {
                HourlyWeatherList(it.hourly)
            }
        }
    }
}

@Composable
private fun HourlyWeatherItem(hourlyWeather: Hourly) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(100.dp)
            .background(color = Color(0x88FFFFFF), shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = convertTimestampToTime(hourlyWeather.dt.toLong()),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Text(
            text = kelvinToCelsius(hourlyWeather.temp),
            color = Color.Black,
            fontSize = 16.sp
        )

        WeatherImage(
            url = stringResource(
                id = R.string.weather_image_link,
                hourlyWeather.weather[0].icon ?: "01d",
            ),
            modifier = Modifier.size(40.dp),
        )
    }
}

@Composable
private fun HourlyWeatherList(list: List<Hourly>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            items(list.take(5)) { hourly ->
                HourlyWeatherItem(hourly)
            }
        }
    )
}

@Composable
private fun WeatherImage(url: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(ImageRequest.Builder
            (LocalContext.current).data(data = url).apply(block = fun ImageRequest.Builder.() {
            transformations(CircleCropTransformation())
            placeholder(R.drawable.ic_not_found)
        }).build()
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun TextWeather(text: String, textColor: Color) {
    Text(text = text, color = textColor)
}

@ExcludeFromJacocoGeneratedReport
private class WeatherScreenProvider : PreviewParameterProvider<WeatherUiState> {

    override val values: Sequence<WeatherUiState>
        get() = sequenceOf(
            WeatherUiState(status = WeatherStatus.ERROR),
            WeatherUiState(status = WeatherStatus.LOADING),
            WeatherUiState(
                status = WeatherStatus.DONE,
                openWeather = OpenWeather(
                    lat = 24.5714,
                    lon = 53.9786,
                    timezone = "Asia/Dubai",
                    timezoneOffset = 14400,
                    current = Current(
                        dt = 1706397633,
                        sunrise = 1706411269,
                        sunset = 1706450741,
                        temp = 297.6,
                        feelsLike = 298.16,
                        pressure = 1015.0,
                        humidity = 79.0,
                        dewPoint = 293.72,
                        uvi = 0.0,
                        clouds = 84.0,
                        visibility = 10000.0,
                        windSpeed = 7.4,
                        windDeg = 324.0,
                        windGust = 8.55,
                        weather = listOf(Weather(
                            id = 803,
                            main = "Clouds",
                            description = "broken clouds",
                            icon = "04n"
                            )
                        )
                    ),
                    hourly = listOf(
                        Hourly(
                            dt = 1706396400,
                            temp = 297.6,
                            feelsLike = 298.16,
                            pressure = 1015,
                            humidity = 79,
                            dewPoint = 293.72,
                            uvi = 0.0,
                            clouds = 84,
                            visibility = 10000,
                            windSpeed = 7.4,
                            windDeg = 324,
                            windGust = 8.55,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n",
                                )
                            ),
                            pop = 0.8,
                    ),
                        Hourly(
                            dt = 1706396400,
                            temp = 297.6,
                            feelsLike = 298.16,
                            pressure = 1015,
                            humidity = 79,
                            dewPoint = 293.72,
                            uvi = 0.0,
                            clouds = 84,
                            visibility = 10000,
                            windSpeed = 7.4,
                            windDeg = 324,
                            windGust = 8.55,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n",
                                )
                            ),
                            pop = 0.8,
                        ),
                        Hourly(
                            dt = 1706396400,
                            temp = 297.6,
                            feelsLike = 298.16,
                            pressure = 1015,
                            humidity = 79,
                            dewPoint = 293.72,
                            uvi = 0.0,
                            clouds = 84,
                            visibility = 10000,
                            windSpeed = 7.4,
                            windDeg = 324,
                            windGust = 8.55,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n",
                                )
                            ),
                            pop = 0.8,
                        ),
                        Hourly(
                            dt = 1706396400,
                            temp = 297.6,
                            feelsLike = 298.16,
                            pressure = 1015,
                            humidity = 79,
                            dewPoint = 293.72,
                            uvi = 0.0,
                            clouds = 84,
                            visibility = 10000,
                            windSpeed = 7.4,
                            windDeg = 324,
                            windGust = 8.55,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n",
                                )
                            ),
                            pop = 0.8,
                        ),
                        Hourly(
                            dt = 1706396400,
                            temp = 297.6,
                            feelsLike = 298.16,
                            pressure = 1015,
                            humidity = 79,
                            dewPoint = 293.72,
                            uvi = 0.0,
                            clouds = 84,
                            visibility = 10000,
                            windSpeed = 7.4,
                            windDeg = 324,
                            windGust = 8.55,
                            weather = listOf(
                                Weather(
                                    id = 803,
                                    main = "Clouds",
                                    description = "broken clouds",
                                    icon = "04n",
                                )
                            ),
                            pop = 0.8,
                        ),),
                    daily = listOf(
                        Daily(
                            dt = 1706428800,
                            sunrise = 1706411269,
                            sunset = 1706450741,
                            moonrise = 1706459280,
                            moonset = 1706417520,
                            moonPhase = 0.58,
                            summary = "Expect a day of partly cloudy with clear spells",
                            temp = Temp(
                                day = 294.43,
                                min = 294.08,
                                max = 297.6,
                                night = 294.08,
                                eve = 294.48,
                                morn = 295.91
                            ),
                            feelsLike = FeelsLike(
                                day = 294.23,
                                night = 293.66,
                                eve = 294.26,
                                morn = 296.28
                            ),
                            pressure = 1019,
                            humidity = 62,
                            dewPoint = 287.0,
                            windSpeed = 8.74,
                            windDeg = 323,
                            windGust = 9.36,
                            weather = listOf(
                                Weather(
                                    id = 800,
                                    main=  "Clear",
                                    description = "clear sky",
                                    icon = "01d"
                                )),
                            clouds = 2,
                            pop = 0.96,
                            uvi = 5.78,
                        )
                    ),
                )
            )
        )
}
