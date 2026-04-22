package com.zebpay.ui.designsystem.v3.color

import com.zebpay.ui.designsystem.v3.utils.ZColorBinding

data class ZColorScheme(

    val text: ZTextColors,

    val buttons: ZButtonColors,

    val background: ZBackgroundColors,

    val navigation: ZNavigationColors,

    val card: ZCardColors,

    val inputField: ZInputFieldColors,

    val tab: ZTabColors,

    val pills: ZPillsColors,

    val separator: ZSeparatorColors,

    val icon: ZIconColors,

    val status: ZStatusTagColors,

    val toast: ZToastColors,

    val note: ZNoteColors,

    val graphics: ZGraphicsColors,

    val scrollBar: ZScrollBarColors,

    val banner: ZBannerColors,

    val bottomSheet: ZBottomSheetColors,

    val graph: ZGraphColor,

    val blurHighlight: ZBlurHighlightColor,

    val shimmer: ZShimmerColors,

    val misc : ZMiscGradients

    ) {
    companion object {

        @ZColorBinding("Light Mode")
        val ZLightColorScheme = ZColorScheme(
            text = ZTextColors.light,
            buttons = ZButtonColors.light,
            background = ZBackgroundColors.light,
            navigation = ZNavigationColors.light,
            card = ZCardColors.light,
            inputField = ZInputFieldColors.light,
            tab = ZTabColors.light,
            pills = ZPillsColors.light,
            separator = ZSeparatorColors.light,
            icon = ZIconColors.light,
            status = ZStatusTagColors.light,
            toast = ZToastColors.light,
            note = ZNoteColors.light,
            graphics = ZGraphicsColors.light,
            scrollBar = ZScrollBarColors.light,
            banner = ZBannerColors.light,
            bottomSheet = ZBottomSheetColors.light,
            graph = ZGraphColor.light,
            blurHighlight = ZBlurHighlightColor.light,
            shimmer = ZShimmerColors.light,
            misc = ZMiscGradients.light
        )

        @ZColorBinding("Dark Mode")
        val ZDarkColorScheme = ZColorScheme(
            text = ZTextColors.dark,
            buttons = ZButtonColors.dark,
            background = ZBackgroundColors.dark,
            navigation = ZNavigationColors.dark,
            card = ZCardColors.dark,
            inputField = ZInputFieldColors.dark,
            tab = ZTabColors.dark,
            pills = ZPillsColors.dark,
            separator = ZSeparatorColors.dark,
            icon = ZIconColors.dark,
            status = ZStatusTagColors.dark,
            toast = ZToastColors.dark,
            note = ZNoteColors.dark,
            graphics = ZGraphicsColors.dark,
            scrollBar = ZScrollBarColors.dark,
            banner = ZBannerColors.dark,
            bottomSheet = ZBottomSheetColors.dark,
            graph = ZGraphColor.dark,
            blurHighlight = ZBlurHighlightColor.dark,
            shimmer = ZShimmerColors.dark,
            misc = ZMiscGradients.dark
        )
    }
}
