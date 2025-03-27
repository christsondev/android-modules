package com.christsondev.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.christsondev.components.theme.AppTheme

data class ButtonColors(
    val font: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val disabledFont: Color = Color.Unspecified,
    val disabledContainer: Color = Color.Unspecified,
)

enum class ButtonContainer {
    NONE,
    FILL,
    OUTLINE;

    @Composable
    fun getModifier(containerColor: Color) = when (this) {
        NONE -> Modifier
        FILL -> Modifier.background(containerColor)
        OUTLINE -> Modifier.border(
            width = 1.dp,
            color = containerColor,
            shape = AppTheme.shape.full,
        )
    }
}

data object ButtonDefaults {

    @Composable
    fun colors(
        font: Color = AppTheme.color.onPrimary,
        container: Color = AppTheme.color.primary,
        disabledFont: Color = AppTheme.color.onPrimary.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.primary,
    ) = ButtonColors(
        font = font,
        container = container,
        disabledFont = disabledFont,
        disabledContainer = disabledContainer,
    )

    @Composable
    fun default(
        font: Color = AppTheme.color.primary,
        container: Color = AppTheme.color.transparent,
        disabledFont: Color = AppTheme.color.primary.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.transparent,
    ) = ButtonColors(
        font = font,
        container = container,
        disabledFont = disabledFont,
        disabledContainer = disabledContainer,
    )

    @Composable
    fun error(
        font: Color = AppTheme.color.error,
        container: Color = AppTheme.color.transparent,
        disabledFont: Color = AppTheme.color.error.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.transparent,
    ) = ButtonColors(
        font = font,
        container = container,
        disabledFont = disabledFont,
        disabledContainer = disabledContainer,
    )
}