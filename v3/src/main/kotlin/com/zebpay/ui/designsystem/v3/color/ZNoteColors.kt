package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZNoteColors {

    val textRed: Color

    val textYellow: Color

    val textGreen: Color

    val borderDefault: Color

    val backgroundDefault: Color

    companion object {

        val light = object : ZNoteColors {
            @ZColorBinding("Red/Solid/03")
            override val textRed: Color
                get() = ZColors.Red03

            @ZColorBinding("Yellow/Solid/02")
            override val textYellow: Color
                get() = ZColors.Yellow02

            @ZColorBinding("Green/Solid/02")
            override val textGreen: Color
                get() = ZColors.Green02

            @ZColorBinding("Blue/Solid/04")
            override val borderDefault: Color
                get() = ZColors.Blue04

            @ZColorBinding("White/Solid/01")
            override val backgroundDefault: Color
                get() = ZColors.White01
        }

        val dark = object : ZNoteColors {
            @ZColorBinding("Red/Solid/04")
            override val textRed: Color
                get() = ZColors.Red04

            @ZColorBinding("Yellow/Solid/04")
            override val textYellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Green/Solid/03")
            override val textGreen: Color
                get() = ZColors.Green03

            @ZColorBinding("Blue/Solid/01")
            override val borderDefault: Color
                get() = ZColors.Blue01

            @ZColorBinding("Black/Solid/03")
            override val backgroundDefault: Color
                get() = ZColors.Black03
        }
    }
}