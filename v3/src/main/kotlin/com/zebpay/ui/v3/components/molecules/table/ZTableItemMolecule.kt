package com.zebpay.ui.v3.components.molecules.table

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZLabelColor

@Composable
fun ZTableItemRow(
    modifier: Modifier = Modifier,
    isHeader: Boolean = false,
    showCenter: Boolean = true,
    skipCenterWeight: Boolean=false,
    leftWeight: Float =1f,
    rightWeight: Float =1f,
    centerWeight: Float =1f,
    backgroundColor : Color =  ZTheme.color.card.fill,
    textStyle: TextStyle = ZTheme.typography.bodyRegularB3,
    leftContent: @Composable RowScope.() -> Unit = {},
    centerContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
    innerPaddingValues: PaddingValues= PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
) {
    val finalTextStyle = if (isHeader) textStyle.semiBold() else textStyle
    CompositionLocalProvider(
        LocalTextStyle provides finalTextStyle,
        LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
        LocalZLabelColor provides ZTheme.color.text.primary,
    ) {
        Box(
            modifier
                .conditional(isHeader) {
                    background(color = backgroundColor)
                },
        ) {
            Row(
                modifier = modifier
                    .padding(innerPaddingValues),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement,
            ) {
                Row(
                    modifier = Modifier.weight(leftWeight), content = leftContent,
                    horizontalArrangement = Arrangement.Start,
                )
                if (showCenter) {
                    Row(
                        modifier = Modifier.conditional(skipCenterWeight.not()) {
                            weight(centerWeight)
                        }, content = centerContent,
                        horizontalArrangement = Arrangement.Center,
                    )
                }
                Row(
                    modifier = Modifier.weight(rightWeight), content = rightContent,
                    horizontalArrangement = Arrangement.End,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZTableItem() {
    ZBackgroundPreviewContainer(
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column(
            modifier = Modifier
                .clip(shape = ZTheme.shapes.large)
                .border(1.dp, color = ZTheme.color.card.border, shape = ZTheme.shapes.large),
        ) {
            ZTableItemRow(
                showCenter = true,
                isHeader = true,
                leftContent = {
                    ZCommonLabel(
                        label = "Left",
                        trailing = {
                            ZIcon(
                                icon = ZIcons.ic_info.asZIcon,
                            )
                        },
                    )
                },
                centerContent = {
                    ZCommonLabel(
                        label = "Center",
                    )
                },
                rightContent = {
                    ZCommonLabel(
                        label = "Right",
                        trailing = {
                            ZIcon(
                                icon = ZIcons.ic_info.asZIcon,
                            )
                        },
                    )
                },
            )

            for (index in 0..3) {
                ZTableItemRow(
                    showCenter = true,
                    leftContent = {
                        ZCommonLabel(
                            label = "Left",
                        )
                    },
                    centerContent = {
                        ZCommonLabel(
                            label = "Center",
                        )
                    },
                    rightContent = {
                        ZCommonLabel(
                            label = "Right",
                        )
                    },
                )
                ZHorizontalDivider()
            }
        }
    }
}