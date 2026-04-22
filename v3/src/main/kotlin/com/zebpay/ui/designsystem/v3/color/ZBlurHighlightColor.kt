package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZBlurHighlightColor {
    val green: Color

    val red: Color

    val yellow: Color

    companion object {
        val light = object : ZBlurHighlightColor {
            @ZColorBinding("Green/Solid/05")
            override val green: Color
                get() = ZColors.Green05

            @ZColorBinding("Red/Solid/05")
            override val red: Color
                get() = ZColors.Red05

            @ZColorBinding("Yellow/Solid/05")
            override val yellow: Color
                get() = ZColors.Yellow05
        }

        val dark = object : ZBlurHighlightColor {
            @ZColorBinding("Green/Solid/01")
            override val green: Color
                get() = ZColors.Green01

            @ZColorBinding("Red/Solid/01")
            override val red: Color
                get() = ZColors.Red01

            @ZColorBinding("Yellow/Solid/01")
            override val yellow: Color
                get() = ZColors.Yellow01
        }
    }
}