package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZTextColors {

    val primary: Color

    val secondary: Color

    val white: Color

    val disabled: Color

    val red: Color

    val blue: Color

    val green: Color

    val greenOnGradient: Color

    val yellow: Color

    val grey: Color

    companion object {

        val light = object : ZTextColors {
            @ZColorBinding("Primary/Solid/01")
            override val primary: Color
                get() = ZColors.Primary01

            @ZColorBinding("Secondary/Solid/01")
            override val secondary: Color
                get() = ZColors.Secondary01

            @ZColorBinding("Primary/Solid/02")
            override val white: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/02")
            override val disabled: Color
                get() = ZColors.Grey02

            @ZColorBinding("Red/Solid/03")
            override val red: Color
                get() = ZColors.Red03

            @ZColorBinding("Blue/Solid/02")
            override val blue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Green/Solid/02")
            override val green: Color
                get() = ZColors.Green02

            @ZColorBinding("Green/Solid/03")
            override val greenOnGradient: Color
                get() = ZColors.Green03

            @ZColorBinding("Yellow/Solid/02")
            override val yellow: Color
                get() = ZColors.Yellow02

            @ZColorBinding("Grey/Solid/01")
            override val grey: Color
                get() = ZColors.Grey01
        }

        val dark = object : ZTextColors {
            @ZColorBinding("Primary/Solid/02")
            override val primary: Color
                get() = ZColors.Primary02

            @ZColorBinding("Secondary/Solid/02")
            override val secondary: Color
                get() = ZColors.Secondary02

            @ZColorBinding("Primary/Solid/02")
            override val white: Color
                get() = ZColors.Primary02

            @ZColorBinding("Grey/Solid/01")
            override val disabled: Color
                get() = ZColors.Grey01

            @ZColorBinding("Red/Solid/04")
            override val red: Color
                get() = ZColors.Red04

            @ZColorBinding("Blue/Solid/02")
            override val blue: Color
                get() = ZColors.Blue02

            @ZColorBinding("Green/Solid/03")
            override val green: Color
                get() = ZColors.Green03

            @ZColorBinding("Green/Solid/03")
            override val greenOnGradient: Color
                get() = ZColors.Green03

            @ZColorBinding("Yellow/Solid/04")
            override val yellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Grey/Solid/02")
            override val grey: Color
                get() = ZColors.Grey02
        }
    }
}