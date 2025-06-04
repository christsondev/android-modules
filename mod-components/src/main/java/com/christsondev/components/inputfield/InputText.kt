package com.christsondev.components.inputfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.christsondev.components.theme.AppTheme

@Composable
internal fun InputText(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    enabled: Boolean = true,
    colors: InputColors = InputDefaults.colors(),
    configs: InputConfigs = InputDefaults.configs(),
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
) {
    if (configs.fixedCursor) {
        DecimalInputField(
            modifier = modifier,
            value = value,
            hint = hint,
            enabled = enabled,
            colors = colors,
            configs = configs,
            onTextChanged = onTextChanged,
            onDone = onDone,
        )
    } else {
        DefaultInputField(
            modifier = modifier,
            value = value,
            hint = hint,
            enabled = enabled,
            colors = colors,
            configs = configs,
            keyboardType = keyboardType,
            onTextChanged = onTextChanged,
            onDone = onDone,
        )
    }
}

@Composable
private fun DefaultInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    enabled: Boolean = true,
    colors: InputColors = InputDefaults.colors(),
    configs: InputConfigs = InputDefaults.configs(),
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
) {
    var text by remember(value) { mutableStateOf(value) }
    val textColor = if (enabled) colors.text else colors.disabledText
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onDone.invoke(text)
                }
                isFocused = focusState.isFocused
            },
        value = text,
        onValueChange = {
            text = it
            onTextChanged(it)
        },
        singleLine = configs.singleLine,
        minLines = configs.minLines,
        maxLines = configs.maxLines,
        enabled = enabled,
        textStyle = AppTheme.typography.body.copy(color = textColor),
        decorationBox = { innerTextField ->
            if (text.isEmpty()) {
                Text(
                    text = hint,
                    style = AppTheme.typography.body,
                    color = colors.hint,
                )
            }
            innerTextField()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone.invoke(text)
            }
        ),
    )
}

@Composable
private fun DecimalInputField(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    enabled: Boolean = true,
    colors: InputColors = InputDefaults.colors(),
    configs: InputConfigs = InputDefaults.configs(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onTextChanged: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
) {
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = formatValue(value))) }
    val textColor = if (enabled) colors.text else colors.disabledText
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    // Update internal TextFieldValue when the external 'value' changes
    LaunchedEffect(value) {
        val formattedValue = formatValue(value)
        if (textFieldValueState.text != formattedValue) {
            textFieldValueState =
                TextFieldValue(text = formattedValue, selection = TextRange(formattedValue.length))
        }
    }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onDone.invoke(textFieldValueState.text)
                }
                isFocused = focusState.isFocused
            },
        value = textFieldValueState,
        onValueChange = { newValue ->
            val cleanInput = newValue.text.filter { it.isDigit() }
            val formattedResult = formatInputToDecimal(cleanInput)
            onTextChanged(formattedResult)

            // Update internal state to reflect the formatted value and keep cursor at the end
            // This also effectively prevents the cursor from moving to any other position
            textFieldValueState = TextFieldValue(
                text = formattedResult,
                selection = TextRange(formattedResult.length)
            )
        },
        singleLine = configs.singleLine,
        minLines = configs.minLines,
        maxLines = configs.maxLines,
        enabled = enabled,
        textStyle = AppTheme.typography.body.copy(color = textColor),
        decorationBox = { innerTextField ->
            if (textFieldValueState.text.isEmpty()) {
                Text(
                    text = hint,
                    style = AppTheme.typography.body,
                    color = colors.hint,
                )
            }
            innerTextField()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone.invoke(textFieldValueState.text)
            }
        ),
        visualTransformation = VisualTransformation.None, // We handle formatting internally
        interactionSource = interactionSource,
    )
}

// Function to format the raw number string into a two-decimal price string
private fun formatInputToDecimal(input: String): String {
    val cleanInput = input.filter { it.isDigit() }

    // If the input represents a numerical value of 0, display ".00"
    // This handles "", "0", "00", "000", etc.
    if (cleanInput.toLongOrNull() == 0L || cleanInput.isEmpty()) {
        return "0.00"
    }

    // Ensure at least two digits for decimal part by padding with leading zeros
    // if the input is less than 2 digits (e.g., "1" becomes "01")
    val paddedInput = cleanInput.padStart(2, '0')

    // Split into integer and decimal parts
    val integerPart = paddedInput.substring(0, paddedInput.length - 2)
    val decimalPart = paddedInput.substring(paddedInput.length - 2)

    val finalIntegerPart = integerPart.trimStart('0') // Remove leading zeros from integer part

    return if (finalIntegerPart.isEmpty()) {
        // This case covers inputs like "01", "07" etc., which are less than 1.00
        // and whose integer part becomes empty after trimming leading zeros.
        // It will prepend "0." for these (e.g., 0.01, 0.07).
        "0.$decimalPart"
    } else {
        // For values >= 1.00 (e.g., "1.23", "10.50")
        "$finalIntegerPart.$decimalPart"
    }
}

// Function to ensure the initial value is always formatted correctly for the TextField's display
private fun formatValue(value: String): String {
    // This helper ensures that the initial `value` provided to the composable
    // is also passed through our formatting logic, especially for the ".00" case.
    val cleanInput = value.filter { it.isDigit() }
    return formatInputToDecimal(cleanInput)
}