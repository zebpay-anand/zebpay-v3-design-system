package com.zebpay.ui.v3.components.molecules.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.loader.ZCircularLoader
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZPrimaryButtonColor
import com.zebpay.ui.v3.components.utils.background

enum class ZPrimaryColor { Blue, Green, Red }

@Immutable
class ZPrimaryButtonColors(
    val backgroundColor: ZGradient,
    val iconColor: Color,
    val trackColor: Color,
    val foregroundColor: Color,
    val textColor: Color,
)


@Composable
private fun disableColor() = ZPrimaryButtonColors(
    backgroundColor = ZTheme.color.buttons.primary.fillDisable.asZGradient(),
    iconColor = ZTheme.color.icon.singleToneWhite,
    trackColor = ZTheme.color.icon.singleToneWhite,
    foregroundColor = ZTheme.color.navigation.top.onGradientIconBg,
    textColor = ZTheme.color.buttons.primary.textDisable,
)

@Composable
fun getPrimaryColors(color: ZPrimaryColor): ZPrimaryButtonColors {
    return when (color) {
        ZPrimaryColor.Blue -> ZPrimaryButtonColors(
            backgroundColor = ZTheme.color.buttons.primary.fillActive,
            iconColor = ZTheme.color.icon.singleToneWhite,
            trackColor = ZTheme.color.icon.singleToneWhite,
            foregroundColor = ZTheme.color.navigation.top.onGradientIconBg,
            textColor = ZTheme.color.buttons.primary.textActive,
        )

        ZPrimaryColor.Green -> ZPrimaryButtonColors(
            backgroundColor = ZTheme.color.buttons.primary.fillGreen,
            iconColor = ZTheme.color.icon.singleToneWhite,
            trackColor = ZTheme.color.icon.singleToneWhite,
            foregroundColor = ZTheme.color.navigation.top.onGradientIconBg,
            textColor = ZTheme.color.buttons.primary.textActive,
        )

        ZPrimaryColor.Red -> ZPrimaryButtonColors(
            backgroundColor = ZTheme.color.buttons.primary.fillRed,
            iconColor = ZTheme.color.icon.singleToneWhite,
            trackColor = ZTheme.color.icon.singleToneWhite,
            foregroundColor = ZTheme.color.navigation.top.onGradientIconBg,
            textColor = ZTheme.color.buttons.primary.textActive,
        )
    }
}

@Composable
fun ZPrimaryButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tagId: String,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = ZTheme.shapes.small,
    buttonColor: ZPrimaryColor = LocalZPrimaryButtonColor.current,
    buttonSize: ZButtonSize = ZButtonSize.Big,
    paddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    hasOverFlowCall: (Boolean)->Unit = {},
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val buttonColors = if (enabled) getPrimaryColors(buttonColor) else disableColor()
    val haptic = LocalHapticFeedback.current
    Box(
        modifier = modifier
            .clip(shape)
            .testTag("button-$tagId")
            .clickable(enabled, role = Role.Button) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .background(
                    gradient = buttonColors.backgroundColor,
                    shape = shape,
                )
                .padding(paddingValues)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (isLoading) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f),
                ) {
                    ZCircularLoader(
                        modifier = Modifier.size(28.dp),
                        color = buttonColors.trackColor,
                        trackColor = buttonColors.foregroundColor,
                    )
                }
            } else {
                ZCommonLabel(
                    label = title.uppercase(),
                    labelColor = buttonColors.textColor,
                    leadingColor = buttonColors.iconColor,
                    trailingColor = buttonColors.iconColor,
                    textStyle = buttonSize.getTextStyle(),
                    maxWidth = true,
                    leading = leading,
                    trailing = trailing,
                    hasOverFlowCall = hasOverFlowCall
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZPrimaryButton() {
    ZBackgroundPreviewContainer {
        ZPrimaryButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            isLoading = true,
            buttonColor = ZPrimaryColor.Blue,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZPrimaryButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            buttonColor = ZPrimaryColor.Blue,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZPrimaryButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            buttonColor = ZPrimaryColor.Red,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
        ZPrimaryButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            buttonColor = ZPrimaryColor.Green,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )

        ZPrimaryButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            buttonColor = ZPrimaryColor.Green,
            enabled = false,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_arrow_left.asZIcon,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                )
            },
        )
    }
}