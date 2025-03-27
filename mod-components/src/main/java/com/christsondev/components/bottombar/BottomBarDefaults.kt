package com.christsondev.components.bottombar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class BottomBarColors(
    val selected: Color = Color.Unspecified,
    val unselected: Color = Color.Unspecified,
)

object BottomBarDefaults {

    @Composable
    fun colors(
        selected: Color = AppTheme.color.onSurface,
        unselected: Color = AppTheme.color.onSurface.copy(alpha = 0.5f),
    ) = BottomBarColors(
        selected = selected,
        unselected = unselected,
    )
}