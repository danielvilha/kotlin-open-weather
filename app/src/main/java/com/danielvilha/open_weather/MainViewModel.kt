package com.danielvilha.open_weather

import android.location.Location
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielvilha.models.AppSettings
import com.danielvilha.models.OpenWeather
import com.danielvilha.models.WeatherStatus
import com.danielvilha.models.network.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

/**
 * Created by Daniel Ferreira de Lima Vilha 28/01/2024.
 */
class MainViewModel : ViewModel() {

    private val _valid : MutableStateFlow<Boolean?> = MutableStateFlow(false)
    val valid : StateFlow<Boolean?> = _valid

    private val _openWeather = MutableLiveData<OpenWeather>()
    val openWeather: MutableLiveData<OpenWeather>
        get() = _openWeather

    private val _status = MutableLiveData<WeatherStatus>()
    val status: MutableLiveData<WeatherStatus>
        get() = _status

    private val _throwable = MutableLiveData<String>()
    val throwable: MutableLiveData<String>
        get() = _throwable

    private val _location = MutableLiveData<Location?>()
    val location: MutableLiveData<Location?>
        get() = _location

    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 10 * 60 * 1000

    fun trackSplashScreenStarted() {
        _location.value?.let {
            getWeather(
                lat = _location.value?.latitude.toString(),
                lon = _location.value?.longitude.toString(),
                appId = AppSettings.appId
            )
        }

        scheduleWeatherUpdates()
    }

    private fun getWeather(lat: String, lon: String, appId: String) {
        _status.value = WeatherStatus.LOADING
        viewModelScope.launch {
            try {
                val openWeather = withContext(Dispatchers.IO) {
                    val time = Calendar.getInstance()
                    val callback = OpenWeatherApi
                        .retrofitService.getWeather(
                            lat = lat,
                            lon = lon,
                            time = time.timeInMillis.toString(),
                            exclude = "minutely,daily",
                            appId = appId
                        )

                    val response = callback.execute()
                    if (response.isSuccessful) {
                        response.body()
                    } else {
                        null
                    }
                }

                if (openWeather != null) {
                    _openWeather.value = openWeather
                    _status.value = WeatherStatus.DONE
                } else {
                    _status.value = WeatherStatus.ERROR
                    _throwable.value = "Empty or invalid response"
                }
            } catch (e: Exception) {
                _status.value = WeatherStatus.ERROR
                _throwable.value = "Error: ${e.message}"
            }
        }
    }

    private fun scheduleWeatherUpdates() {
        handler.postDelayed({
            _location.value.let {
                getWeather(
                    lat = it?.latitude.toString(),
                    lon = it?.longitude.toString(),
                    appId = AppSettings.appId
                )
            }
            scheduleWeatherUpdates()
        }, updateInterval.toLong())
    }

    override fun onCleared() {
        handler.removeCallbacksAndMessages(null)
        super.onCleared()
    }
}