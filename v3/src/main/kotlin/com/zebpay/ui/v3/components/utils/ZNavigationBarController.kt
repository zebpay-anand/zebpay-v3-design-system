package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.v3.components.atoms.navigation.ZNavColors

val LocalZNavigationBarColor = staticCompositionLocalOf<ZNavColors> {
    error("No ZNavbar item color provided")
}