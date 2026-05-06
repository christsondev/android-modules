package com.christsondev.components.inputfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
internal fun InputText(
    modifier: Modifier = Modifier,
    value: String = "",
    hint: String = "",
    enabled: Boolean = true,
    colors: InputColors = InputDefaults.colors(),
    configs: InputConfigs = InputDefaults.configs(),
    keyboardType: KeyboardType = KeyboardType.Text,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onTextChanged: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
) {
    val textColor = if (enabled) colors.text else colors.disabledText
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    // Unified state using TextFieldValue to handle cursor logic for both standard and decimal inputs
    var textFieldValue by remember(value, configs.fixedCursor) {
        val initialText = if (configs.fixedCursor) formatValue(value) else value
        mutableStateOf(
            TextFieldValue(
                text = initialText,
                selection = TextRange(initialText.length)
            )
        )
    }

    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                if (isFocused && !focusState.isFocused) {
                    onDone.invoke(textFieldValue.text)
                }
                isFocused = focusState.isFocused
            },
        value = textFieldValue,
        onValueChange = { newValue ->
            if (configs.fixedCursor) {
                val cleanInput = newValue.text.filter { it.isDigit() }
                val formattedResult = formatInputToDecimal(cleanInput)
                textFieldValue = TextFieldValue(
                    text = formattedResult,
                    selection = TextRange(formattedResult.length)
                )
                onTextChanged(formattedResult)
            } else {
                textFieldValue = newValue
                onTextChanged(newValue.text)
            }
        },
        singleLine = configs.singleLine,
        minLines = configs.minLines,
        maxLines = configs.maxLines,
        enabled = enabled,
        textStyle = configs.textStyle.copy(color = textColor),
        decorationBox = { innerTextField ->
            if (textFieldValue.text.isEmpty()) {
                Text(
                    text = hint,
                    style = configs.textStyle,
                    color = colors.hint,
                )
            }
            innerTextField()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = if (configs.fixedCursor) KeyboardType.Number else keyboardType,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                onDone.invoke(textFieldValue.text)
            }
        ),
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
    )
}

private fun formatInputToDecimal(input: String): String {
    val cleanInput = input.filter { it.isDigit() }
    if (cleanInput.toLongOrNull() == 0L || cleanInput.isEmpty()) return "0.00"

    val paddedInput = cleanInput.padStart(2, '0')
    val integerPart = paddedInput.substring(0, paddedInput.length - 2).trimStart('0')
    val decimalPart = paddedInput.substring(paddedInput.length - 2)

    return if (integerPart.isEmpty()) "0.$decimalPart" else "$integerPart.$decimalPart"
}

private fun formatValue(value: String): String {
    val cleanInput = value.filter { it.isDigit() }
    return formatInputToDecimal(cleanInput)
}
