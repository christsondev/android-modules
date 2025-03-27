package com.christsondev.components.list

import androidx.compose.runtime.Composable

object ListItem {
    sealed interface Type {

        data class Default(
            val onClick: (() -> Unit)? = null,
        ) : Type

        data class Chevron(
            val onClick: () -> Unit,
        ) : Type

        data class Expandable(
            val expanded: Boolean = false,
            val content: @Composable () -> Unit,
        ) : Type
    }
}