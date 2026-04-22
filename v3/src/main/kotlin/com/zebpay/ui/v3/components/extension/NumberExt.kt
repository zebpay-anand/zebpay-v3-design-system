package com.zebpay.ui.v3.components.extension

import kotlin.math.abs

fun Double?.toDecimalString(decimalPlaces: Int = 2, onlyPositive: Boolean = true): String {
    return if (this == null)
        ""
    else
        "%.${decimalPlaces}f".format(if (onlyPositive) abs(this) else this)
}