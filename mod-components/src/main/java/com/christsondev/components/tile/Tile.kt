package com.christsondev.components.tile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

data object Tile {
    sealed interface Type {
        data object Box: Type

        data class Column(
            val arrangement: Arrangement.Vertical,
            val alignment: Alignment.Horizontal,
        ): Type

        data class Row(
            val arrangement: Arrangement.Horizontal,
            val alignment: Alignment.Vertical,
        ): Type
    }
}

@Composable
fun Tile(
    type: Tile.Type.Box,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.color.background,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.appendModifier(
            backgroundColor = backgroundColor,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        content = content,
    )
}

@Composable
fun Tile(
    type: Tile.Type.Column,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.color.background,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.appendModifier(
            backgroundColor = backgroundColor,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        verticalArrangement = type.arrangement,
        horizontalAlignment = type.alignment,
        content = content,
    )
}

@Composable
fun Tile(
    type: Tile.Type.Row,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.color.background,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.appendModifier(
            backgroundColor = backgroundColor,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        horizontalArrangement = type.arrangement,
        verticalAlignment = type.alignment,
        content = content,
    )
}

@Composable
private fun Modifier.appendModifier(
    backgroundColor: Color,
    contentPadding: PaddingValues,
    onClick: (() -> Unit)?,
) = this
    .shadow(elevation = 3.dp, shape = AppTheme.shape.medium)
    .background(color = backgroundColor)
    .apply {
        onClick?.let { clickable(onClick = onClick) }
    }
    .padding(contentPadding)

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Tile(
            type = Tile.Type.Box,
            contentPadding = PaddingValues(16.dp),
            content = {
                Text("Testing")
            },
        )
    }
}