package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf

data class ZHeaderGradientController(
    val onGradient: Boolean = false,
)

val LocalZHeaderGradientController = staticCompositionLocalOf {
    ZHeaderGradientController(false)
}