package com.christsondev.components.bottombar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
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
    containerColor: Color = AppTheme.color.background,
    onSelectedIndex: (Int) -> Unit,
) {
    var selectedIndex by remember { mutableIntStateOf(initialValue) }
    val animationValue = remember { Animatable(0f) }

    val screenWidth = ScreenWidth().dp
    val itemWidth = screenWidth.div(items.size)
    val buttonSize = 8.dp
    val halfButtonSize = buttonSize.div(2)

    LaunchedEffect(selectedIndex) {
        animationValue.animateTo(targetValue = selectedIndex.toFloat())
    }

    val positionOffset = itemWidth.times(animationValue.value).plus(itemWidth.div(2))

    Box(
        modifier = modifier
            .shadow(3.dp, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(color = containerColor),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                    selected = selectedIndex == index,
                )
            }
        }

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(
                        x = positionOffset.minus(halfButtonSize).toPx().toInt(),
                        y = 52.dp.toPx().toInt(),
                    )
                }
                .background(color = colors.selected, shape = AppTheme.shape.full)
                .size(buttonSize),
        )
    }
}

@Composable
private fun RowScope.Item(
    icon: ImageVector,
    label: String,
    fontColor: Color,
    selected: Boolean,
    modifier: Modifier = Modifier,
) {
    val textAlpha by animateFloatAsState(if (selected) 0f else 1f, animationSpec = tween(400))

    Column(
        modifier = modifier
            .weight(1f)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconComposer.Icon(icon, tint = fontColor).Compose(Modifier.size(24.dp))

        Text(
            modifier = Modifier.alpha(textAlpha),
            text = label,
            style = AppTheme.typography.smallTitle,
            color = fontColor,
        )
    }
}

@Composable
private fun ScreenWidth(): Int {
    return LocalConfiguration.current.screenWidthDp
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        DotBottomBar(
            items = listOf(
                BottomBarItem(
                    icon = Icons.Rounded.Dashboard,
                    label = "Dashboard",
                ),
                BottomBarItem(
                    icon = Icons.Rounded.Home,
                    label = "Home",
                ),
                BottomBarItem(
                    icon = Icons.Rounded.Settings,
                    label = "Settings",
                )
            ),
            onSelectedIndex = { },
        )
    }
}