package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ZDecorationItemColor(
    val iconColor: Color = Color.Unspecified,
    val textColor: Color = Color.Black,
    val background: Color = Color.Transparent,
    val border: Color = Color.Blue,
    val isDisable: Boolean = false,
    val isError: Boolean = false,
)

val LocalZDecorationItemColor = staticCompositionLocalOf {
    ZDecorationItemColor()
}