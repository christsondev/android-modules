package com.christsondev.components.inputfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
        ) : Type

        data class Label(
            val label: String,
            val value: String = "",
            val hint: String = "",
            val labelWeight: Float = 0.3f,
            val keyboardType: KeyboardType = KeyboardType.Text,
            val onTextChanged: (String) -> Unit = {},
            val onDone: (String) -> Unit = {},
        ) : Type

        data class Icon(
            val icon: IconComposer,
            val value: String = "",
            val hint: String = "",
            val keyboardType: KeyboardType = KeyboardType.Text,
            val onTextChanged: (String) -> Unit = {},
            val onDone: (String) -> Unit = {},
        ) : Type

        data class LeadingContent(
            val content: @Composable () -> Unit,
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
    configs: InputConfigs = InputDefaults.configs(),
    contentPadding: PaddingValues = PaddingValues(AppTheme.padding.container.default),
    enabled: Boolean = true,
) {
    when (type) {
        is InputField.Type.Text -> {
            InputFieldText(
                modifier = modifier,
                type = type,
                colors = colors,
                configs = configs,
                contentPadding = contentPadding,
                enabled = enabled,
            )
        }

        is InputField.Type.Label -> {
            InputFieldWithLabel(
                modifier = modifier,
                type = type,
                colors = colors,
                configs = configs,
                contentPadding = contentPadding,
                enabled = enabled,
            )
        }

        is InputField.Type.Icon -> {
            InputFieldWithIcon(
                modifier = modifier,
                type = type,
                colors = colors,
                configs = configs,
                contentPadding = contentPadding,
                enabled = enabled,
            )
        }

        is InputField.Type.LeadingContent -> {
            InputFieldWithLeadingContent(
                modifier = modifier,
                type = type,
                colors = colors,
                configs = configs,
                contentPadding = contentPadding,
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
    configs: InputConfigs,
    contentPadding: PaddingValues,
    enabled: Boolean,
) {
    InputText(
        modifier = modifier.padding(contentPadding),
        value = type.value,
        hint = type.hint,
        enabled = enabled,
        colors = colors,
        configs = configs,
        keyboardType = type.keyboardType,
        onTextChanged = type.onTextChanged,
        onDone = type.onDone,
    )
}

@Composable
private fun InputFieldWithLabel(
    type: InputField.Type.Label,
    modifier: Modifier = Modifier,
    colors: InputColors,
    configs: InputConfigs,
    contentPadding: PaddingValues,
    enabled: Boolean,
) {
    Row(
        modifier = modifier.padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.padding.container.default),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(type.labelWeight),
            text = type.label,
            style = AppTheme.typography.title,
            color = AppTheme.color.onPrimaryContainer,
        )

        InputText(
            modifier = Modifier.weight(1f - type.labelWeight),
            value = type.value,
            hint = type.hint,
            enabled = enabled,
            colors = colors,
            configs = configs,
            keyboardType = type.keyboardType,
            onTextChanged = type.onTextChanged,
            onDone = type.onDone,
        )
    }
}

@Composable
private fun InputFieldWithIcon(
    type: InputField.Type.Icon,
    modifier: Modifier = Modifier,
    colors: InputColors,
    configs: InputConfigs,
    contentPadding: PaddingValues,
    enabled: Boolean,
) {
    Row(
        modifier = modifier.padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.padding.container.betweenItems),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        type.icon.Compose(Modifier.size(AppTheme.size.icon))

        InputText(
            value = type.value,
            hint = type.hint,
            enabled = enabled,
            colors = colors,
            configs = configs,
            keyboardType = type.keyboardType,
            onTextChanged = type.onTextChanged,
            onDone = type.onDone,
        )
    }
}

@Composable
private fun InputFieldWithLeadingContent(
    type: InputField.Type.LeadingContent,
    modifier: Modifier = Modifier,
    colors: InputColors,
    configs: InputConfigs,
    contentPadding: PaddingValues,
    enabled: Boolean,
) {
    Row(
        modifier = modifier.padding(contentPadding),
        horizontalArrangement = Arrangement.spacedBy(AppTheme.padding.container.betweenItems),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        type.content.invoke()

        InputText(
            value = type.value,
            hint = type.hint,
            enabled = enabled,
            colors = colors,
            configs = configs,
            keyboardType = type.keyboardType,
            onTextChanged = type.onTextChanged,
            onDone = type.onDone,
        )
    }
}

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
                ),
            )

            InputField(
                modifier = Modifier
                    .shadow(1.dp, AppTheme.shape.medium),
                type = InputField.Type.Label(
                    label = "Username",
                    value = "Username",
                ),
                contentPadding = PaddingValues(16.dp),
            )

            InputField(
                type = InputField.Type.Icon(
                    icon = IconComposer.Icon(Icons.Rounded.CalendarMonth),
                    value = "Username",
                ),
                configs = InputDefaults.configs(
                    singleLine = false,
                ),
            )
        }
    }
}