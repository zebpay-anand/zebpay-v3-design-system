package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZCardColors {

    val fill: Color

    val selected: Color

    //Default Border
    val border: Color

    val borderYellow: Color

    val borderRed: Color

    val borderGreen: Color

    val borderBlue: ZGradient



    companion object {
        val light = object : ZCardColors {
            @ZColorBinding("White/Solid/01")
            override val fill: Color
                get() = ZColors.White01

            @ZColorBinding("White/Solid/02")
            override val selected: Color
                get() = ZColors.White02

            @ZColorBinding("Blue/Solid/04")
            override val border: Color
                get() = ZColors.Blue04

            @ZColorBinding("Yellow/Solid/02")
            override val borderYellow: Color
                get() = ZColors.Yellow02

            @ZColorBinding("Red/Solid/02")
            override val borderRed: Color
                get() = ZColors.Red02

            @ZColorBinding("Green/Solid/02")
            override val borderGreen: Color
                get() = ZColors.Green02

            @ZColorBinding("Primary/Gradient/01")
            override val borderBlue: ZGradient
                get() = ZGradientColors.Primary01

        }
        val dark = object : ZCardColors {
            @ZColorBinding("Black/Solid/03")
            override val fill: Color
                get() = ZColors.Black03

            @ZColorBinding("Black/Solid/02")
            override val selected: Color
                get() = ZColors.Black02

            @ZColorBinding("Blue/Solid/01")
            override val border: Color
                get() = ZColors.Blue01

            @ZColorBinding("Yellow/Solid/04")
            override val borderYellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Red/Solid/04")
            override val borderRed: Color
                get() = ZColors.Red04

            @ZColorBinding("Green/Solid/03")
            override val borderGreen: Color
                get() = ZColors.Green03

            @ZColorBinding("Primary/Gradient/01")
            override val borderBlue: ZGradient
                get() = ZGradientColors.Primary01
        }
    }
}