package com.zebpay.ui.v3.components.molecules.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.dottedVerticalLine
import com.zebpay.ui.designsystem.v3.utils.medium
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.selections.ZCheckbox
import com.zebpay.ui.v3.components.atoms.selections.ZRadioButton
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.molecules.tags.ZStatusTag
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import com.zebpay.ui.v3.components.utils.LocalZListItemBold
import com.zebpay.ui.v3.components.utils.LocalZListItemReadOnly
import com.zebpay.ui.v3.components.utils.LocalZListItemRight
import com.zebpay.ui.v3.components.utils.background

@Composable
private fun ZListItemRow(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    onChecked: (() -> Unit)? = null,
    isSubRow: Boolean = false,
    selectionWidget: Boolean = false,
    checked: Boolean? = false,
    isMultiSelect: Boolean = false,
    tagID: String="",
    showBackground: Boolean = false,
    readOnly: Boolean = LocalZListItemReadOnly.current,
    isBoldLabel: Boolean = LocalZListItemBold.current,
    textStyle: TextStyle = LocalTextStyle.current,
    leftContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
    showDivider: Boolean = false,
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
) {
    val bgColor = ZTheme.color.banner.default
    Box(
        modifier
            .safeClickable(onClick != null, tagId = tagID) {
                onClick?.invoke()
            }
            .conditional(isSubRow) {
                padding(start = 28.dp)
            }
            .conditional(showBackground && isSubRow.not()) {
                background(gradient = bgColor)
            },
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides textStyle,
            LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
            LocalZListItemBold provides isBoldLabel,
            LocalZListItemReadOnly provides readOnly,
        ) {
            Row(
                modifier = modifier
                    .padding(innerPaddingValues),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = horizontalArrangement,
            ) {
                if (selectionWidget) {
                    when (isMultiSelect) {
                        true -> {
                            ZCheckbox(
                                state = checked,
                                onClick = onChecked,
                                readOnly = readOnly,
                            )
                        }

                        false -> {
                            ZRadioButton(
                                selected = checked ?: false,
                                onClick = onChecked,
                                readOnly = readOnly,
                            )
                        }
                    }
                }
                Row(modifier = Modifier.weight(1f), content = leftContent)
                CompositionLocalProvider(
                    LocalZListItemRight provides true,
                ) {
                    Row(modifier = Modifier.weight(1f), content = rightContent,
                        horizontalArrangement = Arrangement.End)
                }
            }
        }
        if (showDivider) {
            ZHorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }
}


@Composable
fun ZListItemRow(
    modifier: Modifier = Modifier,
    onChecked: () -> Unit,
    checked: Boolean? = false,
    isMultiSelect: Boolean = false,
    showBackground: Boolean = false,
    readOnly: Boolean = LocalZListItemReadOnly.current,
    isBoldLabel: Boolean = LocalZListItemBold.current,
    textStyle: TextStyle = LocalTextStyle.current,
    leftContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
) {
    ZListItemRow(
        modifier = modifier,
        onChecked = onChecked,
        checked = checked,
        selectionWidget = true,
        readOnly = readOnly,
        isBoldLabel = isBoldLabel,
        textStyle = textStyle,
        showBackground = showBackground,
        isMultiSelect = isMultiSelect,
        leftContent = leftContent,
        rightContent = rightContent,
        innerPaddingValues = innerPaddingValues,
        horizontalArrangement = horizontalArrangement,
    )
}


@Composable
fun ZListItemRow(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isSubRow: Boolean = false,
    showBackground: Boolean = false,
    tagID: String="",
    readOnly: Boolean = LocalZListItemReadOnly.current,
    isBoldLabel: Boolean = LocalZListItemBold.current,
    textStyle: TextStyle = LocalTextStyle.current,
    leftContent: @Composable RowScope.() -> Unit = {},
    rightContent: @Composable RowScope.() -> Unit = {},
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
) {
    ZListItemRow(
        tagID=tagID,
        modifier = modifier,
        isSubRow = isSubRow,
        onClick = onClick,
        isMultiSelect = false,
        readOnly = readOnly,
        isBoldLabel = isBoldLabel,
        textStyle = textStyle,
        showBackground = showBackground,
        leftContent = leftContent,
        rightContent = rightContent,
        innerPaddingValues = innerPaddingValues,
        horizontalArrangement = horizontalArrangement,
    )
}

