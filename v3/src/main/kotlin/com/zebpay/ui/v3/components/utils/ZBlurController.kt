package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf

data class ZBlurController(
    val toggle: (Boolean) -> Unit,
)

val LocalBlurController = staticCompositionLocalOf<ZBlurController> {
    error("No BlurController provided")
}

