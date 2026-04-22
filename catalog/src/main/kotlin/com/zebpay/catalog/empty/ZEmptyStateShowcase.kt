package com.zebpay.catalog.empty

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZEmptyStateShowcase : ShowcaseModule {
    override val route: String
        get() = "empty-state"
    override val title: Int
        get() = R.string.zebpay_empty_state
    override val subtitle: Int
        get() = R.string.empty_state_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZEmptyStateScreen(title, subtitle, onNavBack = it)
    }
}