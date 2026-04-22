package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient

interface ZButtonColors {

    val primary: ZButtonPrimary

    val secondary: ZButtonSecondary

    val tertiary: ZButtonTertiary


    companion object {
        val light = object : ZButtonColors {
            override val primary: ZButtonPrimary
                get() = ZButtonPrimary.light
            override val secondary: ZButtonSecondary
                get() = ZButtonSecondary.light
            override val tertiary: ZButtonTertiary
                get() = ZButtonTertiary.light

        }

        val dark = object : ZButtonColors {
            override val primary: ZButtonPrimary
                get() = ZButtonPrimary.dark
            override val secondary: ZButtonSecondary
                get() = ZButtonSecondary.dark
            override val tertiary: ZButtonTertiary
                get() = ZButtonTertiary.dark

        }
    }
}

interface ZButtonPrimary {

    val textActive: Color

    val textDisable: Color

    val fillActive: ZGradient

    val fillRed: ZGradient

    val fillGreen: ZGradient

    val fillDisable: Color

    companion object {

        internal val light = object : ZButtonPrimary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val textDisable: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Red/Gradient/04")
            override val fillRed: ZGradient
                get() = ZGradientColors.Red04

            @ZColorBinding("Green/Gradient/04")
            override val fillGreen: ZGradient
                get() = ZGradientColors.Green04

            @ZColorBinding("Grey/Solid/02")
            override val fillDisable: Color
                get() = ZColors.Grey02

        }

        internal val dark = object : ZButtonPrimary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val textDisable: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Red/Gradient/05")
            override val fillRed: ZGradient
                get() = ZGradientColors.Red05

            @ZColorBinding("Green/Gradient/05")
            override val fillGreen: ZGradient
                get() = ZGradientColors.Green05

            @ZColorBinding("Grey/Solid/01")
            override val fillDisable: Color
                get() = ZColors.Grey01

        }
    }

}

interface ZButtonSecondary {

    val textActive: ZGradient

    val textWhite: Color

    val textDisable: Color

    val fillActive: Color

    val fillDisable: Color

    val borderActive: ZGradient

    val borderDisable: Color

    val borderGreen: ZGradient

    val borderRed: ZGradient

    companion object {

        val light = object : ZButtonSecondary {
            @ZColorBinding("Primary/Gradient/01")
            override val textActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Primary/Solid/02")
            override val textWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/02")
            override val textDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("White/Solid/01")
            override val fillActive: Color
                get() = ZColors.White01

            @ZColorBinding("White/Solid/01")
            override val fillDisable: Color
                get() = ZColors.White01

            @ZColorBinding("Primary/Gradient/01")
            override val borderActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val borderDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("Green/Gradient/04")
            override val borderGreen: ZGradient
                get() = ZGradientColors.Green04

            @ZColorBinding("Red/Gradient/04")
            override val borderRed: ZGradient
                get() = ZGradientColors.Red04
        }

        val dark = object : ZButtonSecondary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Primary/Solid/02")
            override val textWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/01")
            override val textDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Black/Solid/02")
            override val fillActive: Color
                get() = ZColors.Black02

            @ZColorBinding("Black/Solid/02")
            override val fillDisable: Color
                get() = ZColors.Black02

            @ZColorBinding("Primary/Gradient/01")
            override val borderActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/01")
            override val borderDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Green/Gradient/05")
            override val borderGreen: ZGradient
                get() = ZGradientColors.Green05

            @ZColorBinding("Red/Gradient/05")
            override val borderRed: ZGradient
                get() = ZGradientColors.Red05

        }
    }
}

interface ZButtonTertiary {
    val textActive: ZGradient
    val textDisable: Color
    val textBlack: Color
    val textWhite: Color
    val tabActive: ZGradient

    companion object {

        val light = object : ZButtonTertiary {
            @ZColorBinding("Primary/Gradient/01")
            override val textActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val textDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("Primary/Solid/01")
            override val textBlack: Color
                get() = ZColors.Primary01

            @ZColorBinding("Primary/Solid/02")
            override val textWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Gradient/01")
            override val tabActive: ZGradient
                get() = ZGradientColors.Primary01

        }
        val dark = object : ZButtonTertiary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Grey/Solid/01")
            override val textDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Primary/Solid/02")
            override val textBlack: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val textWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val tabActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

        }
    }
}