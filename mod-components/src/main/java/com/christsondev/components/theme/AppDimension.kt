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
data class AppDimension(
    val padding: AppPadding = AppPadding(),
    val shape: AppShape = AppShape(),
    val size: AppSize = AppSize(),
)

@Immutable
data class AppPadding(
    val none: Dp = 0.dp,
    val default: Dp = 16.dp,
    val container: Container = Container(),
    val icon: Icon = Icon(),
    val text: Text = Text(),
) {
    @Immutable
    data class Icon(
        val default: Dp = 12.dp,
        val medium: Dp = 16.dp,
        val large: Dp = 24.dp,
    )

    @Immutable
    data class Container(
        val smallBetweenItems: Dp = 8.dp,
        val betweenItems: Dp = 16.dp,
        val smallVertical: Dp = 8.dp,
        val vertical: Dp = 16.dp,
        val smallHorizontal: Dp = 8.dp,
        val horizontal: Dp = 16.dp,
    )

    @Immutable
    data class Text(
        val smallVertical: Dp = 6.dp,
        val vertical: Dp = 12.dp,
        val smallHorizontal: Dp = 12.dp,
        val horizontal: Dp = 24.dp,
    )
}

@Immutable
data class AppShape(
    val none: Shape = RoundedCornerShape(ZeroCornerSize),
    val small: Shape = RoundedCornerShape(8.dp),
    val medium: Shape = RoundedCornerShape(16.dp),
    val large: Shape = RoundedCornerShape(24.dp),
    val xlarge: Shape = RoundedCornerShape(32.dp),
    val full: Shape = CircleShape,
)

@Immutable
data class AppSize(
    val icon: Dp = 24.dp,
)

internal val LocalAppDimension = staticCompositionLocalOf<AppDimension> {
    error("Shape not set")
}

internal val appDimension = AppDimension()