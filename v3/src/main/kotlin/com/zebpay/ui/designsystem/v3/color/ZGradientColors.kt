package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Color

object ZGradientColors {

    //Primary Gradient 01
    val Primary01 = ZGradient.Linear(
        listOf(
            0f to Color(0xFF5786FF),
            1f to Color(0xFF3751FE),
        ),
    )


    //Separator 02 Dark
    val Black01 = ZGradient.Linear(
        listOf(
            0f to Color(0x002F3554),
            0.3f to Color(0xFF393F61),
            0.7f to Color(0xFF393F61),
            1f to Color(0x002F3554),
        ),
    )

    //Gradient (Line Separator 02(Dark))
    val Purple01 = ZGradient.Linear(
        listOf(
            0f to Color(0x00363273),
            0.28255f to Color(0xFF363273),
            0.7f to Color(0xFF363273),
            0f to Color(0x00363273),
        ),
    )

    //Gradient (Banner Gradient Dark)
    val Blue01 = ZGradient.Linear(
        listOf(
            0f to Color(0xFF0A1244),
            0.5f to Color(0xFF182D80),
            1f to Color(0xFF130955),
        ),
        angle = 65f,
    )

    //Gradient (Separator 02 Gradient)
    val Blue02 = ZGradient.Linear(
        listOf(
            0f to Color(0x00DBE2FB),
            0.3f to Color(0xFFD5E9FF),
            0.7f to Color(0xFFDBE9FB),
            1f to Color(0x00DBE6FB),
        ),
    )

    //Gradient (Banner Gradient)
    val Blue03 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFE9F7FF),
            0.31f to Color(0xFFD8EDFF),
            0.605f to Color(0xFFF5FCF0),
            0.845691f to Color(0xFFE6EEFF),
        ),
        angle = 65f,
    )

    //Banner Gradient
    val Blue4 = ZGradient.Linear(
        listOf(
            0.000168637f to Color(0xFF02376E),
            0.501233f to Color(0xFF0E1A2B),
            0.871258f to Color(0xFF112842),
        ),
        angle = 70f,
    )


    //Gradient (Bottomsheet Bg (Light))
    val Green01 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFEFFFF1),
            1f to Color(0xFF59DA89),
        ),
        angle = 0f,
    )

    //Bottomsheet Bg(Dark)
    val Green02 = ZGradient.Linear(
        listOf(
            0.4f to Color(0xFF110B4B),
            1f to Color(0xFF1DAB62),
        ),
        angle = 0f,
    )

    val Green03 = ZGradient.Linear(
        listOf(
            0.089f to Color(0x8022D4AB),
            1f to Color(0x0059DA89),
        ),
        angle = 0f,
    )

    //Gradient
    val Green04 = ZGradient.Linear(
        listOf(
            0f to Color(0xFF3DC489),
            1f to Color(0xFF0F9F5A),
        ),
    )

    //Gradient
    val Green05 = ZGradient.Linear(
        listOf(
            0f to Color(0xFF1DBE8D),
            1f to Color(0xFF158569),
        ),
    )

    //Bottom Sheet
    val Red01 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFFFEFEF),
            1f to Color(0xFFFF9D9D),
        ),
        angle = 0f,
    )

    //Bottom Sheet
    val Red02 = ZGradient.Linear(
        listOf(
            0.23f to Color(0xFF120C4B),
            0.77f to Color(0xFFFF6767),
        ),
        angle = 0f,
    )

    //Graph
    val Red03 = ZGradient.Linear(
        listOf(
            0f to Color(0x80F75C5C),
            1f to Color(0x00F75C5C),
        ),
        angle = 0f,
    )

    //Gradient Button
    val Red04 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFF65053),
            1f to Color(0xFFD12A2D),
        ),
    )

    //Gradient Button
    val Red05 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFF75C5C),
            1f to Color(0xFFB43A2F),
        ),
    )

    //Miscellaneous/Green (G)
    val MiscGradientGreen01 = ZGradient.Linear(
        listOf(
            0.2f to Color(0x3322D4AB),
            1f to Color(0x0059DA89),
        ),
        angle = 0f,
    )

    val MiscGradientGreen02 = ZGradient.Linear(
        listOf(
            0f to Color(0x0089F1B8),
            0.39f to Color(0x4D89F1B8),
            1f to Color(0xFF89F1B8),
        ),
        angle = 90f,
    )

    val MiscGradientGreen03 = ZGradient.Linear(
        listOf(
            0f to Color(0x002F8756),
            0.39f to Color(0x4D2F8756),
            1f to Color(0xFF2F8756),
        ),
        angle = 90f,
    )

    //Miscellaneous/Red (G)
    val MiscGradientRed01 = ZGradient.Linear(
        listOf(
            0.2f to Color(0x33F75C5C),
            1f to Color(0x00F75C5C),
        ),
        angle = 180f,
    )

    val MiscGradientRed02 = ZGradient.Linear(
        listOf(
            0f to Color(0x00FFD3D3),
            0.39f to Color(0x4DFFD3D3),
            1f to Color(0xFFFFD3D3),
        ),
        angle = 270f,
    )

    val MiscGradientRed03 = ZGradient.Linear(
        listOf(
            0f to Color(0x008F4040),
            0.4f to Color(0x4D8F4040),
            1f to Color(0xFF8F4040),
        ),
        angle = 270f,
    )

    //Miscellaneous/White (G)
    val MiscGradientWhite = ZGradient.Linear(
        listOf(
            0.6f to Color(0x99FFFFFF),
            1f to Color(0x33FFFFFF),
        ),
        angle = 69f,
    )

    val Purple02 = ZGradient.Linear(
        listOf(
            0f to Color(0xFFBE23E7),
            0.54f to Color(0xFF5132D9),
            1f to Color(0xFF3794FF),
        ),
        angle = 287f,
    )

}