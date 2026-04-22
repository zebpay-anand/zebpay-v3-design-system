package com.zebpay.ui.designsystem.v3.utils

import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun NoRippleContent(content: @Composable () -> Unit) {
    CompositionLocalProvider(value = LocalRippleConfiguration provides null, content = content)
}