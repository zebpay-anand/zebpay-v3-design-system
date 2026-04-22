package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf


data class ZBottomController(
    val close: () -> Unit,
)


val LocalSheetCloseController = staticCompositionLocalOf<ZBottomController> {
    error("No LocalSheetClose provided")
}