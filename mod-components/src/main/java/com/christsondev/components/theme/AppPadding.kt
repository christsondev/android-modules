package com.christsondev.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

internal val LocalAppPadding = staticCompositionLocalOf<AppPadding> {
    error("Shape not set")
}

internal val appPadding = AppPadding()