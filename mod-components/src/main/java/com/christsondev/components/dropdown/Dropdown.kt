package com.christsondev.components.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material.icons.rounded.ArrowDropUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.christsondev.components.theme.AppMultiPreview
import com.christsondev.components.theme.AppTheme

data class DropdownData<T>(
    val title: String,
    val data: T,
)

@Composable
fun<T> Dropdown(
    modifier: Modifier = Modifier,
    items: List<DropdownData<T>>,
    initialLabel: String,
    selectedValue: T? = null,
    colors: DropdownColors = DropdownDefaults.colors(),
    enabled: Boolean = true,
    onSelected: (DropdownData<T>) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember(selectedValue, items) {
        mutableIntStateOf(items.getIndexByItem(selectedValue, -1))
    }

    val cornerRadius = AppTheme.shape.large as RoundedCornerShape
    val arrowIcon = if (expanded) Icons.Rounded.ArrowDropUp else Icons.Rounded.ArrowDropDown

    Card(
        modifier = modifier,
        shape = AppTheme.shape.full,
        enabled = enabled,
        colors = CardDefaults.cardColors(containerColor = colors.selectedContainer),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        onClick = { expanded = true },
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
        ) {
            var title = initialLabel
            if (selectedIndex != -1) {
                title = items[selectedIndex].title
            }

            Text(
                modifier = Modifier.align(alignment = Alignment.Center),
                text = title,
                style = AppTheme.typography.body,
                color = colors.selectedText,
                textAlign = TextAlign.Center,
            )

            Icon(
                imageVector = arrowIcon,
                tint = colors.selectedText,
                contentDescription = "",
                modifier = Modifier.align(alignment = Alignment.CenterEnd)
            )
        }

        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(extraSmall = cornerRadius),
        ) {
            DropdownMenu(
                expanded = expanded,
                containerColor = colors.dropdownContainer,
                onDismissRequest = { expanded = false },
            ) {
                items.forEachIndexed { index, data ->

                    var textColor = colors.text
                    var backgroundColor = colors.container

                    if (selectedIndex == index) {
                        textColor = colors.selectedText
                        backgroundColor = colors.selectedContainer
                    }

                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .clip(shape = AppTheme.shape.large)
                            .background(color = backgroundColor),
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 12.dp),
                                    text = data.title,
                                    style = AppTheme.typography.body,
                                    color = textColor,
                                    textAlign = TextAlign.Center,
                                )
                            },
                            onClick = {
                                onSelected.invoke(data)
                                selectedIndex = index
                                expanded = false
                            },
                        )
                    }
                }
            }
        }
    }
}

private fun<T> List<DropdownData<T>>.getIndexByItem(item: T?, defaultValue: Int): Int {
    forEachIndexed { index, dropdownData ->
        if (dropdownData.data == item) {
            return index
        }
    }

    return defaultValue
}

@AppMultiPreview
@Composable
private fun Preview() {
    AppTheme {
        Dropdown(
            items = listOf(
                DropdownData(
                    title = "Item 1",
                    data = 1,
                ),
                DropdownData(
                    title = "Item 2",
                    data = 2,
                ),
                DropdownData(
                    title = "Item 3",
                    data = 3,
                ),
            ),
            initialLabel = "Choose",
            selectedValue = 1,
            onSelected = {},
        )
    }
}