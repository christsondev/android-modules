package com.christsondev.components.theme

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.LocalActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import java.util.Locale

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    locale: Locale = Locale.getDefault(),
    isPreview: Boolean = true,
    colors: AppThemeColor = AppTheme.Default.colors(),
    content: @Composable () -> Unit,
) = CompositionLocalProvider(
    LocalContentColor provides if (useDarkTheme) Color.White else Color.Black,
    LocalAppColor provides if (useDarkTheme) colors.dark else colors.light,
    LocalAppTypography provides appTypography,
    LocalAppShape provides appShape,
    LocalAppLocale provides locale,
) {
    if (!isPreview) {
        AdjustStatusBarTheme(useDarkTheme, colors)
    }

    content()
}

object AppTheme {

    val color: AppColor
        @ReadOnlyComposable
        @Composable
        get() = LocalAppColor.current
    val typography: AppTypography
        @ReadOnlyComposable
        @Composable
        get() = LocalAppTypography.current
    val shape: AppShape
        @ReadOnlyComposable
        @Composable
        get() = LocalAppShape.current

    object Default {

        fun colors(
            light: AppColor = lightThemeColor,
            dark: AppColor = darkThemeColor,
        ) = AppThemeColor(
            light = light,
            dark = dark,
        )
    }
}

data class AppThemeColor(
    val light: AppColor,
    val dark: AppColor,
)

// region adjust status bar theme
@Composable
private fun AdjustStatusBarTheme(
    useDarkTheme: Boolean,
    colors: AppThemeColor,
) {
    val context = LocalActivity.current as? ComponentActivity

    DisposableEffect(useDarkTheme) {
        val systemBarStyle = if (useDarkTheme) {
            SystemBarStyle.dark(
                scrim = colors.dark.background.toArgb(),
            )
        } else {
            SystemBarStyle.light(
                scrim = colors.light.background.toArgb(),
                darkScrim = colors.dark.background.toArgb(),
            )
        }

        context?.enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = systemBarStyle,
        )

        onDispose { }
    }
}

// endregion