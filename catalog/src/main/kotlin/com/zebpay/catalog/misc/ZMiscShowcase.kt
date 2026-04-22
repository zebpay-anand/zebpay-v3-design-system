package com.zebpay.catalog.misc

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZMiscShowcase : ShowcaseModule {
    override val route: String
        get() = "misc"
    override val title: Int
        get() = R.string.zebpay_misc
    override val subtitle: Int
        get() = R.string.misc_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZMiscScreen(title, subtitle, onNavBack = it)
    }
}