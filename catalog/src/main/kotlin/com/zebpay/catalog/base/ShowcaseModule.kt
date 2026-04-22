package com.zebpay.catalog.base

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable

interface ShowcaseModule {
    val route: String

    @get:StringRes
    val title: Int

    @get:StringRes
    val subtitle: Int
    val content: @Composable (onNavBack: () -> Unit) -> Unit
}