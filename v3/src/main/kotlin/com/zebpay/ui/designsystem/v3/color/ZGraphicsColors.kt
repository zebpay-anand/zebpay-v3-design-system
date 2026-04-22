package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient

interface ZGraphicsColors {

    val rrMeterPrimary: ZGradient

    val rrMeterSecondary: Color

    val pieChartGreen: Color

    val pieChartBlue: Color

    val pieChartPurple: Color

    val glowGreen: Color

    val glowRed: Color

    val graphGreen: ZGradient

    val graphRed: ZGradient

    val badgeActive: ZGradient

    val badgeDefault: Color

    companion object {

        val light = object : ZGraphicsColors {
            @ZColorBinding("Primary/Gradient/01")
            override val rrMeterPrimary: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Blue/Solid/04")
            override val rrMeterSecondary: Color
                get() = ZColors.Blue04

            @ZColorBinding("Green/Solid/04")
            override val pieChartGreen: Color
                get() = ZColors.Green04

            @ZColorBinding("Blue/Solid/03")
            override val pieChartBlue: Color
                get() = ZColors.Blue03

            @ZColorBinding("Purple/Solid/01")
            override val pieChartPurple: Color
                get() = ZColors.Purple01

            @ZColorBinding("Green/Solid/05")
            override val glowGreen: Color
                get() = ZColors.Green05

            @ZColorBinding("Red/Solid/05")
            override val glowRed: Color
                get() = ZColors.Red05

            @ZColorBinding("Illustration/Gradient/Green")
            override val graphGreen: ZGradient
                get() = ZGradientColors.MiscGradientGreen01

            @ZColorBinding("Illustration/Gradient/Red")
            override val graphRed: ZGradient
                get() = ZGradientColors.MiscGradientRed01

            @ZColorBinding("Primary/Gradient/01")
            override val badgeActive: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Blue/Solid/04")
            override val badgeDefault: Color
                get() = ZColors.Blue04
        }

        val dark = object : ZGraphicsColors {
            @ZColorBinding("Primary/Gradient/01")
            override val rrMeterPrimary: ZGradient
                get() = ZGradientColors.Primary01

            @ZColorBinding("Blue/Solid/04")
            override val rrMeterSecondary: Color
                get() = ZColors.Blue04

            @ZColorBinding("Green/Solid/04")
            override val pieChartGreen: Color
                get() = ZColors.Green04

            @ZColorBinding("Blue/Solid/03")
            override val pieChartBlue: Color
                get() = ZColors.Blue03

            @ZColorBinding("Purple/Solid/01")
            override val pieChartPurple: Color
                get() = ZColors.Purple01

            @ZColorBinding("Green/Solid/01")
            override val glowGreen: Color
                get() = ZColors.Green01

            @ZColorBinding("Red/Solid/01")
            override val glowRed: Color
                get() = ZColors.Red01

            @ZColorBinding("Green/Solid/01")
            override val graphGreen: ZGradient
                get() = ZColors.Green01.asZGradient()

            @ZColorBinding("Red/Solid/01")
            override val graphRed: ZGradient
                get() = ZColors.Red01.asZGradient()

            @ZColorBinding("Primary/Solid/02")
            override val badgeActive: ZGradient
                get() = ZColors.Primary02.asZGradient()

            @ZColorBinding("Black/Solid/06")
            override val badgeDefault: Color
                get() = ZColors.Black06
        }
    }
}