package com.christsondev.components.toggle

import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

data class ToggleItem<T>(
    val label: String,
    val item: T,
)

@Composable
fun<T> Toggle(
    items: List<ToggleItem<T>>,
    index: Int? = null,
    colors: ToggleColors = ToggleDefaults.colors(),
    enabled: Boolean = true,
    onSelected: (T) -> Unit,
) {
    var selectedIndex by remember(index) { mutableStateOf(index) }
    var parentWidth by remember { mutableIntStateOf(0) }

    val backgroundOffset by animateIntOffsetAsState(
        label = "Toggle animate backgroundOffset as State",
        targetValue = IntOffset(
            x = selectedIndex?.let { (parentWidth / items.size * it) } ?: 0,
            y = 0,
        ),
    )

    val containerColor = if (enabled) colors.container else colors.disabledContainer

    ConstraintLayout (
        modifier = Modifier
            .shadow(elevation = 3.dp, shape = AppTheme.shape.large)
            .background(containerColor)
            .onGloballyPositioned {
                parentWidth = it.size.width
            },
    ) {
        val (containerRef, backgroundRef) = createRefs()

        selectedIndex?.let {
            Box(
                modifier = Modifier
                    .offset { backgroundOffset }
                    .constrainAs(backgroundRef) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.percent(1f / items.size)
                        height = Dimension.fillToConstraints
                    }
                    .background(
                        color = colors.selectedContainer,
                        shape = AppTheme.shape.large,
                    ),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(containerRef) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            items.forEachIndexed { index, item ->
                var fontColor = if (selectedIndex == index) colors.selectedText else colors.text

                val clickableModifier = if(enabled) {
                    Modifier.clickableRipple {
                        selectedIndex = index
                        onSelected.invoke(item.item)
                    }
                } else {
                    fontColor = colors.disabledText
                    Modifier
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .clip(AppTheme.shape.large)
                        .then(clickableModifier)
                        .padding(16.dp),
                    text = item.label,
                    style = AppTheme.typography.body,
                    color = fontColor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Toggle(
                items = listOf(
                    ToggleItem("Cash", "Cash"),
                    ToggleItem("Invoice", "Invoice"),
                ),
            ) { }

            Toggle(
                items = listOf(
                    ToggleItem("Cash", "Cash"),
                    ToggleItem("Invoice", "Invoice"),
                ),
                index = 0,
            ) { }
        }
    }
}