package com.zebpay.ui.designsystem.v3.color

import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZGraphColor {

    val green: ZGradient

    val red: ZGradient

    companion object {
        val light = object : ZGraphColor {
            @ZColorBinding("Green/Gradient/03")
            override val green: ZGradient
                get() = ZGradientColors.Green03

            @ZColorBinding("Red/Gradient/03")
            override val red: ZGradient
                get() = ZGradientColors.Red03
        }

        val dark = object : ZGraphColor {
            @ZColorBinding("Green/Gradient/03")
            override val green: ZGradient
                get() = ZGradientColors.Green03

            @ZColorBinding("Red/Gradient/03")
            override val red: ZGradient
                get() = ZGradientColors.Red03
        }
    }
}