package com.christsondev.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppSize(
    val icon: Dp = 24.dp,
    val iconMedium: Dp = 48.dp,
    val iconBig: Dp = 72.dp,
)

internal val LocalAppSize = staticCompositionLocalOf<AppSize> {
    error("Shape not set")
}

internal val appSize = AppSize()