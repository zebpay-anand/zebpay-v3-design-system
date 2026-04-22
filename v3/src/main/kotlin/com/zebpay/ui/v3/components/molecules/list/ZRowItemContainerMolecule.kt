package com.zebpay.ui.v3.components.molecules.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZAmountLabel
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
fun ZRowItemContainer(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    tagId: String = "",
    leftContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
    innerPaddingValues: PaddingValues = PaddingValues(vertical = 0.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
) {
    Column(
        modifier = modifier
            .safeClickable(enabled = onClick != null, tagId = tagId) {
                onClick?.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPaddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
        ) {
            Row(modifier = Modifier.weight(1f), content = leftContent)
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
                content = rightContent,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZRowItemContainer() {
    ZBackgroundPreviewContainer {
        ZRowItemContainer(
            leftContent = {
                ZCommonLabel(
                    "Bitcoin",
                    trailing = {
                        ZIconButton(
                            onClick = {},
                            icon = ZIcons.ic_arrow_right.asZIcon,
                        )
                    },
                )
            },
            rightContent = {
                ZAmountLabel(
                    amount = "7,500",
                    amountStyle = ZTheme.typography.bodyRegularB3.semiBold(),
                )
            },
        )
    }
}