package com.danielvilha.open_weather.presentation.splashscreen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.danielvilha.open_weather.R
import com.danielvilha.open_weather.theme.OpenWeatherTheme
import com.danielvilha.open_weather.util.ExcludeFromJacocoGeneratedReport
import com.danielvilha.open_weather.util.LightDarkPreview
import kotlinx.coroutines.delay

private const val SPLASH_DELAY: Long = 1000

/**
 * Created by Daniel Ferreira de Lima Vilha 28/01/2024.
 */

@LightDarkPreview
@Composable
@ExcludeFromJacocoGeneratedReport
private fun Preview() {
    OpenWeatherTheme {
        Content(
            modifier = Modifier.fillMaxSize(),
            valid = true,
            onStart = { /*TODO*/ },
            onSplashEndedValid = { /*TODO*/ },
            onSplashEndedInvalid = { /*TODO*/ }
        )
    }
}
@Composable
fun SplashScreen(
    valid: State<Boolean?>,
    onStart: () -> Unit,
    onSplashEndedValid: () -> Unit,
    onSplashEndedInvalid: () -> Unit,
) {
    OpenWeatherTheme {
        Content(
            modifier = Modifier.fillMaxSize(),
            valid = valid.value,
            onStart = { onStart() },
            onSplashEndedValid = { onSplashEndedValid() },
            onSplashEndedInvalid = { onSplashEndedInvalid() }
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    valid: Boolean?,
    onStart: () -> Unit,
    onSplashEndedValid: () -> Unit,
    onSplashEndedInvalid: () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val color = remember { Animatable(Color.Black) }
    val currentValid = rememberUpdatedState(newValue = valid)

    LaunchedEffect(key1 = null) {
        delay(SPLASH_DELAY)
        if (currentValid.value == true) onSplashEndedValid()
        else onSplashEndedInvalid()
    }

    LaunchedEffect(key1 = valid) {
        valid?.let { valid ->
            val animateToColor = if (valid) Color.Green else Color.Red
            color.animateTo(animateToColor, animationSpec = tween(500))
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF424D5C))
            ) {
                Column(
                    modifier = modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_weather_background),
                        contentDescription = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .size(250.dp),
                    )
                }
            }
        }
    )

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                onStart()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
