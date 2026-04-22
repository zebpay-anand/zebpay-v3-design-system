package com.zebpay.ui.designsystem.v3.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme

@Composable
fun ZBackgroundPreviewContainer(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(8.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    ZebpayTheme {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB3,
            LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
        ) {
            Surface(
                color = ZTheme.color.background.default,
                content = {
                    Column(
                        modifier = Modifier.padding(innerPaddingValues),
                        verticalArrangement = verticalArrangement,
                    ) {
                        content()
                    }
                },
                modifier = modifier,
            )
        }
    }
}