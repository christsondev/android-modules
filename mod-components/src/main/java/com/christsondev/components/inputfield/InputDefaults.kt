package com.christsondev.components.inputfield

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.christsondev.components.theme.AppTheme

data class InputColors(
    val text: Color = Color.Unspecified,
    val hint: Color = Color.Unspecified,
    val disabledText: Color = Color.Unspecified,
)

data class InputConfigs(
    val singleLine: Boolean,
    val minLines: Int,
    val maxLines: Int,
    val fixedCursor: Boolean,
    val textStyle: TextStyle,
)

object InputDefaults {

    @Composable
    fun colors(
        text: Color = AppTheme.color.onSurface,
        hint: Color = AppTheme.color.onSurface.copy(alpha = 0.7f),
        disabledText: Color = AppTheme.color.onSurface.copy(alpha = 0.3f),
    ) = InputColors(
        text = text,
        hint = hint,
        disabledText = disabledText,
    )

    @Composable
    fun configs(
        singleLine: Boolean = true,
        minLines: Int = 1,
        maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
        fixedCursor: Boolean = false,
        textStyle: TextStyle = AppTheme.typography.body,
    ) = InputConfigs(
        singleLine = singleLine,
        minLines = minLines,
        maxLines = maxLines,
        fixedCursor = fixedCursor,
        textStyle = textStyle,
    )
}
