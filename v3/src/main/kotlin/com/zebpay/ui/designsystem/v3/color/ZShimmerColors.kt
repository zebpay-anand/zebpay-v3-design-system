package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZShimmerColors {
    val shimmer01: Color

    val shimmer02: Color

    companion object {
        val light = object : ZShimmerColors {
            @ZColorBinding("White/Solid/02")
            override val shimmer01: Color
                get() = ZColors.White02
            @ZColorBinding("White/Solid/03")
            override val shimmer02: Color
                get() = ZColors.White03
        }

        val dark = object : ZShimmerColors {
            @ZColorBinding("Black/Solid/03")
            override val shimmer01: Color
                get() = ZColors.Black03
            @ZColorBinding("Black/Solid/02")
            override val shimmer02: Color
                get() = ZColors.Black02
        }
    }
}