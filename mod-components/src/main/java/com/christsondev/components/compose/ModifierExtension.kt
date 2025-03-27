package com.christsondev.components.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.clickableRipple(
    bounded: Boolean = false,
    onClick: () -> Unit,
) = this.clickable(
    onClick = onClick,
    interactionSource = remember { MutableInteractionSource() },
    indication = ripple(
        bounded = bounded,
    ),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.combinedClickableRipple(
    onClick: () -> Unit,
    onLongPress: () -> Unit,
) = this.combinedClickable(
    interactionSource = remember { MutableInteractionSource() },
    indication = ripple(),
    onClick = onClick,
    onLongClick = onLongPress,
)