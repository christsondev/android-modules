package com.christsondev.components.inputfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme
import kotlin.math.min
import kotlin.math.sin

data class InputColors(
    val text: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val hint: Color = Color.Unspecified,
    val disabledText: Color = Color.Unspecified,
    val disabledContainer: Color = Color.Unspecified,
)

data class InputConfigs(
    val singleLine: Boolean,
    val minLines: Int,
    val maxLines: Int,
)

object InputDefaults {

    @Composable
    fun colors(
        text: Color = AppTheme.color.onSurface,
        container: Color = AppTheme.color.surface,
        hint: Color = AppTheme.color.onSurface.copy(alpha = 0.7f),
        disabledText: Color = AppTheme.color.onSurface.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.onSurface,
    ) = InputColors(
        text = text,
        container = container,
        hint = hint,
        disabledText = disabledText,
        disabledContainer = disabledContainer,
    )

    @Composable
    fun configs(
        singleLine: Boolean = true,
        minLines: Int = 1,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    ) = InputConfigs(
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
    )
}