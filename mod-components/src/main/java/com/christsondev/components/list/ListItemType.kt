package com.christsondev.components.list

import androidx.compose.runtime.Composable
import com.christsondev.components.IconComposer

object ListItem {
    sealed interface Type {

        data class Default(
            val onClick: (() -> Unit)? = null,
        ) : Type

        data class Chevron(
            val onClick: () -> Unit,
            val chevronIcon: IconComposer = IconComposer.ChevronRight(),
        ) : Type

        data class Expandable(
            val expanded: Boolean = false,
            val content: @Composable () -> Unit,
        ) : Type
    }
}