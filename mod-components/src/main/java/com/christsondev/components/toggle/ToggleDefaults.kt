package com.christsondev.components.toggle

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class ToggleColors(
    val text: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val selectedText: Color = Color.Unspecified,
    val selectedContainer: Color = Color.Unspecified,
    val disabledText: Color = Color.Unspecified,
    val disabledContainer: Color = Color.Unspecified,
)

object ToggleDefaults {

    @Composable
    fun colors(
        text: Color = AppTheme.color.onPrimaryContainer,
        container: Color = AppTheme.color.primaryContainer,
        selectedText: Color = AppTheme.color.onPrimary,
        selectedContainer: Color = AppTheme.color.primary,
        disabledText: Color = AppTheme.color.onPrimary.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.primaryContainer,
    ) = ToggleColors(
        text = text,
        container = container,
        selectedText = selectedText,
        selectedContainer = selectedContainer,
        disabledText = disabledText,
        disabledContainer = disabledContainer,
    )
}