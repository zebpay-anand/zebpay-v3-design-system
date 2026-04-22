package com.zebpay.ui.designsystem.v3.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import com.zebpay.ui.designsystem.v3.utils.TextStyleBinding
import com.zebpay.ui.v3.R

internal val FigTreeFontFamily = FontFamily(
    Font(R.font.figtree_regular, weight = FontWeight.Normal),
    Font(R.font.figtree_semibold, weight = FontWeight.SemiBold),
    Font(R.font.figtree_bold, weight = FontWeight.Bold),
    Font(R.font.figtree_medium, weight = FontWeight.Medium),
    Font(R.font.figtree_extra_bold, weight = FontWeight.ExtraBold),
    Font(R.font.figtree_black, weight = FontWeight.Black),
)

@Immutable
data class ZTypography(
    @TextStyleBinding("Display", "D1/Regular_32")
    val displayRegularD1: TextStyle,
    @TextStyleBinding("Display", "D2/Regular_28")
    val displayRegularD2: TextStyle,
    @TextStyleBinding("Display", "D3/Regular_24")
    val displayRegularD3: TextStyle,
    @TextStyleBinding("Headline", "H1/Regular_24", true)
    val headlineRegularH1: TextStyle,
    @TextStyleBinding("Headline", "H2/Regular_20", true)
    val headlineRegularH2: TextStyle,
    @TextStyleBinding("Headline", "H3/Regular_16", true)
    val headlineRegularH3: TextStyle,
    @TextStyleBinding("Headline", "H4/Regular_14", true)
    val headlineRegularH4: TextStyle,
    @TextStyleBinding("Body", "B1/Regular_20")
    val bodyRegularB1: TextStyle,
    @TextStyleBinding("Body", "B2/Regular_16")
    val bodyRegularB2: TextStyle,
    @TextStyleBinding("Body", "B3/Regular_14")
    val bodyRegularB3: TextStyle,
    @TextStyleBinding("Body", "B4/Regular_12")
    val bodyRegularB4: TextStyle,
    @TextStyleBinding("Body", "B5/Regular_10")
    val bodyRegularB5: TextStyle,
    @TextStyleBinding("CTA", "C1/bold_16", true)
    val ctaC1: TextStyle,
    @TextStyleBinding("CTA", "C2/semibold_14", true)
    val ctaC2: TextStyle,
)

val ZebpayTypography = ZTypography(
    displayRegularD1 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 32.sp,
        lineHeight = 8.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    displayRegularD2 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 28.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    displayRegularD3 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    headlineRegularH1 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 24.sp,
        lineHeight = 34.sp,
        letterSpacing = 0.8.sp,
    ).adjustLineHeight(),
    headlineRegularH2 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.8.sp,
    ).adjustLineHeight(),
    headlineRegularH3 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.8.sp,
    ).adjustLineHeight(),
    headlineRegularH4 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    bodyRegularB1 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 20.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    bodyRegularB2 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    bodyRegularB3 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    bodyRegularB4 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    bodyRegularB5 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.4.sp,
    ).adjustLineHeight(),
    ctaC1 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.8.sp,
    ).adjustLineHeight(),
    ctaC2 = TextStyle(
        fontFamily = FigTreeFontFamily,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.8.sp,
    ).adjustLineHeight(),
)

fun TextStyle.adjustLineHeight(): TextStyle {
    return copy(
        platformStyle = PlatformTextStyle(
            includeFontPadding = false,
        ),
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center, trim = LineHeightStyle.Trim.None,
        ),
    )
}