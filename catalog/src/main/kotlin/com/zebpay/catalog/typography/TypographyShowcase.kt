package com.zebpay.catalog.typography

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object TypographyShowcase : ShowcaseModule {
    override val route: String
        get() = "Typography"
    override val title: Int
        get() = R.string.typography
    override val subtitle: Int
        get() = R.string.list_of_text_variations_description

    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        TypographyScreen(title, subtitle, onNavBack = it)
    }
}