package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient

interface ZIconColors {

    val singleTonePrimary: Color

    val singleToneSecondary: Color

    val singleToneActive: ZGradient

    val singleToneDisable: Color

    val singleToneWhite: Color

    val singleToneRed: Color

    val singleToneGreen: Color

    val singleToneYellow: Color

    val singleToneBlue: Color

    val singleToneGrey: Color

    val dualToneSolid: Color

    val dualToneGradient: ZGradient

    val illustrationGreen: ZGradient

    val illustrationRed: ZGradient

    companion object {

        val light = object : ZIconColors {
            @ZColorBinding("Primary/Solid/01")
            override val singleTonePrimary: Color
                get() = ZColors.Primary01

            @ZColorBinding("Secondary/Solid/01")
            override val singleToneSecondary: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Primary/Gradient/01")
            override val singleToneActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Grey/Solid/02")
            override val singleToneDisable: Color
                get() = ZColors.Grey02

            @ZColorBinding("Primary/Solid/02")
            override val singleToneWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Red/Solid/03")
            override val singleToneRed: Color
                get() = ZColors.Red03

            @ZColorBinding("Green/Solid/02")
            override val singleToneGreen: Color
                get() = ZColors.Green02

            @ZColorBinding("Yellow/Solid/02")
            override val singleToneYellow: Color
                get() = ZColors.Yellow02

            @ZColorBinding("Blue/Solid/02")
            override val singleToneBlue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Grey/Solid/01")
            override val singleToneGrey: Color
                get() = ZColors.Grey01

            @ZColorBinding("Primary/Solid/01")
            override val dualToneSolid: Color
                get() = ZColors.Primary01

            @ZColorBinding("Primary/Gradient/01")
            override val dualToneGradient: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Illustration/Gradient/Green")
            override val illustrationGreen: ZGradient
                get() = ZGradientColors.MiscGradientGreen01

            @ZColorBinding("Illustration/Gradient/Red")
            override val illustrationRed: ZGradient
                get() = ZGradientColors.MiscGradientRed01
        }

        val dark = object : ZIconColors {
            @ZColorBinding("Primary/Solid/022")
            override val singleTonePrimary: Color
                get() = ZColors.Primary02

            @ZColorBinding("Secondary/Solid/02")
            override val singleToneSecondary: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Primary/Solid/02")
            override val singleToneActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Grey/Solid/01")
            override val singleToneDisable: Color
                get() = ZColors.Grey01

            @ZColorBinding("Primary/Solid/02")
            override val singleToneWhite: Color
                get() = ZColors.Primary02

            @ZColorBinding("Red/Solid/04")
            override val singleToneRed: Color
                get() = ZColors.Red04

            @ZColorBinding("Green/Solid/03")
            override val singleToneGreen: Color
                get() = ZColors.Green03

            @ZColorBinding("Yellow/Solid/04")
            override val singleToneYellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Blue/Solid/02")
            override val singleToneBlue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Grey/Solid/02")
            override val singleToneGrey: Color
                get() = ZColors.Grey02

            @ZColorBinding("Primary/Solid/02")
            override val dualToneSolid: Color
                get() = ZColors.Primary02

            @ZColorBinding("Primary/Gradient/01")
            override val dualToneGradient: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Illustration/Gradient/Green")
            override val illustrationGreen: ZGradient
                get() = ZGradientColors.MiscGradientGreen01

            @ZColorBinding("Illustration/Gradient/Red")
            override val illustrationRed: ZGradient
                get() = ZGradientColors.MiscGradientRed01
        }
    }

}