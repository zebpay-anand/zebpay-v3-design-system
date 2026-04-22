package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZNavigationColors {

    val top: ZNavigationTop

    val bottom: ZNavigationBottom

    companion object {
        val light = object : ZNavigationColors {
            override val top: ZNavigationTop
                get() = ZNavigationTop.light
            override val bottom: ZNavigationBottom
                get() = ZNavigationBottom.light

        }
        val dark = object : ZNavigationColors {
            override val top: ZNavigationTop
                get() = ZNavigationTop.dark
            override val bottom: ZNavigationBottom
                get() = ZNavigationBottom.dark

        }
    }
}

interface ZNavigationTop {

    val onSolidText: Color

    val onSolidIcon: Color

    val onSolidIconBg: Color

    val onSolidIconBorder: Color

    val onGradientText: Color

    val onGradientIcon: Color

    val onGradientIconBg: Color

    val onGradientIconBorder: Color

    companion object {
        val light = object : ZNavigationTop {
            @ZColorBinding("Primary/Solid/01")
            override val onSolidText: Color
                get() = ZColors.Primary01

            @ZColorBinding("Primary/Solid/01")
            override val onSolidIcon: Color
                get() = ZColors.Primary01

            @ZColorBinding("White/Solid/05")
            override val onSolidIconBg: Color
                get() = ZColors.White05

            @ZColorBinding("Blue/Solid/04")
            override val onSolidIconBorder: Color
                get() = ZColors.Blue04

            @ZColorBinding("Primary/Solid/02")
            override val onGradientText: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val onGradientIcon: Color
                get() = ZColors.Primary02

            @ZColorBinding("White/Solid/05")
            override val onGradientIconBg: Color
                get() = ZColors.White05

            @ZColorBinding("White/Solid/04")
            override val onGradientIconBorder: Color
                get() = ZColors.White04

        }

        val dark = object : ZNavigationTop {
            @ZColorBinding("Primary/Solid/02")
            override val onSolidText: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val onSolidIcon: Color
                get() = ZColors.Primary02

            @ZColorBinding("Black/Solid/03")
            override val onSolidIconBg: Color
                get() = ZColors.Black03

            @ZColorBinding("Black/Solid/05")
            override val onSolidIconBorder: Color
                get() = ZColors.Black05

            @ZColorBinding("Primary/Solid/02")
            override val onGradientText: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Solid/02")
            override val onGradientIcon: Color
                get() = ZColors.Primary02

            @ZColorBinding("White/Solid/05")
            override val onGradientIconBg: Color
                get() = ZColors.White05

            @ZColorBinding("White/Solid/04")
            override val onGradientIconBorder: Color
                get() = ZColors.White04

        }

    }

}

interface ZNavigationBottom {

    val background: Color

    val active: ZGradient

    val default: Color

    companion object {
        val light = object : ZNavigationBottom {
            @ZColorBinding("White/Solid/04")
            override val background: Color
                get() = ZColors.White04

            @ZColorBinding("Primary/Gradient/01")
            override val active: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Secondary/Solid/01")
            override val default: Color
                get() = ZColors.Secondary01

        }

        val dark = object : ZNavigationBottom {
            @ZColorBinding("Black/Solid/07")
            override val background: Color
                get() = ZColors.Black07

            @ZColorBinding("Primary/Gradient/01")
            override val active: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Secondary/Solid/02")
            override val default: Color
                get() = ZColors.Secondary02

        }
    }
}