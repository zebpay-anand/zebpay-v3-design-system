package com.zebpay.ui.v3.components.atoms.pills

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.border


/**
 * A composable function that displays a pill.
 *
 * @param text The text to display in the pill.
 * @param modifier The modifier to apply to the pill.
 * @param isSelected True if the pill is selected, false otherwise.
 * @param enable True if the pill is enable, false otherwise.
 * @param showSelectedIndicator True if the pill indicator need to show, false otherwise.
 * @param selectedIndicatorIcon ZIcon for selected prefix icon, ic_tick default.
 * @param iconOnly True if only Icon need to show.
 * @param visibleIcon ZIcon to display in the pill.
 * @param onCheckedChange The callback to invoke when the pill is clicked.
 */
@Composable
fun ZPill(
    modifier: Modifier = Modifier,
    text: String = "",
    isSelected: Boolean = false,
    enable: Boolean = true,
    showSelectedIndicator: Boolean = false,
    selectedIndicatorIcon: ZIcon = ZIcons.ic_tick.asZIcon,
    iconOnly: Boolean = false,
    icon: ZIcon = ZIcons.ic_star.asZIcon,
    innerPaddingValues: PaddingValues= PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    onCheckedChange: () -> Unit,
) {
    val pillColors = ZTheme.color.pills
    val iconColors = ZTheme.color.icon
    val borderWidth by animateDpAsState(
        targetValue = when {
            enable.not() -> 1.dp
            isSelected -> 0.dp
            else -> 1.dp
        },
        label = "stroke",
    )

    val bgColor: ZGradient = when {
        enable.not() -> pillColors.fillDisabled.asZGradient()
        isSelected -> pillColors.fillActive
        else -> pillColors.fillDefault.asZGradient()
    }

    val borderColor: ZGradient = when {
        enable.not() -> pillColors.boarderDisabled.asZGradient()
        isSelected -> pillColors.borderActive
        else -> pillColors.borderDefault.asZGradient()
    }

    val textColor =  when {
        enable.not() -> pillColors.textDisabled
        isSelected -> pillColors.textActive
        else -> pillColors.textDefault
    }
    val selectedIndicatorIconColor by animateColorAsState(
        targetValue = when {
            enable.not() -> iconColors.singleToneDisable
            isSelected -> iconColors.singleToneWhite
            else -> iconColors.singleTonePrimary
        },
        label = "text_color",
    )

    val iconColor by animateColorAsState(
        targetValue = when {
            enable.not() -> iconColors.singleToneDisable
            isSelected -> iconColors.singleToneWhite
            else -> iconColors.singleToneSecondary
        },
        label = "text_color",
    )
    val shapes = ZTheme.shapes
    val style = when {
        enable.not() -> ZTheme.typography.bodyRegularB3
        isSelected -> ZTheme.typography.bodyRegularB3.semiBold()
        else -> ZTheme.typography.bodyRegularB3
    }
    Box(
        modifier
            .clip(ZTheme.shapes.full)
            .background(gradient = bgColor, shape = shapes.full)
            .conditional(enable.not() || isSelected.not()) {
                border(borderWidth, borderColor, shapes.full)
            }
            .safeClickable(enable) {
                onCheckedChange.invoke()
            }
            .padding(innerPaddingValues),
    ) {
       if (iconOnly){
           CompositionLocalProvider(
               LocalContentColor provides iconColor, LocalZIconSize provides 18.dp,
           ) {
               Row(modifier = Modifier.size(width = 34.dp, height = 24.dp),
                   horizontalArrangement = Arrangement.Center,
                   verticalAlignment = Alignment.CenterVertically) {
                   ZIcon(icon)
               }
           }
       }else {
           CompositionLocalProvider(
               LocalTextStyle provides style,
               LocalContentColor provides selectedIndicatorIconColor,
               LocalZIconSize provides 14.dp,
           ) {
               ZCommonLabel(
                   modifier = Modifier.align(Alignment.Center),
                   label = text,
                   labelColor = textColor,
                   leading = if (isSelected && showSelectedIndicator) {
                       { ZIcon(selectedIndicatorIcon) }
                   } else {
                       null
                   },
               )
           }
       }
    }
}

@ThemePreviews
@Composable
private fun PreviewZPillAtom() {
    ZBackgroundPreviewContainer {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ZPill(
                isSelected = false,
                iconOnly = true,
                onCheckedChange = {},
            )
            ZPill(
                text = "All Coins",
                isSelected = true,
                onCheckedChange = {},
            )
            ZPill(
                text = "New",
                isSelected = false,
                onCheckedChange = {},
            )
            ZPill(
                text = "Top Gainers",
                enable = false,
                onCheckedChange = {},
            )
        }
    }
}