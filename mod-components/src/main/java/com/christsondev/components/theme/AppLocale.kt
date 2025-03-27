package com.christsondev.components.theme

import androidx.compose.runtime.compositionLocalOf
import java.util.Locale

val LocalAppLocale = compositionLocalOf { Locale.getDefault() }