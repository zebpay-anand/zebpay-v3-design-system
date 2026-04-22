package com.zebpay.ui.designsystem.v3.color

import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZMiscGradients {

    val askGreen: ZGradient

    val bidRed: ZGradient

    companion object {
        val light = object : ZMiscGradients {
            @ZColorBinding("Miscellaneous/Green 02")
            override val askGreen: ZGradient
                get() = ZGradientColors.MiscGradientGreen02
            @ZColorBinding("Miscellaneous/Red 02")
            override val bidRed: ZGradient
                get() = ZGradientColors.MiscGradientRed02
        }

        val dark = object : ZMiscGradients {
            @ZColorBinding("Miscellaneous/Green 03")
            override val askGreen: ZGradient
                get() = ZGradientColors.MiscGradientGreen03
            @ZColorBinding("Miscellaneous/Red 03")
            override val bidRed: ZGradient
                get() = ZGradientColors.MiscGradientRed03
        }
    }
}