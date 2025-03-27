package com.christsondev.components.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.christsondev.components.R

internal val nunitoFamily = FontFamily(
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_medium, FontWeight.Medium),
    Font(R.font.nunito_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_bold, FontWeight.Bold),
)

@Immutable
data class AppTypography(
    val h1: TextStyle = TextStyle(
        fontSize = 32.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
    ),
    val h2: TextStyle = TextStyle(
        fontSize = 28.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Normal,
    ),
    val h3: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Normal,
    ),
    val h3Bold: TextStyle = TextStyle(
        fontSize = 24.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
    ),
    val title: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
    ),
    val body: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Normal,
    ),
    val description: TextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Normal,
    ),
    val smallTitle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Bold,
    ),
    val smallBody: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = nunitoFamily,
        fontWeight = FontWeight.Normal,
    ),
)

internal val LocalAppTypography = staticCompositionLocalOf<AppTypography> {
    error("LocalAppTypography were not set")
}

internal val appTypography = AppTypography()