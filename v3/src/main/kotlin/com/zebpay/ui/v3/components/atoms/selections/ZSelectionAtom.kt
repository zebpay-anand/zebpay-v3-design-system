package com.zebpay.ui.v3.components.atoms.selections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.background


@Composable
fun ZRadioButton(
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    size: Dp = 20.dp,
    enabled: Boolean = true,
    readOnly: Boolean = false,
) {
    val iconColors = ZTheme.color.icon
    val borderColor by animateColorAsState(
        targetValue = if (enabled)
            iconColors.singleTonePrimary
        else
            iconColors.singleToneDisable,
        label = "stroke_color",
    )
    val gradient = if (selected && enabled)
        iconColors.dualToneGradient
    else
        iconColors.singleToneDisable.asZGradient()

    Box(
        modifier = modifier
            .clip(CircleShape)
            .safeClickable(enabled = readOnly.not() && onClick != null) {
                onClick?.invoke()
            }
            .size(size)
            .padding(2.dp)
            .border((1.4).dp, borderColor, CircleShape)
            .padding(4.dp),
    ) {
        AnimatedVisibility(
            visible = selected,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient, CircleShape),
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZRadioButton() {
    ZebpayTheme {
        Box(
            Modifier
                .padding(12.dp)
                .background(color = ZTheme.color.background.default),
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZRadioButton(
                    selected = true,
                    onClick = {},
                )
                ZRadioButton(
                    selected = false,
                    onClick = {},
                )
                ZRadioButton(
                    selected = true,
                    enabled = false,
                    onClick = {},
                )
                ZRadioButton(
                    selected = false,
                    enabled = false,
                    onClick = {},
                )
            }
        }
    }
}


enum class CheckboxState {
    /**
     * State that means a component is on
     */
    On,

    /**
     * State that means a component is off
     */
    Off,

    /**
     * State that means that on/off value of a component cannot be determined
     */
    Indeterminate
}

private fun Boolean?.toggleableState() = when (this) {
    true -> CheckboxState.On
    false -> CheckboxState.Off
    else -> CheckboxState.Indeterminate
}


@Composable
fun ZCheckbox(
    modifier: Modifier = Modifier,
    state: Boolean? = null,
    onClick: (() -> Unit)?,
    readOnly: Boolean = false,
    size: Dp = 20.dp,
    enabled: Boolean = true,
) {
    ZCheckbox(
        state = state.toggleableState(),
        modifier = modifier,
        onClick = onClick,
        readOnly = readOnly,
        size = size,
        enabled = enabled,
    )
}

@Composable
fun ZCheckbox(
    state: CheckboxState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    size: Dp = 20.dp,
    enabled: Boolean = true,
) {

    val zThemeColor = ZTheme.color
    val border by animateDpAsState(
        targetValue = if (state == CheckboxState.Off)
            1.dp
        else
            0.dp,
        label = "stroke_width",
    )

    val borderColor by animateColorAsState(
        targetValue = if (enabled)
            zThemeColor.icon.singleTonePrimary
        else
            ZTheme.color.icon.singleToneDisable,
        label = "stroke_color",
    )
    val color = if (state == CheckboxState.Off)
        Color.Transparent.asZGradient()
    else
        if (enabled)
            zThemeColor.icon.dualToneGradient
        else
            zThemeColor.icon.singleToneDisable.asZGradient()

    val extraSmallShape = ZTheme.shapes.extraSmall
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(ZTheme.shapes.extraSmall)
            .clickable(readOnly.not()) {
                onClick?.invoke()
            }
            .size(size)
            .padding(2.dp)
            .background(color, extraSmallShape)
            .conditional(state == CheckboxState.Off) {
                border(border, borderColor, extraSmallShape)
            },
    ) {
        AnimatedVisibility(
            visible = state != CheckboxState.Off,
            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + scaleOut(),
        ) {
            val iconVector = if (state == CheckboxState.Indeterminate)
                ZIcons.ic_subtract
            else
                ZIcons.ic_tick
            Icon(
                painterResource(id = iconVector),
                contentDescription = "",
                tint = zThemeColor.icon.singleToneWhite,
                modifier = Modifier.size(12.dp),
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZCheckbox() {
    ZebpayTheme {
        Box(
            Modifier
                .padding(12.dp)
                .background(color = ZTheme.color.background.default),
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZCheckbox(
                    state = false,
                    enabled = true,
                    onClick = {},
                )

                ZCheckbox(
                    state = CheckboxState.On,
                    enabled = true,
                    onClick = {},
                )

                ZCheckbox(
                    state = CheckboxState.Indeterminate,
                    enabled = true,
                    onClick = {},
                )
                ZCheckbox(
                    state = CheckboxState.Off,
                    enabled = false,
                    onClick = {},
                )

                ZCheckbox(
                    state = CheckboxState.On,
                    enabled = false,
                    onClick = {},
                )
            }
        }
    }
}


@Composable
fun ZFavouritesIcon(
    onClick: () -> Unit,
    selected: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    innerPadding: PaddingValues = PaddingValues(2.dp),
    size: Dp = 20.dp,
) {
    val iconVector = if (selected)
        ZIcons.ic_star_filled
    else
        ZIcons.ic_star

    IconButton(
        modifier = Modifier.size(size),
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        ZGradientIcon(
            modifier = Modifier.padding(innerPadding),
            drawable = iconVector,
            contentDescription = "",
            gradient = ZTheme.color.icon.dualToneGradient,
        )
    }
}


@Composable
fun ZNotificationIcon(
    onClick: () -> Unit,
    selected: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    innerPadding: PaddingValues = PaddingValues(2.dp),
    size: Dp = 20.dp,
) {
    val iconVector = if (selected)
        ZIcons.ic_notification_active
    else
        ZIcons.ic_notifications

    IconButton(
        modifier = Modifier.size(size),
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        ZGradientIcon(
            modifier = Modifier.padding(innerPadding),
            drawable = iconVector,
            contentDescription = "",
            gradient = ZTheme.color.icon.dualToneGradient,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewMiscIcon() {
    ZebpayTheme {
        Column(
            Modifier
                .padding(12.dp)
                .background(color = ZTheme.color.background.default),
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZFavouritesIcon(selected = false, onClick = {})
                ZFavouritesIcon(selected = true, onClick = {})
            }

            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZNotificationIcon(selected = false, onClick = {})
                ZNotificationIcon(selected = true, onClick = {})
            }
        }
    }
}

