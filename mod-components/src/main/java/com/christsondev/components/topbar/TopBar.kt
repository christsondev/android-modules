package com.christsondev.components.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

@Composable
fun TopBar(
    title: String? = null,
    onBack: (() -> Unit)? = null,
    onContent: @Composable (() -> Unit)? = null,
) {
    Box(modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp)) {
        onBack?.let {
            IconButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack,
            ) {
                IconComposer.Back().Compose(Modifier.size(24.dp))
            }
        }

        title?.let {
            val textAlign = when {
                onBack == null -> TextAlign.Start
                else -> TextAlign.Center
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .align(Alignment.Center),
                text = title,
                textAlign = textAlign,
                style = AppTheme.typography.h1,
                color = AppTheme.color.onPrimaryContainer,
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            onContent?.invoke()
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        TopBar(
            title = "Hello",
            onBack = {},
        )
    }
}