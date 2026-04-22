package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZBackgroundColors {
    val default: Color

    companion object {
        val light = object : ZBackgroundColors {
            @ZColorBinding(label = "Primary/Solid/02")
            override val default: Color
                get() = ZColors.Primary02
        }

        val dark = object : ZBackgroundColors {
            @ZColorBinding(label = "Black/Solid/01")
            override val default: Color
                get() = ZColors.Black01
        }
    }
}