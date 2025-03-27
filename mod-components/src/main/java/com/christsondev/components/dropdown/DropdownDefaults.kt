package com.christsondev.components.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class DropdownColors(
    val text: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val selectedText: Color = Color.Unspecified,
    val selectedContainer: Color = Color.Unspecified,
    val dropdownContainer: Color = Color.Unspecified,
)

data object DropdownDefaults {

    @Composable
    fun colors(
        text: Color = AppTheme.color.onSurface,
        container: Color = AppTheme.color.surface,
        selectedText: Color = AppTheme.color.onSurfaceVariant,
        selectedContainer: Color = AppTheme.color.surfaceVariant,
        dropdownContainer: Color = AppTheme.color.background,
    ) = DropdownColors(
        text = text,
        container = container,
        selectedText = selectedText,
        selectedContainer = selectedContainer,
        dropdownContainer = dropdownContainer,
    )
}