package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZToastColors {

    val iconRed: Color

    val iconYellow: Color

    val iconGreen: Color

    val borderRed: Color

    val borderYellow: Color

    val borderGreen: Color

    val defaultBackgroundRed: Color

    val defaultBackgroundYellow: Color

    val defaultBackgroundGreen: Color

    val backgroundRed: Color

    val backgroundYellow: Color

    val backgroundGreen: Color

    companion object {

        val light = object : ZToastColors {
            @ZColorBinding("Red/solid/03")
            override val iconRed: Color
                get() = ZColors.Red03

            @ZColorBinding("Yellow/solid/03")
            override val iconYellow: Color
                get() = ZColors.Yellow03

            @ZColorBinding("Green/solid/02")
            override val iconGreen: Color
                get() = ZColors.Green02

            @ZColorBinding("Red/solid/03")
            override val borderRed: Color
                get() = ZColors.Red03

            @ZColorBinding("Yellow/solid/03")
            override val borderYellow: Color
                get() = ZColors.Yellow03

            @ZColorBinding("Green/solid/02")
            override val borderGreen: Color
                get() = ZColors.Green02

            @ZColorBinding("Red/solid/05")
            override val defaultBackgroundRed: Color
                get() = ZColors.Red05

            @ZColorBinding("Yellow/solid/05")
            override val defaultBackgroundYellow: Color
                get() = ZColors.Yellow05

            @ZColorBinding("Green/solid/05")
            override val defaultBackgroundGreen: Color
                get() = ZColors.Green05

            @ZColorBinding("Red/Solid/06")
            override val backgroundRed: Color
                get() = ZColors.Red06

            @ZColorBinding("Yellow/Solid/06")
            override val backgroundYellow: Color
                get() = ZColors.Yellow06

            @ZColorBinding("Green/Solid/06")
            override val backgroundGreen: Color
                get() = ZColors.Green06
        }

        val dark = object : ZToastColors {
            @ZColorBinding("Red/Solid/04")
            override val iconRed: Color
                get() = ZColors.Red04

            @ZColorBinding("Yellow/Solid/04")
            override val iconYellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Green/Solid/03")
            override val iconGreen: Color
                get() = ZColors.Green03

            @ZColorBinding("Red/Solid/04")
            override val borderRed: Color
                get() = ZColors.Red04

            @ZColorBinding("Yellow/Solid/04")
            override val borderYellow: Color
                get() = ZColors.Yellow04

            @ZColorBinding("Green/Solid/03")
            override val borderGreen: Color
                get() = ZColors.Green03

            @ZColorBinding("Red/Solid/01")
            override val defaultBackgroundRed: Color
                get() = ZColors.Red01

            @ZColorBinding("Yellow/Solid/01")
            override val defaultBackgroundYellow: Color
                get() = ZColors.Yellow01

            @ZColorBinding("Green/Solid/01")
            override val defaultBackgroundGreen: Color
                get() = ZColors.Green01

            @ZColorBinding("Red/Solid/07")
            override val backgroundRed: Color
                get() = ZColors.Red07

            @ZColorBinding("Yellow/Solid/07")
            override val backgroundYellow: Color
                get() = ZColors.Yellow07

            @ZColorBinding("Green/Solid/07")
            override val backgroundGreen: Color
                get() = ZColors.Green07
        }
    }
}