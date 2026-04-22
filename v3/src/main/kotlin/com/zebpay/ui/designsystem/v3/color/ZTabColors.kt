package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient

interface ZTabColors {

    val primary: ZTabPrimary

    val secondary: ZTabSecondary

    val tertiary: ZTabTertiary

    companion object {

        val light = object : ZTabColors {
            override val primary: ZTabPrimary
                get() = ZTabPrimary.light
            override val secondary: ZTabSecondary
                get() = ZTabSecondary.light
            override val tertiary: ZTabTertiary
                get() = ZTabTertiary.light
        }

        val dark = object : ZTabColors {
            override val primary: ZTabPrimary
                get() = ZTabPrimary.dark
            override val secondary: ZTabSecondary
                get() = ZTabSecondary.dark
            override val tertiary: ZTabTertiary
                get() = ZTabTertiary.dark
        }

    }
}


interface ZTabPrimary {

    val textActive: Color

    val textDefault: Color

    val textDisable: Color

    val fillDefault: Color

    val fillActive: ZGradient

    val fillDisable: Color

    val borderDefault: Color

    val borderDisable: Color

    companion object {

        val light = object : ZTabPrimary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Secondary/Solid/01")
            override val textDefault: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Grey/Solid/02")
            override val textDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("White/Solid/01")
            override val fillDefault: Color
                get() = ZColors.White01

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("White/Solid/04")
            override val fillDisable: Color
                get() = ZColors.White04

            @ZColorBinding("Blue/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("Grey/Solid/02")
            override val borderDisable: Color
                get() = ZColors.Grey02
        }

        val dark = object : ZTabPrimary {
            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Secondary/Solid/02")
            override val textDefault: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Grey/Solid/01")
            override val textDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Black/Solid/03")
            override val fillDefault: Color
                get() = ZColors.Black03

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Black/Solid/03")
            override val fillDisable: Color
                get() = ZColors.Black03

            @ZColorBinding("Black/Solid/06")
            override val borderDefault: Color
                get() = ZColors.Black06

            @ZColorBinding("Grey/Solid/01")
            override val borderDisable: Color
                get() = ZColors.Grey01
        }

    }

}

interface ZTabSecondary {

    val textDefault: Color

    val textActive: Color

    val textDisable: Color

    val fillDefault: Color

    val fillActive: ZGradient

    val fillDisable: Color

    val borderDefault: Color

    val borderActive: ZGradient

    val borderDisable: Color

    companion object {
        val light = object : ZTabSecondary {
            @ZColorBinding("Secondary/Solid/01")
            override val textDefault: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/02")
            override val textDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("White/Solid/02")
            override val fillDefault: Color
                get() = ZColors.White02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("White/Solid/02")
            override val fillDisable: Color
                get() = ZColors.White02

            @ZColorBinding("Blue/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("Primary/Gradient/01")
            override val borderActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val borderDisable: Color
                get() = ZColors.Grey02
        }

        val dark = object : ZTabSecondary {
            @ZColorBinding("Secondary/Solid/02")
            override val textDefault: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Primary/Solid/02")
            override val textActive: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/01")
            override val textDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Black/Solid/02")
            override val fillDefault: Color
                get() = ZColors.Black02

            @ZColorBinding("Primary/Gradient/01")
            override val fillActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Black/Solid/02")
            override val fillDisable: Color
                get() = ZColors.Black02

            @ZColorBinding("Black/Solid/06")
            override val borderDefault: Color
                get() = ZColors.Black06

            @ZColorBinding("Primary/Solid/02")
            override val borderActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Grey/Solid/01")
            override val borderDisable: Color
                get() = ZColors.Grey01
        }
    }

}

interface ZTabTertiary {

    val textDefault: Color

    val textActive: ZGradient

    val textDisable: Color

    companion object {
        val light = object : ZTabTertiary {
            @ZColorBinding("Secondary/Solid/01")
            override val textDefault: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Primary/Gradient/01")
            override val textActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val textDisable: Color
                get() = ZColors.Grey02
        }

        val dark = object : ZTabTertiary {
            @ZColorBinding("Secondary/Solid/02")
            override val textDefault: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Primary/Solid/02")
            override val textActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Grey/Solid/01")
            override val textDisable: Color
                get() = ZColors.Grey01
        }
    }
}