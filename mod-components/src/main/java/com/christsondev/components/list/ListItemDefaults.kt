package com.christsondev.components.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class ListItemColors(
    val primary: Color = Color.Unspecified,
    val text: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
)

data object ListItemDefaults {

    @Composable
    fun colors(
        primaryColor: Color = AppTheme.color.onSurface,
        textColor: Color = AppTheme.color.onSurface,
        containerColor: Color = AppTheme.color.surface,
    ) = ListItemColors(
        primary = primaryColor,
        text = textColor,
        container = containerColor,
    )
}