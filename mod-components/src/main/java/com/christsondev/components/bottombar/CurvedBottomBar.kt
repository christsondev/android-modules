package com.christsondev.components.bottombar

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

@Composable
fun CurvedBottomBar(
    items: List<BottomBarItem>,
    modifier: Modifier = Modifier,
    initialValue: Int = 0,
    colors: BottomBarColors = BottomBarDefaults.colors(),
    containerColor: Color = AppTheme.color.surfaceContainer,
    onSelectedIndex: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(initialValue) }
    val animationValue = remember { Animatable(initialValue.toFloat()) }

    LaunchedEffect(selectedIndex) {
        animationValue.animateTo(targetValue = selectedIndex.toFloat())
    }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val density = LocalDensity.current
        val itemWidth = maxWidth / items.size
        val positionOffset = itemWidth * animationValue.value + (itemWidth / 2)
        val halfButtonSize = 48.dp / 2

        val shape = remember(positionOffset) {
            CurvedShape(
                positionOffset = with(density) { positionOffset.toPx() },
                circleRadius = with(density) { 36.dp.toPx() },
            )
        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .curvedShadow(
                        shape = shape,
                        elevation = 16.dp,
                    )
                    .background(containerColor, shape = shape)
                    .clip(shape),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, item ->
                    val fontColor = if (selectedIndex == index) colors.selected else colors.unselected
                    Item(
                        modifier = Modifier
                            .clickableRipple {
                                selectedIndex = index
                                onSelectedIndex.invoke(index)
                            },
                        icon = item.icon,
                        label = item.label,
                        fontColor = fontColor,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (positionOffset - halfButtonSize).toPx().toInt(),
                            y = 0,
                        )
                    }
                    .shadow(elevation = 8.dp, shape = AppTheme.shape.full)
                    .background(color = containerColor),
            ) {
                items[selectedIndex].icon
                    .copyComposer(tint = colors.selected)
                    .Compose(
                        Modifier
                            .padding(12.dp)
                            .size(halfButtonSize),
                    )
            }
        }
    }
}

@Composable
private fun RowScope.Item(
    icon: IconComposer,
    label: String,
    fontColor: Color,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .weight(1f)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon.copyComposer(fontColor).Compose(Modifier.size(24.dp))

        Text(
            text = label,
            style = AppTheme.typography.smallTitle,
            color = fontColor,
        )
    }
}

private fun Modifier.curvedShadow(
    shape: Shape,
    elevation: Dp,
    shadowColor: Color = Color.Black.copy(alpha = 0.2f),
): Modifier = drawBehind {
    val blurRadius = elevation.toPx()
    drawIntoCanvas { canvas ->
        val paint = androidx.compose.ui.graphics.Paint()
            .asFrameworkPaint()
            .apply {
                color = shadowColor.toArgb()
                maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)
            }
        val outline = shape.createOutline(size, layoutDirection, this)
        if (outline is Outline.Generic) {
            canvas.nativeCanvas.drawPath(outline.path.asAndroidPath(), paint)
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Box(
            modifier = Modifier.background(AppTheme.color.surfaceContainer)
        ) {
            CurvedBottomBar(
                items = listOf(
                    BottomBarItem(
                        icon = IconComposer.Icon(Icons.Rounded.Dashboard),
                        label = "Dashboard",
                    ),
                    BottomBarItem(
                        icon = IconComposer.Icon(Icons.Rounded.Home),
                        label = "Home",
                    ),
                    BottomBarItem(
                        icon = IconComposer.Icon(Icons.Rounded.Settings),
                        label = "Settings",
                    )
                ),
                initialValue = 1,
                onSelectedIndex = { },
            )
        }
    }
}