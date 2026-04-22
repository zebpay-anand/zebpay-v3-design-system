package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZBottomSheetColors {

    val scrim: Color

    val default: Color

    val green: ZGradient

    val red: ZGradient

    companion object {
        val light = object : ZBottomSheetColors {
            @ZColorBinding("Blur/01")
            override val scrim: Color
                get() = ZColors.Blur01

            @ZColorBinding("Primary/Solid/02")
            override val default: Color
                get() = ZColors.Primary02

            @ZColorBinding("Green/Gradient/01")
            override val green: ZGradient
                get() = ZGradientColors.Green01

            @ZColorBinding("Red/Gradient/01")
            override val red: ZGradient
                get() = ZGradientColors.Red01
        }

        val dark = object : ZBottomSheetColors {
            @ZColorBinding("Blur/01")
            override val scrim: Color
                get() = ZColors.Blur02

            @ZColorBinding("Black/Solid/01")
            override val default: Color
                get() = ZColors.Black01

            @ZColorBinding("Green/Gradient/02")
            override val green: ZGradient
                get() = ZGradientColors.Green02

            @ZColorBinding("Red/Gradient/02")
            override val red: ZGradient
                get() = ZGradientColors.Red02
        }
    }

}