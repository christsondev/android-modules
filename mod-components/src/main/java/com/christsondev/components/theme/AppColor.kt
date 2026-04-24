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

// --- Core Brand Colors ---
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

// --- Status & Feedback (Semantic) ---
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,

    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color,

    val error: Color,
    val onError: Color,
    val errorContainer: Color,
    val onErrorContainer: Color,

    val info: Color,
    val onInfo: Color,

// --- Surfaces & Backgrounds ---
    val background: Color,
    val onBackground: Color,

    val surface: Color,
    val onSurface: Color,

    val surfaceVariant: Color,
    val onSurfaceVariant: Color,

    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerLow: Color,

    val inverseSurface: Color,
    val inverseOnSurface: Color,

// --- Borders & Utilities ---
    val outline: Color,
    val outlineVariant: Color,
    val shadow: Color,
    val scrim: Color,  // For modal backgrounds
)

internal val lightThemeColor = AppColor( 
    primary = Color(0xFF006677),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF006677).copy(alpha = 0.1f),
    onPrimaryContainer = Color(0xFF004D5A),
    secondary = Color(0xFF5A6263),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE1E3E4),
    onSecondaryContainer = Color(0xFF171D1E),
    tertiary = Color(0xFF5D5F61),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE2E2E6),
    onTertiaryContainer = Color(0xFF1A1C1E),
    success = Color(0xFF2E7D32),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFC8E6C9),
    onSuccessContainer = Color(0xFF003300),
    warning = Color(0xFFFF9100),
    onWarning = Color(0xFFFFFFFF),
    warningContainer = Color(0xFFFFF3E0),
    onWarningContainer = Color(0xFFE65100),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    info = Color(0xFF0288D1),
    onInfo = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF191C1D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C1D),
    surfaceVariant = Color(0xFFF1F4F4),
    onSurfaceVariant = Color(0xFF3F484A),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFF1F4F4),
    surfaceContainerLow = Color(0xFFFFFFFF),
    inverseSurface = Color(0xFF2E3132),
    inverseOnSurface = Color(0xFFEFF1F1),
    outline = Color(0xFF6F797B),
    outlineVariant = Color(0xFFBFC8CA),
    shadow = Color(0xFF000000).copy(alpha = 0.05f),
    scrim = Color(0xFF000000).copy(alpha = 0.32f),
)

internal val darkThemeColor = AppColor(
    primary = Color(0xFF006677),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFF006677).copy(alpha = 0.1f),
    onPrimaryContainer = Color(0xFF004D5A),
    secondary = Color(0xFF5A6263),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE1E3E4),
    onSecondaryContainer = Color(0xFF171D1E),
    tertiary = Color(0xFF5D5F61),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFE2E2E6),
    onTertiaryContainer = Color(0xFF1A1C1E),
    success = Color(0xFF2E7D32),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFC8E6C9),
    onSuccessContainer = Color(0xFF003300),
    warning = Color(0xFFFF9100),
    onWarning = Color(0xFFFFFFFF),
    warningContainer = Color(0xFFFFF3E0),
    onWarningContainer = Color(0xFFE65100),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    info = Color(0xFF0288D1),
    onInfo = Color(0xFFFFFFFF),
    background = Color(0xFFF8F9FA),
    onBackground = Color(0xFF191C1D),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C1D),
    surfaceVariant = Color(0xFFF1F4F4),
    onSurfaceVariant = Color(0xFF3F484A),
    surfaceContainer = Color(0xFFFFFFFF),
    surfaceContainerHigh = Color(0xFFF1F4F4),
    surfaceContainerLow = Color(0xFFFFFFFF),
    inverseSurface = Color(0xFF2E3132),
    inverseOnSurface = Color(0xFFEFF1F1),
    outline = Color(0xFF6F797B),
    outlineVariant = Color(0xFFBFC8CA),
    shadow = Color(0xFF000000).copy(alpha = 0.05f),
    scrim = Color(0xFF000000).copy(alpha = 0.32f),  // For modal backgrounds
)