package com.christsondev.components.tile

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.christsondev.components.theme.AppTheme

data class TileBorder(
    val elevation: Dp,
    val cornerRadius: Shape,
)

data object TileBorderDefaults {

    @Composable
    fun flat(
        cornerRadius: Shape = AppTheme.shape.medium,
    ) = TileBorder(
        elevation = 0.dp,
        cornerRadius = cornerRadius,
    )

    @Composable
    fun elevated(
        elevation: Dp = 3.dp,
        cornerRadius: Shape = AppTheme.shape.medium,
    ) = TileBorder(
        elevation = elevation,
        cornerRadius = cornerRadius,
    )
}