package com.christsondev.components.bottombar

import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

@Composable
fun DotBottomBar(
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
        val itemWidth = maxWidth / items.size
        val positionOffset = itemWidth * animationValue.value + (itemWidth / 2)
        val dotSize = 8.dp
        val halfDotSize = dotSize / 2
        val shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)

        Box(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .curvedShadow(shape = shape, elevation = 8.dp)
                    .background(containerColor, shape = shape)
                    .clip(shape),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                items.forEachIndexed { index, item ->
                    val fontColor =
                        if (selectedIndex == index) colors.selected else colors.unselected
                    Item(
                        modifier = Modifier
                            .clickableRipple {
                                selectedIndex = index
                                onSelectedIndex.invoke(index)
                            },
                        icon = item.icon,
                        label = item.label,
                        fontColor = fontColor,
                        selected = selectedIndex == index,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (positionOffset - halfDotSize).toPx().toInt(),
                            y = 52.dp.toPx().toInt(),
                        )
                    }
                    .background(color = colors.selected, shape = AppTheme.shape.full)
                    .size(dotSize),
            )
        }
    }
}

@Composable
private fun RowScope.Item(
    icon: IconComposer,
    label: String,
    fontColor: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val textAlpha by animateFloatAsState(
        if (selected) 0f else 1f,
        animationSpec = tween(400),
        label = "textAlpha",
    )

    Column(
        modifier = modifier
            .weight(1f)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        icon.copyComposer(fontColor).Compose(Modifier.size(24.dp))

        Text(
            modifier = Modifier.alpha(textAlpha),
            text = label,
            style = AppTheme.typography.smallTitle,
            color = fontColor,
        )
    }
}

private fun Modifier.curvedShadow(
    shape: Shape,
    elevation: Dp,
    shadowColor: Color = Color.Black.copy(alpha = 0.1f),
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
            modifier = Modifier.background(AppTheme.color.surfaceContainer),
        ) {
            DotBottomBar(
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
                    ),
                ),
                initialValue = 1,
                onSelectedIndex = { },
            )
        }
    }
}