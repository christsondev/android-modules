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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme
import com.christsondev.utilities.orElse

data object Tile {
    sealed interface Type {
        data object Box: Type

        data object ConstraintLayout: Type

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
    containerColor: Color = AppTheme.color.background,
    tileBorder: TileBorder = TileBorderDefaults.elevated(),
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.appendModifier(
            containerColor = containerColor,
            tileBorder = tileBorder,
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
    containerColor: Color = AppTheme.color.background,
    tileBorder: TileBorder = TileBorderDefaults.elevated(),
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.appendModifier(
            containerColor = containerColor,
            tileBorder = tileBorder,
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
    containerColor: Color = AppTheme.color.background,
    tileBorder: TileBorder = TileBorderDefaults.elevated(),
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.appendModifier(
            containerColor = containerColor,
            tileBorder = tileBorder,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        horizontalArrangement = type.arrangement,
        verticalAlignment = type.alignment,
        content = content,
    )
}

@Composable
fun Tile(
    type: Tile.Type.ConstraintLayout,
    modifier: Modifier = Modifier,
    containerColor: Color = AppTheme.color.background,
    tileBorder: TileBorder = TileBorderDefaults.elevated(),
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable ConstraintLayoutScope.() -> Unit,
) {
    ConstraintLayout(
        modifier = modifier.appendModifier(
            containerColor = containerColor,
            tileBorder = tileBorder,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        content = content,
    )
}

@Composable
private fun Modifier.appendModifier(
    containerColor: Color,
    contentPadding: PaddingValues,
    tileBorder: TileBorder,
    onClick: (() -> Unit)? = null,
) =
    this
        .shadow(elevation = tileBorder.elevation, shape = tileBorder.cornerRadius, clip = true)
        .background(color = containerColor)
        .then(onClick?.let { Modifier.clickableRipple(onClick = it) }.orElse { Modifier })
        .padding(contentPadding)


@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(Color.Red)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Tile(
                modifier = Modifier.padding(8.dp),
                type = Tile.Type.Box,
                contentPadding = PaddingValues(16.dp),
                tileBorder = TileBorderDefaults.elevated(),
                content = {
                    Text("Elevated")
                },
            )

            Tile(
                modifier = Modifier.padding(8.dp),
                type = Tile.Type.Box,
                contentPadding = PaddingValues(16.dp),
                tileBorder = TileBorderDefaults.flat(),
                content = {
                    Text("Flat")
                },
            )
        }
    }
}