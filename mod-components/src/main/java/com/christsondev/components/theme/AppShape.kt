package com.christsondev.components.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Immutable
data class AppShape(
    val none: Shape = RoundedCornerShape(ZeroCornerSize),
    val small: Shape = RoundedCornerShape(8.dp),
    val medium: Shape = RoundedCornerShape(16.dp),
    val large: Shape = RoundedCornerShape(24.dp),
    val full: Shape = CircleShape,
)

internal val LocalAppShape = staticCompositionLocalOf<AppShape> {
    error("Shape not set")
}

internal val appShape = AppShape()