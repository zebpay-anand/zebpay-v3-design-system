package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.color.ZGradientColors

val LocalZGradientColor = staticCompositionLocalOf<ZGradient> {
    ZGradientColors.Primary01
}