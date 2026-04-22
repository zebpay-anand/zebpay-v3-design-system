package com.zebpay.ui.v3.components.molecules.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonGradientLabel
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZTextButtonColor


enum class ZTextButtonColor { Blue, White, Black, Grey }

enum class ZButtonSize {
    Big, Medium
}


internal val ZButtonSize.iconSize: Dp
    get() = when (this) {
        ZButtonSize.Big -> 20.dp
        ZButtonSize.Medium -> 16.dp
    }

@Composable
internal fun ZButtonSize.getTextStyle() = when (this) {
    ZButtonSize.Big -> ZTheme.typography.ctaC1
    ZButtonSize.Medium -> ZTheme.typography.ctaC2
}

@Immutable
class ZTextButtonColors(
    val iconColor: ZGradient,
    val textColor: ZGradient,
)


@Composable
private fun getTextButtonColor(color: ZTextButtonColor): ZTextButtonColors {
    return when (color) {
        ZTextButtonColor.Blue -> ZTextButtonColors(
            iconColor = ZTheme.color.buttons.tertiary.textActive,
            textColor = ZTheme.color.buttons.tertiary.textActive,
        )

        ZTextButtonColor.White -> ZTextButtonColors(
            iconColor = ZTheme.color.icon.singleToneWhite.asZGradient(),
            textColor = ZTheme.color.buttons.tertiary.textWhite.asZGradient(),
        )

        ZTextButtonColor.Black -> ZTextButtonColors(
            iconColor = ZTheme.color.icon.singleTonePrimary.asZGradient(),
            textColor = ZTheme.color.buttons.tertiary.textBlack.asZGradient(),
        )

        ZTextButtonColor.Grey -> ZTextButtonColors(
            iconColor = ZTheme.color.icon.singleToneDisable.asZGradient(),
            textColor = ZTheme.color.buttons.tertiary.textDisable.asZGradient(),
        )
    }
}


@Composable
fun ZTextButton(
    title: String,
    onClick: () -> Unit,
    tagId: String,
    modifier: Modifier = Modifier,
    innerPaddingContent: PaddingValues = PaddingValues(horizontal = 4.dp),
    buttonSize: ZButtonSize = ZButtonSize.Medium,
    textButtonColor: ZTextButtonColor = LocalZTextButtonColor.current,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    enabled: Boolean = true,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val buttonColors = getTextButtonColor(if (enabled) textButtonColor else ZTextButtonColor.Grey)
    val haptic = LocalHapticFeedback.current
    Box(
        modifier = modifier
            .testTag(tagId)
            .clickable(enabled, role = Role.Button) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .padding(innerPaddingContent)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(
                LocalZIconSize provides buttonSize.iconSize,
                LocalZGradientColor provides buttonColors.iconColor,
                LocalTextStyle provides buttonSize.getTextStyle(),
            ) {
                ZCommonGradientLabel(
                    label = title.uppercase(),
                    labelColor = buttonColors.textColor,
                    maxWidth = false,
                    horizontalArrangement = horizontalArrangement,
                    leading = leading,
                    trailing = trailing,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewMediumZTextButton() {
    ZBackgroundPreviewContainer {
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Medium,
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Medium,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Medium,
            textButtonColor = ZTextButtonColor.Blue,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Medium,
            textButtonColor = ZTextButtonColor.Black,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Medium,
            enabled = false,
            textButtonColor = ZTextButtonColor.Black,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
    }
}


@ThemePreviews
@Composable
fun PreviewBigZTextButton() {
    ZBackgroundPreviewContainer {
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Big,
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Big,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Big,
            textButtonColor = ZTextButtonColor.Blue,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Big,
            textButtonColor = ZTextButtonColor.Black,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZTextButton(
            title = "CTA",
            onClick = {},
            tagId = "text-btn",
            buttonSize = ZButtonSize.Big,
            enabled = false,
            textButtonColor = ZTextButtonColor.Black,
            leading = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZGradientIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
    }
}