@Composable
fun ZListLabelItem(
    modifier: Modifier = Modifier,
    label: String,
    tagID: String ="",
    underline: Boolean = false,
    isDashUnderline: Boolean = false,
    onLabelClick: (() -> Unit)? = null,
    subText: String? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    status: @Composable (() -> Unit)? = null,
    leadingIconColor: Color = LocalContentColor.current,
    trailingIconColor: Color = LocalContentColor.current,
    iconSize: Dp = LocalZIconSize.current,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
) {

    val isRight = LocalZListItemRight.current
    val isBold = LocalZListItemBold.current
    val isReadOnly = LocalZListItemReadOnly.current

    val textColor = if (isBold && isReadOnly.not()) {
        ZTheme.color.text.primary
    } else {
        ZTheme.color.text.secondary
    }
    val subTextColor = if (isReadOnly.not()) {
        ZTheme.color.text.primary
    } else {
        ZTheme.color.text.secondary
    }
    val labelStyle = if (isBold) {
        if (isRight)
            LocalTextStyle.current.semiBold()
        else LocalTextStyle.current.medium()
    } else {
        LocalTextStyle.current
    }

    val subTextStyle = if (isBold && isRight) {
        LocalTextStyle.current.medium()
    } else {
        LocalTextStyle.current
    }
    val leadingSize = if (isRight) 12.dp else 20.dp
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides subTextStyle,
            LocalZLabelColor provides subTextColor,
            LocalContentColor provides trailingIconColor,
            LocalZIconSize provides iconSize,
        ) {
            ZCommonLabel(
                label = label,
                labelColor = textColor,
                tagId = tagID,
                textStyle = labelStyle,
                onLabelClick = onLabelClick,
                underLine = underline,
                dashLine = isDashUnderline,
                leading = if (leading != null) {
                    {
                        CompositionLocalProvider(
                            LocalContentColor provides leadingIconColor,
                            LocalZIconSize provides leadingSize,
                        ) {
                            leading()
                        }
                    }
                } else null,
                trailing = if (!subText.isNullOrEmpty()) {
                    {
                        ZCommonLabel(subText, trailing = trailing)
                    }
                } else if (trailing != null) {
                    {
                        trailing()
                    }
                } else {
                    null
                },
            )
            if (status != null) {
                status()
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZListItem() {
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Read Only",
            color = ZTheme.color.text.primary,
            style = ZTheme.typography.bodyRegularB3.semiBold(),
        )
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB3,
        ) {
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
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
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )

            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                leftContent = {
                    ZListLabelItem(
                        label = "Left",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                tint = Color.Unspecified,
                            )
                        },
                        status = {
                            ZStatusTag(
                                primaryText = "Text1",
                                showIcon = false,
                            )
                        },
                    )
                },
                rightContent = {
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )

            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                isBoldLabel = true,
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
                        subText = "Subtext",
                    )
                },
                rightContent = {
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                textStyle = ZTheme.typography.bodyRegularB2,
                readOnly = true,
                isBoldLabel = true,
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
                        subText = "Subtext",
                    )
                },
                rightContent = {
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewSelectionItem() {
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Selections - Icon, Radio & Checkbox",
            color = ZTheme.color.text.primary,
            style = ZTheme.typography.bodyRegularB3.semiBold(),
        )
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB3,
        ) {
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                isBoldLabel = true,
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
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                isBoldLabel = true,
                leftContent = {
                    ZListLabelItem(
                        label = "Left",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                tint = Color.Unspecified,
                            )
                        },
                        status = {
                            ZStatusTag(
                                primaryText = "Text",
                                showIcon = false,
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
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                isBoldLabel = true,
                onChecked = {},
                isMultiSelect = false,
                leftContent = {
                    ZListLabelItem(
                        label = "Left",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                tint = Color.Unspecified,
                            )
                        },
                        status = {
                            ZStatusTag(
                                primaryText = "Text",
                                showIcon = false,
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
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                isBoldLabel = true,
                onChecked = {},
                isMultiSelect = true,
                leftContent = {
                    ZListLabelItem(
                        label = "Left",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                tint = Color.Unspecified,
                            )
                        },
                        status = {
                            ZStatusTag(
                                primaryText = "Text",
                                showIcon = false,
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


@ThemePreviews
@Composable
fun PreviewSubSectionItem() {
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Sub Section Rows",
            color = ZTheme.color.text.primary,
            style = ZTheme.typography.bodyRegularB3.semiBold(),
        )
        ZColumnListContainer {
            var expand by remember { mutableStateOf(true) }
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
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
                                icon = ZIcons.ic_arrow_down.asZIcon,
                                onClick = {
                                    expand = expand.not()
                                },
                            )
                        },
                    )
                },
                rightContent = {
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )
            AnimatedVisibility(expand) {
                Column(
                    modifier = Modifier
                        .dottedVerticalLine(
                            color = ZTheme.color.separator.solidDefault,
                            dashHeight = 20f,
                            offsetStart = Pair(60f, 12f),
                            offsetEnd = Pair(60f, 18f),
                        ),
                ) {
                    for (index in 0..4) {
                        ZListItemRow(
                            modifier = Modifier.fillMaxWidth(),
                            isSubRow = true,
                            leftContent = {
                                ZListLabelItem(
                                    label = "Left",
                                )
                            },
                            rightContent = {
                                ZListLabelItem(
                                    label = "Right",
                                    leading = {
                                        ZIcon(
                                            icon = ZIcons.ic_currency_inr.asZIcon,
                                        )
                                    },
                                    subText = "Subtext",
                                )
                            },
                        )
                    }
                }
            }
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                showBackground = true,
                isBoldLabel = true,
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
                    ZListLabelItem(
                        label = "Right",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_currency_inr.asZIcon,
                            )
                        },
                        subText = "Subtext",
                    )
                },
            )
        }
    }
}




