package com.christsondev.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.NoteAdd
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

object Button {
    sealed class Type {
        data class Text(
            val title: String,
        ) : Type()

        data class IconWithText(
            val icon: IconComposer,
            val title: String,
        ) : Type()

        data class TextWithLoading(
            val loading: Boolean,
            val title: String,
        ) : Type()
    }
}

@Composable
fun Button(
    type: Button.Type,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.colors(),
    container: ButtonContainer = ButtonContainer.FILL,
    contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    onClick: () -> Unit,
) {
    val fontColor = if (enabled) colors.font else colors.disabledFont
    val containerColor = if (enabled) colors.container else colors.disabledContainer
    val elevation = if (container == ButtonContainer.FILL) 3.dp else 0.dp
    val clickModifier = if (enabled) modifier.clickableRipple { onClick.invoke() } else Modifier
    val backgroundModifier = container.getModifier(containerColor)

    Row(
        modifier = Modifier
            .shadow(
                elevation = elevation,
                shape = AppTheme.shape.full,
            )
            .clip(shape = AppTheme.shape.full)
            .then(modifier)
            .then(backgroundModifier)
            .then(clickModifier)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        when (type) {
            is Button.Type.Text -> {
                Text(title = type.title, fontColor = fontColor)
            }

            is Button.Type.IconWithText -> {
                IconWithText(
                    iconComposer = type.icon,
                    title = type.title,
                    fontColor = fontColor,
                )
            }

            is Button.Type.TextWithLoading -> {
                TextWithLoading(
                    loading = type.loading,
                    title = type.title,
                    fontColor = fontColor,
                    trackColor = fontColor.copy(alpha = 0.33f),
                )
            }
        }
    }
}

@Composable
private fun Text(
    title: String,
    fontColor: Color,
) {
    androidx.compose.material3.Text(
        modifier = Modifier.padding(8.dp),
        text = title,
        style = AppTheme.typography.body,
        color = fontColor,
    )
}

@Composable
private fun IconWithText(
    iconComposer: IconComposer,
    title: String,
    fontColor: Color,
) {
    iconComposer.copyComposer(fontColor).Compose(Modifier.size(24.dp))

    Text(title, fontColor)
}

@Composable
private fun TextWithLoading(
    loading: Boolean,
    title: String,
    fontColor: Color,
    trackColor: Color,
) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = fontColor,
            trackColor = trackColor,
        )
    }

    Text(title, fontColor)
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.Text("Text Button"),
            ) { }

            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.IconWithText(
                    icon = IconComposer.Icon(Icons.AutoMirrored.Rounded.NoteAdd),
                    title = "Icon Button",
                ),
            ) { }

            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.TextWithLoading(
                    loading = true,
                    title = "Loading Button",
                ),
            ) { }

            HorizontalDivider()

            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.Text("Text Button"),
                colors = ButtonDefaults.default(),
                container = ButtonContainer.NONE,
            ) { }

            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.IconWithText(
                    icon = IconComposer.Icon(Icons.AutoMirrored.Rounded.NoteAdd),
                    title = "Icon Button",
                ),
                container = ButtonContainer.FILL,
            ) { }

            Button(
                modifier = Modifier.fillMaxWidth(),
                type = Button.Type.TextWithLoading(
                    loading = true,
                    title = "Loading Button",
                ),
                colors = ButtonDefaults.default(),
                container = ButtonContainer.OUTLINE,
            ) { }
        }
    }
}