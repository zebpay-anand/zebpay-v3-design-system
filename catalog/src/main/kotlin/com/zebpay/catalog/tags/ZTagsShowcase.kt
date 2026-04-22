package com.zebpay.catalog.tags

import androidx.compose.runtime.Composable
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.ui.catalog.R

object ZTagsShowcase : ShowcaseModule {
    override val route: String
        get() = "tags"
    override val title: Int
        get() = R.string.zebpay_tag
    override val subtitle: Int
        get() = R.string.tag_showcase_description


    override val content: @Composable (onNavBack: () -> Unit) -> Unit = {
        ZTagScreen(title, subtitle, onNavBack = it)
    }
}