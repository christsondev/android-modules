package com.christsondev.components.inputfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

object InputField {
    sealed interface Type {
        data class Text(
            val value: String = "",
            val hint: String = "",
            val keyboardType: KeyboardType = KeyboardType.Text,
            val onTextChanged: (String) -> Unit = {},
            val onDone: (String) -> Unit = {},
        ): Type

        data class WithLabel(
            val label: String,
            val value: String = "",
            val hint: String = "",
            val labelWeight: Float = 0.3f,
            val keyboardType: KeyboardType = KeyboardType.Text,
            val onTextChanged: (String) -> Unit = {},
            val onDone: (String) -> Unit = {},
        ): Type

        data class WithIcon(
            val icon: IconComposer,
            val value: String = "",
            val hint: String = "",
            val keyboardType: KeyboardType = KeyboardType.Text,
            val onTextChanged: (String) -> Unit = {},
            val onDone: (String) -> Unit = {},
        ) : Type
    }
}

@Composable
fun InputField(
    type: InputField.Type,
    modifier: Modifier = Modifier,
    colors: InputColors = InputDefaults.colors(),
    enabled: Boolean = true,
) {
    when (type) {
        is InputField.Type.Text -> {
            InputFieldText(
                modifier = modifier,
                type = type,
                colors = colors,
                enabled = enabled,
            )
        }

        is InputField.Type.WithLabel -> {
            InputFieldWithLabel(
                modifier = modifier,
                type = type,
                colors = colors,
                enabled = enabled,
            )
        }

        is InputField.Type.WithIcon -> {
            InputFieldWithIcon(
                modifier = modifier,
                type = type,
                colors = colors,
                enabled = enabled,
            )
        }
    }
}

@Composable
private fun InputFieldText(
    type: InputField.Type.Text,
    modifier: Modifier = Modifier,
    colors: InputColors,
    enabled: Boolean,
) {
    val backgroundColor = if (enabled) colors.container else colors.disabledContainer

    InputText(
        modifier = containerModifier(backgroundColor = backgroundColor).then(modifier),
        value = type.value,
        hint = type.hint,
        enabled = enabled,
        colors = colors,
        keyboardType = type.keyboardType,
        onTextChanged = type.onTextChanged,
        onDone = type.onDone,
    )
}

@Composable
private fun InputFieldWithLabel(
    type: InputField.Type.WithLabel,
    modifier: Modifier = Modifier,
    colors: InputColors,
    enabled: Boolean,
) {
    val backgroundColor = if (enabled) colors.container else colors.disabledContainer

    Row(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
       Text(
           modifier = Modifier.weight(type.labelWeight),
           text = type.label,
           style = AppTheme.typography.title,
           color = AppTheme.color.onPrimaryContainer,
       )

        InputText(
            modifier = containerModifier(backgroundColor = backgroundColor).weight(1f - type.labelWeight),
            value = type.value,
            hint = type.hint,
            enabled = enabled,
            colors = colors,
            keyboardType = type.keyboardType,
            onTextChanged = type.onTextChanged,
            onDone = type.onDone,
        )
    }
}

@Composable
private fun InputFieldWithIcon(
    type: InputField.Type.WithIcon,
    modifier: Modifier = Modifier,
    colors: InputColors,
    enabled: Boolean,
) {
    val backgroundColor = if (enabled) colors.container else colors.disabledContainer

    Row(
        modifier = containerModifier(backgroundColor = backgroundColor).then(modifier),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        type.icon.Compose(Modifier.size(24.dp))

        InputText(
            value = type.value,
            hint = type.hint,
            enabled = enabled,
            colors = colors,
            keyboardType = type.keyboardType,
            onTextChanged = type.onTextChanged,
            onDone = type.onDone,
        )
    }
}

@Composable
private fun containerModifier(backgroundColor: Color) =
    Modifier
        .shadow(elevation = 3.dp, shape = AppTheme.shape.full)
        .background(color = backgroundColor)
        .padding(all = 16.dp)

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(AppTheme.color.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputField(
                type = InputField.Type.Text(
                    value = "Username",
                )
            )

            InputField(
                type = InputField.Type.WithLabel(
                    label = "Username",
                    value = "Username",
                )
            )

            InputField(
                type = InputField.Type.WithIcon(
                    icon = IconComposer.Icon(Icons.Rounded.CalendarMonth),
                    value = "Username",
                )
            )
        }
    }
}