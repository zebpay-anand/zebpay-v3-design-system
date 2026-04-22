package com.zebpay.catalog.pills

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZPillShowcase : ShowcaseModule {
    override val route: String
        get() = "pills"
    override val title: Int
        get() = R.string.zebpay_pills
    override val subtitle: Int
        get() = R.string.pill_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZPillScreen(title, subtitle, onNavBack = it)
    }
}