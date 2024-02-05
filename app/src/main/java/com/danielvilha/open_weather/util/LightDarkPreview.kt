package com.danielvilha.open_weather.util

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

private const val DARK_MODE_PREVIEW_NAME = "Dark Mode"
private const val LIGHT_MODE_PREVIEW_NAME = "Light Mode"

/**
 * Created by Daniel Ferreira de Lima Vilha 28/01/2024.
 */
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    name = LIGHT_MODE_PREVIEW_NAME,
    device = Devices.NEXUS_7,
    apiLevel = 33
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = DARK_MODE_PREVIEW_NAME,
    device = Devices.NEXUS_7,
    apiLevel = 33
)
@ExcludeFromJacocoGeneratedReport
annotation class LightDarkPreview()
