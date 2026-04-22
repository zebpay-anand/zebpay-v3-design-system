package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.v3.components.molecules.button.ZOutlineColor
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryColor
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor

val LocalZPrimaryButtonColor = staticCompositionLocalOf {
    ZPrimaryColor.Blue
}

val LocalZOutlineButtonColor = staticCompositionLocalOf {
    ZOutlineColor.Blue
}

val LocalZTextButtonColor = staticCompositionLocalOf {
    ZTextButtonColor.Blue
}

