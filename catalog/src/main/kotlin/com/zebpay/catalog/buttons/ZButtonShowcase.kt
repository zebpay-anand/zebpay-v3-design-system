package com.zebpay.catalog.buttons


import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZButtonShowcase : ShowcaseModule {
    override val route: String
        get() = "buttons"
    override val title: Int
        get() = R.string.zebpay_buttons
    override val subtitle: Int
        get() = R.string.buttons_help_users_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZButtonScreen(title, subtitle, onNavBack = it)
    }
}