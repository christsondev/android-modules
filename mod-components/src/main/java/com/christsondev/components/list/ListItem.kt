package com.christsondev.components.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.compose.clickableRipple
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

@Composable
fun ListItem(
    type: ListItem.Type,
    title: String,
    description: String? = null,
    label: String? = null,
    leadingIcon: IconComposer? = null,
    contentPadding: PaddingValues = PaddingValues(),
    colors: ListItemColors = ListItemDefaults.colors(),
) {
    when (type) {
        is ListItem.Type.Default -> {
            val modifier = type.onClick?.let {
                Modifier.clickableRipple(bounded = true) { type.onClick.invoke() }
            }

            ListItemContent(
                modifier = modifier ?: Modifier,
                colors = colors,
                title = title,
                description = description,
                label = label,
                leadingIcon = leadingIcon,
                trailingContent = {},
                contentPadding = contentPadding,
            )
        }

        is ListItem.Type.Chevron -> {
            ListItemContent(
                modifier = Modifier.clickableRipple(bounded = true) { type.onClick.invoke() },
                colors = colors,
                title = title,
                description = description,
                label = label,
                leadingIcon = leadingIcon,
                trailingContent = { Chevron() },
                contentPadding = contentPadding,
            )
        }

        is ListItem.Type.Expandable -> {
            var expanded by remember(type.expanded) { mutableStateOf(type.expanded) }

            Column(modifier = Modifier.background(color = AppTheme.color.background)) {
                ListItemContent(
                    modifier = Modifier.clickableRipple(bounded = true) { expanded = !expanded },
                    colors = colors,
                    title = title,
                    description = description,
                    label = label,
                    leadingIcon = leadingIcon,
                    trailingContent = {
                       Expandable(expanded, colors)
                    },
                    contentPadding = contentPadding,
                )

                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    type.content.invoke()
                }
            }
        }
    }
}

@Composable
private fun ListItemContent(
    modifier: Modifier = Modifier,
    title: String,
    colors: ListItemColors,
    description: String? = null,
    label: String? = null,
    leadingIcon: IconComposer? = null,
    contentPadding: PaddingValues = PaddingValues(),
    trailingContent: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = colors.container)
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.Compose(modifier = Modifier.size(48.dp).padding(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = title,
                style = AppTheme.typography.title,
                color = colors.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            description?.let {
                Text(
                    text = it,
                    style = AppTheme.typography.description,
                    color = colors.text,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        label?.let {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = it,
                style = AppTheme.typography.body,
                color = colors.text,
            )
        }

        trailingContent.invoke()
    }
}

@Composable
private fun Chevron() {
    IconComposer.ChevronRight().Compose(modifier = Modifier.size(36.dp).padding(12.dp))
}

@Composable
private fun Expandable(
    expanded: Boolean,
    colors: ListItemColors,
) {
    val arrowIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    Icon(
        imageVector = arrowIcon,
        tint = colors.primary,
        contentDescription = "",
    )
}

@AppMultiPreview
@Composable
private fun Preview_Default() {
    AppTheme {
        ListItem(
            type = ListItem.Type.Default(
                onClick = { },
            ),
            title = "Title",
            description = "Description",
            label = "Label",
            leadingIcon = IconComposer.Warning(),
        )
    }
}

@AppMultiPreview
@Composable
private fun Preview_Chevron() {
    AppTheme {
        ListItem(
            type = ListItem.Type.Chevron(
                onClick = { },
            ),
            title = "Title",
            description = "Description",
            label = "Label",
            leadingIcon = IconComposer.Warning(),
        )
    }
}

@AppMultiPreview
@Composable
private fun Preview_Expandable() {
    AppTheme {
        ListItem(
            type = ListItem.Type.Expandable(
                expanded = true,
                content = {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text("Item 1")
                        Text("Item 2")
                        Text("Item 3")
                    }
                },
            ),
            title = "Title",
            description = "Description",
            label = "Label",
            leadingIcon = IconComposer.Warning(),
        )
    }
}