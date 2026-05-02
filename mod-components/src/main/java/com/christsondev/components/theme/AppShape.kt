package com.christsondev.components.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppShape(
    val none: Shape = RoundedCornerShape(ZeroCornerSize),
    val small: Shape = RoundedCornerShape(8.dp),
    val medium: Shape = RoundedCornerShape(16.dp),
    val large: Shape = RoundedCornerShape(24.dp),
    val xlarge: Shape = RoundedCornerShape(32.dp),
    val topSmall: Shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
    val topMedium: Shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    val topLarge: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    val topXLarge: Shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
    val bottomSmall: Shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp),
    val bottomMedium: Shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
    val bottomLarge: Shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
    val bottomXLarge: Shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
    val startSmall: Shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
    val startMedium: Shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp),
    val startLarge: Shape = RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
    val startXLarge: Shape = RoundedCornerShape(topStart = 32.dp, bottomStart = 32.dp),
    val endSmall: Shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
    val endMedium: Shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
    val endLarge: Shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
    val endXLarge: Shape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp),
    val full: Shape = CircleShape,
)

internal val LocalAppShape = staticCompositionLocalOf<AppShape> {
    error("Shape not set")
}

internal val appShape = AppShape()