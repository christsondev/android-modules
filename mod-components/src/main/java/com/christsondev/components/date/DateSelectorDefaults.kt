package com.christsondev.components.date

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class DateSelectorColors(
    val font: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val disabledFont: Color = Color.Unspecified,
    val disabledContainer: Color = Color.Unspecified,
)

object DateSelectorDefaults {

    @Composable
    fun colors(
        font: Color = AppTheme.color.onPrimary,
        container: Color =AppTheme.color.primary,
        disabledFont: Color = AppTheme.color.onPrimary.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.primary,
    ) = DateSelectorColors(
        font = font,
        container = container,
        disabledFont = disabledFont,
        disabledContainer = disabledContainer,
    )
}