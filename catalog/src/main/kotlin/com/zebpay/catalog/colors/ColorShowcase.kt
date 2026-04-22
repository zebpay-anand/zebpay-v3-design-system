package com.zebpay.catalog.colors

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ColorShowcase : ShowcaseModule {
    override val route: String
        get() = "Color_Palette"
    override val title: Int
        get() = R.string.color_palette

    override val subtitle: Int
        get() = R.string.utilize_color_description

    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ColorPaletteScreen(title, subtitle, onNavBack = it)
    }
}