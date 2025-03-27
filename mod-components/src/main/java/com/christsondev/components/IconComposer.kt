package com.christsondev.components

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.christsondev.components.theme.AppTheme
import androidx.compose.material3.Icon as M3Icon

@Immutable
sealed interface IconComposer {

    @Composable
    fun Compose(modifier: Modifier)

    fun copyComposer(tint: Color?): IconComposer = this

    data class ResourceIcon(
        @DrawableRes val resourceId: Int,
        val contentDescription: String = "",
        val tint: Color? = null,
    ) : IconComposer {
        @Composable
        override fun Compose(modifier: Modifier) {
            M3Icon(
                modifier = modifier,
                painter = painterResource(id = resourceId),
                contentDescription = contentDescription,
                tint = tint ?: AppTheme.color.onSurface,
            )
        }

        override fun copyComposer(tint: Color?): IconComposer = copy(tint = tint)
    }

    data class Icon(
        val imageVector: ImageVector,
        val contentDescription: String = "",
        val tint: Color? = null,
    ) : IconComposer {
        @Composable
        override fun Compose(modifier: Modifier) {
            M3Icon(
                modifier = modifier,
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = tint ?: AppTheme.color.onSurface,
            )
        }

        override fun copyComposer(tint: Color?): IconComposer = copy(tint = tint)
    }

    data class Warning(val contentDescription: String = "") : IconComposer {
        @Composable
        override fun Compose(modifier: Modifier) {
            Icon(
                imageVector = Icons.Rounded.Warning,
                contentDescription = contentDescription,
                tint = AppTheme.color.warning,
            ).Compose(modifier = modifier)
        }
    }

    data class ChevronRight(val contentDescription: String = "") : IconComposer {
        @Composable
        override fun Compose(modifier: Modifier) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                contentDescription = contentDescription,
                tint = AppTheme.color.onSurface,
            ).Compose(modifier = modifier)
        }
    }

    data class Back(val contentDescription: String = "") : IconComposer {
        @Composable
        override fun Compose(modifier: Modifier) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = contentDescription,
                tint = AppTheme.color.onSurface,
            ).Compose(modifier = modifier)
        }
    }
}