package com.zebpay.catalog.dropdown

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZDropdownShowcase : ShowcaseModule {
    override val route: String
        get() = "dropdown"
    override val title: Int
        get() = R.string.zebpay_dropdown
    override val subtitle: Int
        get() = R.string.dropdown_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZDropdownScreen(title, subtitle, onNavBack = it)
    }
}