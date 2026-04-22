package com.zebpay.ui.v3.components.molecules.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.resource.ZIcons


@Composable
fun <T> ZGridColumn(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    horizontalSpacing: Dp = 8.dp,
    verticalSpacing: Dp = 12.dp,
    skipFillRow: Boolean = false,
    itemContent: @Composable RowScope.(T) -> Unit,
) {
    Column(modifier = modifier) {
        items.chunked(columns).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
                modifier = Modifier.padding(vertical = verticalSpacing),
            ) {
                rowItems.forEach { item ->
                    itemContent(item)
                }
                if (skipFillRow.not()) {
                    // Fill empty cells if row isn't full
                    repeat(columns - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZGridColumn() {
    val items = arrayListOf(
        "Available Balance",
        "Locked Balance",
        "QuickTrade",
    )
    ZBackgroundPreviewContainer {
        ZColumnListContainer {
            CompositionLocalProvider(
                LocalTextStyle provides ZTheme.typography.bodyRegularB3,
                LocalContentColor provides Color.Unspecified,
            ) {
                ZGridColumn(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    items = items,
                    columns = 2,
                ) {
                    ZCommonLabel(
                        modifier = Modifier.weight(1f),
                        labelColor = ZTheme.color.text.secondary,
                        label = it,
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_circle_tick_filled.asZIcon,
                            )
                        },
                    )
                }
            }
        }
    }
}
