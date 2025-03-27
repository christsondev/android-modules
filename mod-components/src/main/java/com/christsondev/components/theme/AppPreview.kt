package com.christsondev.components.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview
annotation class AppPreview

@Preview(
    name = "LightTheme",
    showBackground = false,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "DarkTheme",
    showBackground = false,
    showSystemUi = false,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class AppMultiPreview