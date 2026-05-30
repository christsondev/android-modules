package com.christsondev.components.date

import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.christsondev.components.theme.AppTheme

data class DateSelectorColors(
    val font: Color = Color.Unspecified,
    val container: Color = Color.Unspecified,
    val disabledFont: Color = Color.Unspecified,
    val disabledContainer: Color = Color.Unspecified,
    val datePickerColors: DatePickerColors,
)

object DateSelectorDefaults {

    @Composable
    fun colors(
        font: Color = AppTheme.color.onPrimary,
        container: Color =AppTheme.color.primary,
        disabledFont: Color = AppTheme.color.onPrimary.copy(alpha = 0.3f),
        disabledContainer: Color = AppTheme.color.primary,
        datePickerColors: DatePickerColors = DatePickerDefaults.colors(),
    ) = DateSelectorColors(
        font = font,
        container = container,
        disabledFont = disabledFont,
        disabledContainer = disabledContainer,
        datePickerColors = datePickerColors,
    )
}