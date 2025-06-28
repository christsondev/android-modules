package com.christsondev.components.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.button.Button
import com.christsondev.components.button.ButtonColors
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme
import com.christsondev.utilities.AppDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeSelector(
    initialStartDate: AppDate,
    initialEndDate: AppDate,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    initialText: String,
    confirmButtonText: String,
    colors: DateSelectorColors = DateSelectorDefaults.colors(),
    onDateChanged: (AppDate?, AppDate?) -> Unit,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = initialStartDate.timeInMillis,
        initialSelectedEndDateMillis = initialEndDate.timeInMillis,
    )

    val iconTint = if (enabled) colors.font else colors.disabledFont
    val buttonColors = ButtonColors(
        font = colors.font,
        container = colors.container,
        disabledFont = colors.disabledFont,
        disabledContainer = colors.disabledContainer,
    )

    Button(
        modifier = Modifier.then(modifier),
        enabled = enabled,
        colors = buttonColors,
        type = Button.Type.IconWithText(
            icon = IconComposer.Icon(Icons.Rounded.CalendarMonth, tint = iconTint),
            title = initialText,
        )
    ) {
        showDialog = true
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    type = Button.Type.Text(confirmButtonText),
                    colors = buttonColors,
                ) {
                    val start = state.selectedStartDateMillis?.let { AppDate(it) }
                    val end = state.selectedEndDateMillis?.let { AppDate(it) }
                    onDateChanged.invoke(start, end)
                    showDialog = false
                }
            },
        ) {
            DateRangePicker(state = state, showModeToggle = false)
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateRangeSelector(
                initialStartDate = AppDate.now(),
                initialEndDate = AppDate.now(),
                initialText = "16-10-1993",
                confirmButtonText = "",
            ) { a, b -> }

            DateRangeSelector(
                initialStartDate = AppDate.now(),
                initialEndDate = AppDate.now(),
                initialText = "16 OCT 1993",
                confirmButtonText = "",
                enabled = false,
            ) { a, b -> }
        }
    }
}