package com.zebpay.catalog.tab


import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZTabShowcase : ShowcaseModule {
    override val route: String
        get() = "tab"
    override val title: Int
        get() = R.string.zebpay_tab
    override val subtitle: Int
        get() = R.string.tab_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZTabBarScreen(title, subtitle, onNavBack = it)
    }
}