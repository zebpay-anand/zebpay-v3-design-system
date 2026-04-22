package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient

interface ZPillsColors {

    val textDefault: Color

    val textActive: Color

    val textDisabled: Color

    val fillDefault: Color

    val fillActive: ZGradient

    val fillDisabled: Color

    val borderDefault: Color

    val borderActive: ZGradient

    val boarderDisabled: Color

    companion object {

        val light = object : ZPillsColors {
            @ZColorBinding("Secondary/Solid/01")
            override val textDefault: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/02")
            override val textDisabled: Color
                get() = ZColors.Grey02

            @ZColorBinding("White/Solid/02")
            override val fillDefault: Color
                get() = ZColors.White02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("White/Solid/02")
            override val fillDisabled: Color
                get() = ZColors.White02

            @ZColorBinding("Blue/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("Primary/Gradient/01")
            override val borderActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val boarderDisabled: Color
                get() = ZColors.Grey02
        }

        val dark = object : ZPillsColors {
            @ZColorBinding("Secondary/Solid/02")
            override val textDefault: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/01")
            override val textDisabled: Color
                get() = ZColors.Grey01

            @ZColorBinding("Black/Solid/02")
            override val fillDefault: Color
                get() = ZColors.Black02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Black/Solid/02")
            override val fillDisabled: Color
                get() = ZColors.Black02

            @ZColorBinding("Black/Solid/06")
            override val borderDefault: Color
                get() = ZColors.Black06

            @ZColorBinding("Primary/Solid/02")
            override val borderActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Grey/Solid/01")
            override val boarderDisabled: Color
                get() = ZColors.Grey01
        }
    }

}