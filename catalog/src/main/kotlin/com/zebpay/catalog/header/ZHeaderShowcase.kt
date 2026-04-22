package com.zebpay.catalog.header

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZHeaderShowcase : ShowcaseModule {
    override val route: String
        get() = "header"
    override val title: Int
        get() = R.string.zebpay_header
    override val subtitle: Int
        get() = R.string.header_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZHeaderScreen(title, subtitle, onNavBack = it)
    }
}