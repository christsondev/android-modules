package com.christsondev.components.compose

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun slideLeft() = slideInHorizontally(initialOffsetX = { it })

fun slideRight() = slideOutHorizontally(targetOffsetX = { it })