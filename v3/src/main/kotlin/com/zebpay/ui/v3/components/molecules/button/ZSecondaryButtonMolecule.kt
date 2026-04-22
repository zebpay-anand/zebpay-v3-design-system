package com.zebpay.ui.v3.components.molecules.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZColors
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonGradientLabel
import com.zebpay.ui.v3.components.atoms.loader.ZCircularLoader
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZOutlineButtonColor
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.border

enum class ZOutlineColor { Blue }

@Immutable
class ZOutlineButtonColors(
    val backgroundColor: ZGradient,
    val borderColor: ZGradient,
    val iconColor: ZGradient,
    val trackColor: Color,
    val foregroundColor: Color,
    val textColor: ZGradient,
)

@Composable
private fun disableColor() = ZOutlineButtonColors(
    backgroundColor = ZTheme.color.buttons.secondary.fillDisable.asZGradient(),
    borderColor = ZTheme.color.buttons.secondary.borderDisable.asZGradient(),
    iconColor = ZTheme.color.icon.singleToneDisable.asZGradient(),
    trackColor = ZTheme.color.icon.singleToneDisable,
    foregroundColor = ZTheme.color.icon.singleToneWhite,
    textColor = ZTheme.color.buttons.secondary.textDisable.asZGradient(),
)

@Composable
fun getOutlineColor(color: ZOutlineColor): ZOutlineButtonColors {
    return when (color) {
        ZOutlineColor.Blue -> ZOutlineButtonColors(
            backgroundColor = ZTheme.color.buttons.secondary.fillActive.asZGradient(),
            borderColor = ZTheme.color.buttons.secondary.borderActive,
            iconColor = ZTheme.color.buttons.secondary.textActive,
            trackColor = ZTheme.color.icon.singleToneWhite,
            foregroundColor = ZColors.Primary03,
            textColor = ZTheme.color.buttons.secondary.textActive,
        )
    }
}


@Composable
fun ZOutlineButton(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tagId: String,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = ZTheme.shapes.small,
    buttonColor: ZOutlineColor = LocalZOutlineButtonColor.current,
    buttonSize: ZButtonSize = ZButtonSize.Big,
    paddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    hasOverFlowCall: (Boolean)->Unit = {},
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
) {
    val buttonColors = if (enabled) getOutlineColor(buttonColor) else disableColor()
    val haptic = LocalHapticFeedback.current
    Box(
        modifier = modifier
            .clip(shape)
            .testTag(tagId)
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
                .border(
                    width = 1.dp,
                    gradient = buttonColors.borderColor,
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
                ZCommonGradientLabel(
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
fun PreviewZOutlineButton() {
    ZBackgroundPreviewContainer {
        ZOutlineButton(
            title = "CTA",
            onClick = {},
            tagId = "Primary-Btn",
            isLoading = true,
            buttonColor = ZOutlineColor.Blue,
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

        ZOutlineButton(
            title = "CTA",
            onClick = {},
            tagId = "outline-Btn",
            isLoading = false,
            buttonColor = ZOutlineColor.Blue,
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

        ZOutlineButton(
            title = "CTA",
            onClick = {},
            tagId = "outline-Btn",
            buttonColor = ZOutlineColor.Blue,
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
            enabled = false,
        )
    }
}