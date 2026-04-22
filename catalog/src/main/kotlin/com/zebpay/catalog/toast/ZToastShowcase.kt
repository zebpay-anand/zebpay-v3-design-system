package com.zebpay.catalog.toast

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZToastShowcase : ShowcaseModule {
    override val route: String
        get() = "toast"
    override val title: Int
        get() = R.string.zebpay_toast
    override val subtitle: Int
        get() = R.string.toast_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZToastScreen(title, subtitle, onNavBack = it)
    }
}