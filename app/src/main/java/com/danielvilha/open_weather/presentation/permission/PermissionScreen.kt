package com.danielvilha.open_weather.presentation.permission

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.danielvilha.open_weather.R
import com.danielvilha.open_weather.theme.OpenWeatherTheme
import com.danielvilha.open_weather.util.ExcludeFromJacocoGeneratedReport
import com.danielvilha.open_weather.util.LightDarkPreview

/**
 * Created by Daniel Ferreira de Lima Vilha 05/02/2024.
 */
@Composable
@LightDarkPreview
@ExcludeFromJacocoGeneratedReport
private fun Preview(
    @PreviewParameter(PermissionScreen::class)
    value : Boolean
) {
    OpenWeatherTheme {
        Content(
            isPermissionGranted = value,
            onPermissionGranted = { }
        )
    }
}

@Composable
fun PermissionScreen(
    isPermissionGranted: Boolean,
    onPermissionGranted: () -> Unit,
) {
    OpenWeatherTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = { innerPadding ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    Content(
                        isPermissionGranted,
                        onPermissionGranted
                    )
                }
            }
        )
    }
}

@Composable
private fun Content(
    isPermissionGranted: Boolean,
    onPermissionGranted: () -> Unit,
) {
    val permissionGranted by remember { mutableStateOf(isPermissionGranted) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF424D5C)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!permissionGranted) {
            Text(
                text = stringResource(id = R.string.about_open_weather),
                modifier = Modifier.padding(32.dp),
                color = Color(0xFFDCDCED),
                fontSize = 24.sp,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
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
    }
}

@ExcludeFromJacocoGeneratedReport
private class PermissionScreen : PreviewParameterProvider<Boolean> {

    override val values: Sequence<Boolean>
        get() = sequenceOf(
            false,
            true
        )

}
