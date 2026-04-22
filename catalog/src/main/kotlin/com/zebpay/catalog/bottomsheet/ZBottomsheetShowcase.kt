package com.zebpay.catalog.bottomsheet


import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZBottomSheetShowcase : ShowcaseModule {
    override val route: String
        get() = "sheet"
    override val title: Int
        get() = R.string.zebpay_sheet
    override val subtitle: Int
        get() = R.string.sheet_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZBottomSheetScreen(title, subtitle, onNavBack = it)
    }
}