package com.danielvilha.open_weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val locationHelper = LocationHelper(this@MainActivity)
    private var navController: NavHostController? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            lifecycleScope.launch {
                locationHelper.requestLocationUpdates { location ->
                    if (location != null) {
                        locationReceived = true
                        viewModel.location.value = location
                        viewModel.trackSplashScreenStarted()
                        navController?.let { startWeatherScreen(it) }
                    }
                }
            }
        } else {
            navController?.let { startPermissionScreen(it) }
        }
    }

    private var locationReceived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navController = rememberNavController()
            val valid = viewModel.valid.collectAsState()
            val hasLocation = hasLocationPermission()

            OpenWeatherTheme {
                NavHost(
                    navController = navController!!,
                    startDestination = "SplashScreen",
                ) {
                    composable(route = "SplashScreen") {
                        SplashScreen(
                            valid = valid,
                            onStart = {
                                if (hasLocation) {
                                    lifecycleScope.launch {
                                        locationHelper.requestLocationUpdates {
                                            if (it != null) {
                                                locationReceived = true
                                                viewModel.location.value = it
                                                viewModel.trackSplashScreenStarted()
                                                startWeatherScreen(navController!!)
                                            }
                                        }
                                    }
                                } else {
                                    requestLocationPermission()
                                }
                            },
                            onSplashEndedValid = {
                            },
                            onSplashEndedInvalid = {
                                startPermissionScreen(navController!!)
                            }
                        )
                    }
                    composable(route = "WeatherScreen") {
                        if (viewModel.openWeather.value == null) {
                            runBlocking {
                                viewModel.trackSplashScreenStarted()
                                delay(1000L)
                            }
                        }
                        WeatherScreen(state = WeatherUiState(
                            status = viewModel.status.value ?: WeatherStatus.LOADING,
                            throwable = viewModel.throwable.value,
                            openWeather = viewModel.openWeather.value
                        ))
                    }
                    composable(route = "PermissionScreen") {
                        PermissionScreen(
                            isPermissionGranted = hasLocation,
                            onPermissionGranted = {
                                if (hasLocationPermission())
                                    startWeatherScreen(navController!!)
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
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun startWeatherScreen(navController: NavHostController) {
        navController.navigate("WeatherScreen") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }

    private fun startPermissionScreen(navController: NavHostController) {
        navController.navigate("PermissionScreen") {
            popUpTo("SplashScreen") { inclusive = true }
        }
    }
}
