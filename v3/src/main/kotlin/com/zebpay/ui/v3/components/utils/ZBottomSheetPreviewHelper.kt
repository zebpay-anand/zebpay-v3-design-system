package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme

@Composable
fun ZSheetPreviewContainer(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalSheetCloseController provides ZBottomController {
        },
    ) {
        ZebpayTheme {
            content.invoke()
        }
    }
}