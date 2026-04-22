package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

val LocalZAmountMasked = staticCompositionLocalOf {
    false
}

val LocalFiatCurrencyCode = staticCompositionLocalOf<String> {
    "INR"
}

val LocalFiatCurrencyCodeIcon = staticCompositionLocalOf {
    ZIcons.ic_currency_inr.asZIcon
}

val LocalDeviceFiatCurrencyCode = staticCompositionLocalOf {
    "INR"
}

val LocalToggleCurrencyCode = staticCompositionLocalOf {
    "INR"
}