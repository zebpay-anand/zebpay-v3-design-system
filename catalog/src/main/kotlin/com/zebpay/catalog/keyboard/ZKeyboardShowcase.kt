package com.zebpay.catalog.keyboard

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZKeyboardShowcase : ShowcaseModule {
    override val route: String
        get() = "keyboard"
    override val title: Int
        get() = R.string.zebpay_keyboard
    override val subtitle: Int
        get() = R.string.keyboard_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZKeyboardScreen(title, subtitle, onNavBack = it)
    }
}