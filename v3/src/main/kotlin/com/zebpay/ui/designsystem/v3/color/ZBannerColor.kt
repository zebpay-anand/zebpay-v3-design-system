package com.zebpay.ui.designsystem.v3.color

import com.zebpay.ui.designsystem.v3.utils.ZColorBinding


interface ZBannerColors {

    val default: ZGradient

    val purple: ZGradient



    companion object {
        val light = object : ZBannerColors {
            @ZColorBinding("Blue/Gradient/03")
            override val default: ZGradient
                get() = ZGradientColors.Blue03

            @ZColorBinding("Purple/Gradient/02")
            override val purple: ZGradient
                get() = ZGradientColors.Purple02
        }

        val dark = object : ZBannerColors {
            @ZColorBinding("Blue/Gradient/01")
            override val default: ZGradient
                get() = ZGradientColors.Blue01

            @ZColorBinding("Purple/Gradient/02")
            override val purple: ZGradient
                get() = ZGradientColors.Purple02
        }
    }
}