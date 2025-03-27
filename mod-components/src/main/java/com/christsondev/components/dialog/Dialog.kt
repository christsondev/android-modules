package com.christsondev.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.christsondev.components.button.Button
import com.christsondev.components.button.ButtonContainer
import com.christsondev.components.button.ButtonDefaults
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

data class DialogButton(
    val label: String,
    val onClick: () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    dismissible: Boolean = true,
    onDismissRequest: () -> Unit,
    negativeButton: (DialogButton)? = null,
    positiveButton: (DialogButton)? = null,
) {
    BasicAlertDialog(
        modifier = Modifier.then(modifier),
        properties = DialogProperties(dismissOnClickOutside = dismissible, dismissOnBackPress = dismissible),
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = Modifier
                .clip(AppTheme.shape.large)
                .background(AppTheme.color.surfaceContainer)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = AppTheme.typography.h3Bold,
                color = AppTheme.color.onSurface,
                textAlign = TextAlign.Center,
            )

            description?.let {
                Text(
                    modifier = Modifier.padding(horizontal = 6.dp),
                    text = description,
                    style = AppTheme.typography.description,
                    color = AppTheme.color.onSurfaceVariant,
                    textAlign = TextAlign.Start,
                )
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.End),
            ) {
                negativeButton?.let {
                    Button(
                        type = Button.Type.Text(negativeButton.label),
                        colors = ButtonDefaults.error(),
                        container = ButtonContainer.NONE,
                        onClick = negativeButton.onClick,
                    )
                }

                positiveButton?.let {
                    Button(
                        type = Button.Type.Text(positiveButton.label),
                        onClick = positiveButton.onClick,
                    )
                }
            }
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Dialog(
            title = "Sample Title",
            description = "Sample Long Description",
            onDismissRequest = { },
            negativeButton = DialogButton(
                label = "Cancel",
                onClick = { },
            ),
            positiveButton = DialogButton(
                label = "Done",
                onClick = { },
            )
        )
    }
}