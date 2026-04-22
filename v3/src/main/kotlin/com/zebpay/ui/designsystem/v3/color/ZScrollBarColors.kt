package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

interface ZScrollBarColors {
    val default: Color

    companion object {
        val light = object : ZScrollBarColors {
            @ZColorBinding("Blue/Solid/04")
            override val default: Color
                get() = ZColors.Blue04
        }

        val dark = object : ZScrollBarColors {
            @ZColorBinding("Black/Solid/04")
            override val default: Color
                get() = ZColors.Black04
        }
    }
}