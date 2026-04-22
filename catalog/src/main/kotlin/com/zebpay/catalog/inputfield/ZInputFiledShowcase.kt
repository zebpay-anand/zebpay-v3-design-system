package com.zebpay.catalog.inputfield

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.catalog.header.ZHeaderShowcase
import com.zebpay.ui.catalog.R

object ZInputFiledShowcase : ShowcaseModule {
    override val route: String
        get() = "Pricing InputFields"
    override val title: Int
        get() = R.string.zebpay_input_field
    override val subtitle: Int
        get() = R.string.inputfield_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZInputFieldScreen(ZHeaderShowcase.title, ZHeaderShowcase.subtitle, onNavBack = it)
    }
}