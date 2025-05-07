package com.christsondev.components.tile

import androidx.compose.foundation.background
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
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

data object Tile {
    sealed interface Type {
        data class Box(
            val content: @Composable BoxScope.() -> Unit,
        ): Type

        data class Column(
            val arrangement: Arrangement.Vertical,
            val alignment: Alignment.Horizontal,
            val content: @Composable ColumnScope.() -> Unit,
        ): Type

        data class Row(
            val arrangement: Arrangement.Horizontal,
            val alignment: Alignment.Vertical,
            val content: @Composable RowScope.() -> Unit,
        ): Type
    }
}

@Composable
fun Tile(
    type: Tile.Type,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppTheme.color.background,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    val tileModifier = modifier
        .shadow(elevation = 3.dp, shape = AppTheme.shape.medium)
        .background(color = backgroundColor)

    onClick?.let { tileModifier.clickableRipple(onClick = onClick) }

    tileModifier.padding(contentPadding)

    when (type) {
        is Tile.Type.Box -> {
            Box(
                modifier = tileModifier,
                content = type.content,
            )
        }

        is Tile.Type.Column -> {
            Column(
                modifier = tileModifier,
                verticalArrangement = type.arrangement,
                horizontalAlignment = type.alignment,
                content = type.content,
            )
        }

        is Tile.Type.Row -> {
            Row(
                modifier = tileModifier,
                horizontalArrangement = type.arrangement,
                verticalAlignment = type.alignment,
                content = type.content,
            )
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Tile(
            type = Tile.Type.Box(
                content = {
                    Text("Testing")
                }
            ),
        )
    }
}