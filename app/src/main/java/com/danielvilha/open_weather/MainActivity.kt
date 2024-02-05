package com.danielvilha.open_weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.danielvilha.models.LocationHelper
import com.danielvilha.models.WeatherStatus
import com.danielvilha.open_weather.presentation.permission.PermissionScreen
import com.danielvilha.open_weather.presentation.splashscreen.SplashScreen
import com.danielvilha.open_weather.presentation.weather.WeatherScreen
import com.danielvilha.open_weather.presentation.weather.WeatherUiState
import com.danielvilha.open_weather.theme.OpenWeatherTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val valid = viewModel.valid.collectAsState()
            val locationHelper = LocationHelper(this@MainActivity)

            OpenWeatherTheme {
                NavHost(
                    navController = navController,
                    startDestination = "SplashScreen",
                ) {
                    composable(route = "SplashScreen") {
                        SplashScreen(
                            valid = valid,
                            onStart = {
                                lifecycleScope.launch {
                                    locationHelper.requestLocationUpdates {
                                        viewModel.location.value = it
                                        viewModel.trackSplashScreenStarted()
                                        if (hasLocationPermission()) {
                                            startWeatherScreen(navController)
                                        } else {
                                            requestLocationPermission()
                                            startPermissionScreen(navController)
                                        }
                                    }
                                }
                            },
                            onSplashEndedValid = {
                                startWeatherScreen(navController)
                            }
                        ) { }
                    }
                    composable(route = "WeatherScreen") {
                        WeatherScreen(state = WeatherUiState(
                            status = viewModel.status.value ?: WeatherStatus.LOADING,
                            throwable = viewModel.throwable.value,
                            openWeather = viewModel.openWeather.value
                        ))
                    }
                    composable(route = "PermissionScreen") {
                        PermissionScreen(
                            onPermissionGranted = {
                                startWeatherScreen(navController)
                            }
                        )
                    }
                }
            }
        }
    }

    private fun hasLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun startWeatherScreen(navController: NavHostController) {
        if (hasLocationPermission()) {
            navController.navigate("WeatherScreen") {
                popUpTo("SplashScreen") { inclusive = true }
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun startPermissionScreen(navController: NavHostController) {
        navController.navigate("PermissionScreen") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }
}
