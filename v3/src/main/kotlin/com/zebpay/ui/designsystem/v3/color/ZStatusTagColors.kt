package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZStatusTagColors {

    val text: Color

    val red: Color

    val green: Color

    val yellow: Color

    val blue: Color

    val blue02: Color

    val grey: Color

    companion object {

        val light = object : ZStatusTagColors {
            @ZColorBinding("Primary/Solid/02")
            override val text: Color
                get() = ZColors.Primary02

            @ZColorBinding("Red/Solid/02")
            override val red: Color
                get() = ZColors.Red02

            @ZColorBinding("Green/Solid/02")
            override val green: Color
                get() = ZColors.Green02

            @ZColorBinding("Yellow/Solid/04")
            override val yellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Blue/Solid/02")
            override val blue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Blue/Solid/05")
            override val blue02: Color
                get() = ZColors.Blue05

            @ZColorBinding("Grey/Solid/01")
            override val grey: Color
                get() = ZColors.Grey01
        }

        val dark = object : ZStatusTagColors {
            @ZColorBinding("Primary/Solid/02")
            override val text: Color
                get() = ZColors.Primary02

            @ZColorBinding("Red/Solid/04")
            override val red: Color
                get() = ZColors.Red02

            @ZColorBinding("Green/Solid/03")
            override val green: Color
                get() = ZColors.Green02

            @ZColorBinding("Yellow/Solid/04")
            override val yellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Blue/Solid/02")
            override val blue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Blue/Solid/05")
            override val blue02: Color
                get() = ZColors.Blue05

            @ZColorBinding("Grey/Solid/02")
            override val grey: Color
                get() = ZColors.Grey02
        }
    }

}