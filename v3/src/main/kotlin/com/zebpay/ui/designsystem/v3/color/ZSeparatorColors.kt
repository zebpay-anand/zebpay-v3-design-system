package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZSeparatorColors {

    val solidDefault: Color

    val solidOnGradient: Color

    val gradientDefault: ZGradient

    companion object {
        val light = object : ZSeparatorColors {
            @ZColorBinding("Blue/Solid/04")
            override val solidDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("Blue/Solid/04")
            override val solidOnGradient: Color
                get() = ZColors.Blue04

            @ZColorBinding("Blue/Gradient/02")
            override val gradientDefault: ZGradient
                get() = ZGradientColors.Blue02
        }

        val dark = object : ZSeparatorColors {
            @ZColorBinding("Black/Solid/05")
            override val solidDefault: Color
                get() = ZColors.Black05

            @ZColorBinding("White/Solid/04")
            override val solidOnGradient: Color
                get() = ZColors.White04

            @ZColorBinding("Black/Gradient/01")
            override val gradientDefault: ZGradient
                get() = ZGradientColors.Black01
        }
    }
}