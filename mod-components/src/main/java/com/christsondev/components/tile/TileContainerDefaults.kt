package com.christsondev.components.tile

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.christsondev.components.theme.AppTheme

internal object TileContainerDefaults {
    val elevation: Dp = 3.dp
    val dashWidth: Dp = 2.dp
    val cornerRadius: RoundedCornerShape = RoundedCornerShape(16.dp)

    val dashIntervals = floatArrayOf(20f, 10f)

    val dashColor: Color
        @Composable
        @ReadOnlyComposable
        get() = AppTheme.color.outlineVariant

    val containerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = AppTheme.color.background
}
