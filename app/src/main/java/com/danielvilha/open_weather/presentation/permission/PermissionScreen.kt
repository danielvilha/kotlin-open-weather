package com.danielvilha.open_weather.presentation.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.danielvilha.open_weather.theme.OpenWeatherTheme
import com.danielvilha.open_weather.util.ExcludeFromJacocoGeneratedReport
import com.danielvilha.open_weather.util.LightDarkPreview

/**
 * Created by Daniel Ferreira de Lima Vilha 05/02/2024.
 */
@Composable
@LightDarkPreview
@ExcludeFromJacocoGeneratedReport
private fun Preview() {
    OpenWeatherTheme {
        Content(onPermissionGranted = { })
    }
}

@Composable
fun PermissionScreen(onPermissionGranted: () -> Unit) {
    OpenWeatherTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Content(onPermissionGranted)
                }
            }
        )
    }
}

@Composable
private fun Content(onPermissionGranted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                onPermissionGranted()
            }
        ) {
            Text(text = "Grant Location Permission")
        }
    }
}
