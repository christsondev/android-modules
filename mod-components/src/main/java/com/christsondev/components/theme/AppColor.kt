package com.christsondev.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val LocalAppColor = staticCompositionLocalOf<AppColor> {
    error("LocalAppColor were not set")
}

@Immutable
data class AppColor(
    val transparent: Color = Color.Transparent,
    val white: Color = Color.White,
    val black: Color = Color.Black,
    val success: Color = Color.Green,
    val warning: Color = Color(0xFFFF9100),
    val background: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val tertiaryContainer: Color,
    val onTertiaryContainer: Color,
    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceContainer: Color,
    val outline: Color,
    val outlineVariant: Color,
    val shadow: Color,
)

internal val lightThemeColor = AppColor(
    background = Color(0xFFFFF8F7),
    primary = Color(0xFF8F4A4C),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDAD9),
    onPrimaryContainer = Color(0xFF3B080E),
    secondary = Color(0xFF775656),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDAD9),
    onSecondaryContainer = Color(0xFF2C1516),
    tertiary = Color(0xFF755A2F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDDB0),
    onTertiaryContainer = Color(0xFF281800),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    surface = Color(0xFFFFF8F7),
    onSurface = Color(0xFF221919),
    surfaceVariant = Color(0xFFF4DDDD),
    onSurfaceVariant = Color(0xFF524343),
    surfaceContainer = Color(0xFFFCEAE9),
    outline = Color(0xFF857373),
    outlineVariant = Color(0xFFD7C1C1),
    shadow = Color(0xFF000000),
)

internal val darkThemeColor = AppColor(
    background = Color(0xFF251919),
    primary = Color(0xFFFFB3B4),
    onPrimary = Color(0xFF561D21),
    primaryContainer = Color(0xFF733336),
    onPrimaryContainer = Color(0xFFFFDAD9),
    secondary = Color(0xFFE6BDBC),
    onSecondary = Color(0xFF44292A),
    secondaryContainer = Color(0xFF5D3F3F),
    onSecondaryContainer = Color(0xFFFFDAD9),
    tertiary = Color(0xFFE5C18D),
    onTertiary = Color(0xFF422C05),
    tertiaryContainer = Color(0xFF5B421A),
    onTertiaryContainer = Color(0xFFFFDDB0),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    surface = Color(0xFF1A1111),
    onSurface = Color(0xFFF0DEDE),
    surfaceVariant = Color(0xFF524343),
    onSurfaceVariant = Color(0xFFD7C1C1),
    surfaceContainer = Color(0xFF271D1D),
    outline = Color(0xFFA08C8C),
    outlineVariant = Color(0xFF524343),
    shadow = Color(0xFF000000),
)