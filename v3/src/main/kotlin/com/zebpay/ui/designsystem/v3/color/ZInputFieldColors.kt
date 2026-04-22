package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZInputFieldColors {

    val borderDefault: Color

    val borderActive: Color

    val borderFilled: Color

    val borderError: Color

    val borderDisabled: Color

    val backgroundDefault: Color

    val backgroundDisabled: Color

    companion object {

        val light = object : ZInputFieldColors {
            @ZColorBinding("Blue/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("Blue/Solid/02")
            override val borderActive: Color
                get() = ZColors.Blue02

            @ZColorBinding("Blue/Solid/04")
            override val borderFilled: Color
                get() = ZColors.Blue04

            @ZColorBinding("Red/Solid/02")
            override val borderError: Color
                get() = ZColors.Red02

            @ZColorBinding("Grey/Solid/02")
            override val borderDisabled: Color
                get() = ZColors.Grey02

            @ZColorBinding("White/Solid/01")
            override val backgroundDefault: Color
                get() = ZColors.White01

            @ZColorBinding("White/Solid/02")
            override val backgroundDisabled: Color
                get() = ZColors.White02
        }

        val dark = object : ZInputFieldColors {
            @ZColorBinding("Black/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Black04

            @ZColorBinding("Blue/Solid/02")
            override val borderActive: Color
                get() = ZColors.Blue02

            @ZColorBinding("Black/Solid/04")
            override val borderFilled: Color
                get() = ZColors.Black04

            @ZColorBinding("Red/Solid/04")
            override val borderError: Color
                get() = ZColors.Red04

            @ZColorBinding("Grey/Solid/01")
            override val borderDisabled: Color
                get() = ZColors.Grey01

            @ZColorBinding("Black/Solid/02")
            override val backgroundDefault: Color
                get() = ZColors.Black02

            @ZColorBinding("Black/Solid/03")
            override val backgroundDisabled: Color
                get() = ZColors.Black03
        }
    }

}