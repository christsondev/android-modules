package com.christsondev.components.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.button.Button
import com.christsondev.components.button.ButtonColors
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme
import com.christsondev.utilities.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    initialDate: Date,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    buttonText: String = "",
    colors: DateSelectorColors = DateSelectorDefaults.colors(),
    onDateChanged: (Date?) -> Unit,
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val datePickerState = remember(initialDate) {
        DatePickerState(
            locale = Locale.getDefault(),
            initialSelectedDateMillis = initialDate.millis,
        )
    }

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
            title = initialDate.toDayMonthYearDash(),
        )
    ) {
        showDialog = true
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(
                    type = Button.Type.Text(buttonText),
                    colors = buttonColors,
                ) {
                    val newDate = datePickerState.selectedDateMillis?.let { Date(it) }
                    onDateChanged.invoke(newDate)
                    showDialog = false
                }
            },
        ) {
            DatePicker(state = datePickerState, showModeToggle = false)
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
            DateSelector(
                initialDate = Date(System.currentTimeMillis()),
            ) { }

            DateSelector(
                initialDate = Date(System.currentTimeMillis()),
                enabled = false,
            ) { }
        }
    }
}