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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme
import com.christsondev.utilities.orElse

data object Tile {
    sealed interface Type {
        data object Box : Type

        data object ConstraintLayout : Type

        data class Column(
            val arrangement: Arrangement.Vertical,
            val alignment: Alignment.Horizontal,
        ) : Type

        data class Row(
            val arrangement: Arrangement.Horizontal,
            val alignment: Alignment.Vertical,
        ) : Type
    }

    sealed interface Container {
        sealed interface Type {
            data object Flat : Type
            data class Elevated(
                val elevation: Dp = TileContainerDefaults.elevation,
            ) : Type
        }

        sealed interface Effect {
            data object None : Effect

            @Suppress("ArrayInDataClass")
            data class Dash(
                val width: Dp = TileContainerDefaults.dashWidth,
                val intervals: FloatArray = TileContainerDefaults.dashIntervals,
                val color: Color? = null,
            ) : Effect
        }
    }
}

private val elevated = Tile.Container.Type.Elevated()
private val none = Tile.Container.Effect.None

@Composable
fun Tile(
    type: Tile.Type.Box,
    modifier: Modifier = Modifier,
    containerColor: Color = TileContainerDefaults.containerColor,
    containerType: Tile.Container.Type = elevated,
    containerEffect: Tile.Container.Effect = none,
    cornerRadius: Shape = TileContainerDefaults.cornerRadius,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.tileStyle(
            containerColor = containerColor,
            containerType = containerType,
            containerEffect = containerEffect,
            cornerRadius = cornerRadius,
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
    containerColor: Color = TileContainerDefaults.containerColor,
    containerType: Tile.Container.Type = elevated,
    containerEffect: Tile.Container.Effect = none,
    cornerRadius: Shape = TileContainerDefaults.cornerRadius,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier.tileStyle(
            containerColor = containerColor,
            containerType = containerType,
            containerEffect = containerEffect,
            cornerRadius = cornerRadius,
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
    containerColor: Color = TileContainerDefaults.containerColor,
    containerType: Tile.Container.Type = elevated,
    containerEffect: Tile.Container.Effect = none,
    cornerRadius: Shape = TileContainerDefaults.cornerRadius,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier.tileStyle(
            containerColor = containerColor,
            containerType = containerType,
            containerEffect = containerEffect,
            cornerRadius = cornerRadius,
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
    containerColor: Color = TileContainerDefaults.containerColor,
    containerType: Tile.Container.Type = elevated,
    containerEffect: Tile.Container.Effect = none,
    cornerRadius: Shape = TileContainerDefaults.cornerRadius,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    content: @Composable ConstraintLayoutScope.() -> Unit,
) {
    ConstraintLayout(
        modifier = modifier.tileStyle(
            containerColor = containerColor,
            containerType = containerType,
            containerEffect = containerEffect,
            cornerRadius = cornerRadius,
            contentPadding = contentPadding,
            onClick = onClick,
        ),
        content = content,
    )
}

@Composable
private fun Modifier.tileStyle(
    containerColor: Color,
    containerType: Tile.Container.Type,
    containerEffect: Tile.Container.Effect,
    cornerRadius: Shape,
    contentPadding: PaddingValues,
    onClick: (() -> Unit)? = null,
): Modifier {
    val defaultDashColor = TileContainerDefaults.dashColor
    return this
        .then(
            when (containerType) {
                is Tile.Container.Type.Flat -> Modifier.background(containerColor, cornerRadius)
                is Tile.Container.Type.Elevated -> Modifier
                    .shadow(containerType.elevation, cornerRadius, clip = true)
                    .background(containerColor)
            }
        )
        .drawBehind {
            if (containerEffect is Tile.Container.Effect.Dash) {
                val dashColor = containerEffect.color.orElse { defaultDashColor }
                // This draws the dashed border following the exact outline of the shape
                drawOutline(
                    outline = cornerRadius.createOutline(size, layoutDirection, this),
                    color = dashColor,
                    style = Stroke(
                        width = containerEffect.width.toPx(),
                        pathEffect = PathEffect.dashPathEffect(containerEffect.intervals, 0f),
                    )
                )
            }
        }
        .then(onClick?.let { Modifier.clickableRipple(onClick = it) }.orElse { Modifier })
        .padding(contentPadding)
}


@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(
            modifier = Modifier
                .background(Color.Red)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Tile(
                modifier = Modifier.padding(8.dp),
                type = Tile.Type.Box,
                contentPadding = PaddingValues(16.dp),
                containerType = elevated,
                content = {
                    Text("Elevated")
                },
            )

            Tile(
                modifier = Modifier.padding(8.dp),
                type = Tile.Type.Box,
                contentPadding = PaddingValues(16.dp),
                containerType = Tile.Container.Type.Flat,
                cornerRadius = CircleShape,
                content = {
                    Text("Flat")
                },
            )

            Tile(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                type = Tile.Type.ConstraintLayout,
                containerColor = Color.Green,
                contentPadding = PaddingValues(16.dp),
                containerType = Tile.Container.Type.Elevated(12.dp),
                containerEffect = Tile.Container.Effect.Dash(width = 4.dp, color = Color.Blue),
                cornerRadius = RoundedCornerShape(24.dp),
                content = {
                    val (text1, text2) = createRefs()

                    Text(
                        modifier = Modifier.constrainAs(text1) {
                            // Constrain relative to the button
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        text = "Text 1",
                    )

                    Text(
                        modifier = Modifier.constrainAs(text2) {
                            // Constrain relative to the button
                            top.linkTo(text1.bottom, 12.dp)
                            bottom.linkTo(parent.bottom)
                        },
                        text = "Text 2",
                    )
                },
            )
        }
    }
}