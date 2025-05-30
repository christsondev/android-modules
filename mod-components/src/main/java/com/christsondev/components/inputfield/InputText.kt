package com.christsondev.components.inputfield

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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
//        onValueChange = { newValue ->
//            val sanitizedValue = newValue.replace(",", "")
//
//            val filteredValue = when (keyboardType) {
//                KeyboardType.Number,
//                KeyboardType.NumberPassword -> sanitizedValue.filter { it.isDigit() }.ifBlank { "0" }
//                KeyboardType.Decimal -> sanitizedValue.validateDecimalInput()
//                KeyboardType.Email -> sanitizedValue // Let the user type freely
//                else -> sanitizedValue
//            }
//
//            text = filteredValue
//            onTextChanged(filteredValue)
//        },
//        onValueChange = { newValue ->
//            when (keyboardType) {
//                KeyboardType.Number,
//                KeyboardType.NumberPassword,
//                KeyboardType.Decimal, -> {
//                    val sanitizedValue = newValue.replace(",", "")
//
//                    text = when {
//                        sanitizedValue.isBlank() -> "0"
//                        sanitizedValue.validateInput() -> sanitizedValue
//                        else -> text
//                    }
//
//                    onTextChanged.invoke(text)
//                }
//
//                else -> {
//                    text = newValue
//                    onTextChanged.invoke(text)
//                }
//            }
//        },
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

private fun String.validateDecimalInput(): String {
    return if (matches(Regex("^\\d*\\.?\\d{0,2}\$"))) this else this.dropLast(1)
}

private fun String.validateInput() = this.matches(Regex("^\\d*\\.?\\d{0,2}\$"))
