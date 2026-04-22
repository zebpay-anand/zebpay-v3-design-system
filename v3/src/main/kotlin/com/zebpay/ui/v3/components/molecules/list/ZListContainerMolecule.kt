package com.zebpay.ui.v3.components.molecules.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
fun ZLazyListContainer(
    modifier: Modifier = Modifier,
    shape: Shape = ZTheme.shapes.large,
    background: Color = ZTheme.color.card.fill,
    border:Color =  ZTheme.color.card.border,
    contentPaddingValues: PaddingValues = PaddingValues(0.dp),
    content: LazyListScope.() -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .clip(shape)
            .background(color = background)
            .border(1.dp, color = border, shape),
        contentPadding = contentPaddingValues,
        content = content,
    )
}

@Composable
fun ZColumnListContainer(
    modifier: Modifier = Modifier,
    shape: Shape = ZTheme.shapes.large,
    background: Color = ZTheme.color.card.fill,
    border:Color =  ZTheme.color.card.border,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    onClick: (() -> Unit)? = null,
    tagId: String = "",
    verticalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(0.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .clip(shape)
            .background(color = background)
            .border(1.dp, color = border, shape)
            .safeClickable(enabled = onClick != null, tagId = tagId) {
                onClick?.invoke()
            },
    ) {
        Column(
            modifier = Modifier.padding(innerPaddingValues),
            verticalArrangement = verticalArrangement,
            content = content,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZLazyListContainer() {
    ZBackgroundPreviewContainer {
        ZLazyListContainer {
            items(3) {
                ZListItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    },
                    leftContent = {
                        ZListLabelItem(
                            label = "Left",
                            leading = {
                                ZIcon(
                                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                    tint = Color.Unspecified,
                                )
                            },
                            trailing = {
                                ZIconButton(
                                    icon = ZIcons.ic_info.asZIcon,
                                    onClick = {},
                                )
                            },
                        )
                    },
                    rightContent = {
                        ZIcon(
                            icon = ZIcons.ic_arrow_right.asZIcon,
                        )
                    },
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZColumnListContainer() {
    ZBackgroundPreviewContainer {
        ZColumnListContainer {
            repeat(3) {
                ZListItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                    },
                    leftContent = {
                        ZListLabelItem(
                            label = "Left",
                            leading = {
                                ZIcon(
                                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                    tint = Color.Unspecified,
                                )
                            },
                            trailing = {
                                ZIconButton(
                                    icon = ZIcons.ic_info.asZIcon,
                                    onClick = {},
                                )
                            },
                        )
                    },
                    rightContent = {
                        ZIcon(
                            icon = ZIcons.ic_arrow_right.asZIcon,
                        )
                    },
                )
                ZHorizontalDivider()
            }
        }
    }
}