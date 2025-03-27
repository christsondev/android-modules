package com.christsondev.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.christsondev.components.IconComposer
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

fun LazyListScope.listGroup(
    content: ListGroupScope.() -> Unit,
) {
    this.item {
        val latestContent = rememberUpdatedState(content)
        val listGroupScope = DefaultListGroupScope().apply(latestContent.value)

        listGroupScope.items.forEachIndexed { index, listItem ->
            listItem.content()

            if (index < listGroupScope.items.size - 1) {
                HorizontalDivider(color = AppTheme.color.outlineVariant)
            }
        }
    }
}

interface ListGroupScope {
    fun listItem(
        type: ListItem.Type,
        title: String,
        description: String? = null,
        label: String? = null,
        leadingIcon: IconComposer? = null,
        contentPadding: PaddingValues = PaddingValues(),
        colors: ListItemColors? = null,
    )
}

private class DefaultListGroupScope : ListGroupScope {
    private val _items = mutableListOf<ComposableListItem>()
    val items: List<ComposableListItem> = _items

    override fun listItem(
        type: ListItem.Type,
        title: String,
        description: String?,
        label: String?,
        leadingIcon: IconComposer?,
        contentPadding: PaddingValues,
        colors: ListItemColors?,
    ) {
        _items.add(
            ComposableListItem {
                ListItem(
                    type = type,
                    title = title,
                    description = description,
                    label = label,
                    leadingIcon = leadingIcon,
                    contentPadding = contentPadding,
                    colors = colors ?: ListItemDefaults.colors(),
                )
            }
        )
    }
}

private data class ComposableListItem(val content: @Composable () -> Unit)

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        LazyColumn {
            listGroup {
                listItem(
                    type = ListItem.Type.Default(),
                    title = "Default",
                    description = "Description Description Description Description Description Description Description Description",
                    label = "Label",
                    leadingIcon = IconComposer.Warning(),
                )

                listItem(
                    type = ListItem.Type.Chevron(
                        onClick = { }
                    ),
                    title = "Chevron without Icon and With Label",
                    description = "Description",
                    label = "Label",
                )

                listItem(
                    type = ListItem.Type.Chevron(
                        onClick = { },
                    ),
                    title = "Chevron without Label",
                    description = "Description",
                    leadingIcon = IconComposer.Warning(),
                )

                listItem(
                    type = ListItem.Type.Expandable(
                        expanded = true,
                        content = {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "Expanded Item 1",
                            )
                        },
                    ),
                    title = "Expandable Item",
                    description = "Description",
                    label = "Label",
                    leadingIcon = IconComposer.Warning(),
                )
            }
        }
    }
}