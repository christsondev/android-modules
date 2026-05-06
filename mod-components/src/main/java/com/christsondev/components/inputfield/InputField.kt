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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

object InputField {
    sealed interface Type {
        data object Text : Type

        data class Label(
            val label: String,
            val labelStyle: TextStyle,
            val labelWeight: Float = 0.3f,
        ) : Type

        data class Icon(
            val icon: IconComposer,
        ) : Type

        data class LeadingContent(
            val content: @Composable () -> Unit,
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
    value: String = "",
    hint: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    onTextChanged: (String) -> Unit = {},
    onDone: (String) -> Unit = {},
) {
    Row(
        modifier = modifier.padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            when (type) {
                is InputField.Type.Label -> AppTheme.padding.container.default
                else -> AppTheme.padding.container.betweenItems
            }
        )
    ) {
        val inputContent = @Composable { inputModifier: Modifier ->
            InputText(
                modifier = inputModifier,
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

        when (type) {
            is InputField.Type.Text -> {
                inputContent(Modifier)
            }

            is InputField.Type.Label -> {
                Text(
                    modifier = Modifier.weight(type.labelWeight),
                    text = type.label,
                    style = type.labelStyle,
                    color = AppTheme.color.onPrimaryContainer,
                )

                inputContent(Modifier.weight(1f - type.labelWeight))
            }

            is InputField.Type.Icon -> {
                type.icon.Compose(Modifier.size(AppTheme.size.icon))
                inputContent(Modifier)
            }

            is InputField.Type.LeadingContent -> {
                type.content.invoke()
                inputContent(Modifier)
            }
        }
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
                type = InputField.Type.Text,
                value = "Username",
            )

            InputField(
                modifier = Modifier
                    .shadow(1.dp, AppTheme.shape.medium)
                    .background(AppTheme.color.surface, AppTheme.shape.medium),
                type = InputField.Type.Label(
                    label = "Username",
                    labelStyle = AppTheme.typography.title,
                ),
                value = "Username",
                contentPadding = PaddingValues(16.dp),
            )

            InputField(
                type = InputField.Type.Icon(
                    icon = IconComposer.Icon(Icons.Rounded.CalendarMonth),
                ),
                value = "Username",
                configs = InputDefaults.configs(
                    singleLine = false,
                ),
            )
        }
    }
}
