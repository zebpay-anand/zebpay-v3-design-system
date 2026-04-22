package com.zebpay.ui.v3.components.molecules.toast

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.medium
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.loader.ZCircularLoader
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZTextButtonColor

private data class ZToastColor(
    val containerColor: Color,
    val strokeColor: Color,
    val titleColor: Color,
    val subTitleColor: Color,
    val iconColor: Color,
)

enum class ZToastType {
    Success, Info, Error;
}


@Composable
private fun ZToastType.getColor(): ZToastColor {
    val toastColor = ZTheme.color.toast
    val textColor = ZTheme.color.text

    return when (this) {
        ZToastType.Success -> ZToastColor(
            containerColor = toastColor.defaultBackgroundGreen,
            strokeColor = toastColor.borderGreen,
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = toastColor.iconGreen,
        )

        ZToastType.Info -> ZToastColor(
            containerColor = toastColor.defaultBackgroundYellow,
            strokeColor = toastColor.borderYellow,
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = toastColor.iconYellow,
        )

        ZToastType.Error -> ZToastColor(
            containerColor = toastColor.defaultBackgroundRed,
            strokeColor = toastColor.borderRed,
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = toastColor.iconRed,
        )
    }
}

@Composable
fun ZToast(
    title: String,
    type: ZToastType,
    modifier: Modifier = Modifier,
    onClose: (() -> Unit)? = null,
    message: String? = "",
    showShadow: Boolean = false,
    maxMessageLine: Int = 5,
    prefixIcon: ZIcon = ZIcons.ic_info.asZIcon,
) {
    val toastColor = type.getColor()

    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        shape = ZTheme.shapes.large,
        color = toastColor.containerColor,
        border = BorderStroke(1.dp, toastColor.strokeColor),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                val (infoRef, titleRef, subtitleRef, arrowRef) = createRefs()
                Text(
                    text = title,
                    color = toastColor.titleColor,
                    maxLines = 1,
                    style = ZTheme.typography.bodyRegularB2.semiBold(),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(titleRef) {
                            top.linkTo(parent.top)
                            start.linkTo(infoRef.end, margin = 8.dp)
                            end.linkTo(
                                if (onClose != null)
                                    arrowRef.start else parent.end,
                                margin = 12.dp,
                            )
                            width = Dimension.fillToConstraints
                        },
                )
                if (!message.isNullOrEmpty()) {
                    Text(
                        text = message,
                        color = toastColor.subTitleColor,
                        style = ZTheme.typography.bodyRegularB3,
                        maxLines = maxMessageLine,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.constrainAs(subtitleRef) {
                            top.linkTo(titleRef.bottom, margin = 4.dp)
                            start.linkTo(titleRef.start)
                            end.linkTo(
                                if (onClose != null)
                                    arrowRef.start else parent.end,
                                margin = 12.dp,
                            )
                            width = Dimension.fillToConstraints
                        },
                    )
                }

                Box(
                    modifier = modifier
                        .size(32.dp)
                        .clip(ZTheme.shapes.large)
                        .background(toastColor.iconColor, shape = ZTheme.shapes.large)
                        .constrainAs(infoRef) {
                            top.linkTo(titleRef.bottom)
                            bottom.linkTo(subtitleRef.top)
                            start.linkTo(parent.start)
                        },
                ) {
                    ZIcon(
                        modifier = Modifier.align(Alignment.Center),
                        icon = prefixIcon,
                        contentDescription = null,
                        size = 20.dp,
                        tint = ZTheme.color.icon.singleToneWhite,
                    )
                }

                if (onClose != null) {
                    ZIconButton(
                        modifier = Modifier.constrainAs(arrowRef) {
                            top.linkTo(titleRef.bottom)
                            bottom.linkTo(subtitleRef.top)
                            end.linkTo(parent.end)
                        },
                        icon = ZIcons.ic_cross.asZIcon,
                        onClick = onClose,
                        size = 24.dp,
                        color = toastColor.iconColor,
                    )
                }
            }
        }
    }
}


private data class ZTopToastColor(
    val containerColor: Color,
    val messageColor: Color,
    val iconColor: Color,
)

@Composable
private fun ZToastType.getIcon(): ZIcon {
    return when (this) {
        ZToastType.Success -> ZIcons.ic_tick_circle.asZIcon
        ZToastType.Error -> ZIcons.ic_info.asZIcon
        ZToastType.Info -> ZIcons.ic_info.asZIcon
    }
}

@Composable
private fun ZToastType.getTopColor(): ZTopToastColor {
    val toastColor = ZTheme.color.toast
    val textColor = ZTheme.color.text
    val iconColor = ZTheme.color.icon
    return when (this) {
        ZToastType.Success -> ZTopToastColor(
            containerColor = toastColor.backgroundGreen,
            messageColor = textColor.white,
            iconColor = iconColor.singleToneWhite,
        )

        ZToastType.Error -> ZTopToastColor(
            containerColor = toastColor.backgroundRed,
            messageColor = textColor.white,
            iconColor = iconColor.singleToneWhite,
        )

        ZToastType.Info -> ZTopToastColor(
            containerColor = toastColor.backgroundYellow,
            messageColor = textColor.white,
            iconColor = iconColor.singleToneWhite,
        )
    }
}


@Composable
fun ZTopToast(
    message: String,
    type: ZToastType,
    modifier: Modifier = Modifier,
    showShadow: Boolean = true,
    ctaText: String = "",
    showClose: Boolean = false,
    isProgress: Boolean = false,
    maxMessageLine: Int = 1,
    onClick: (() -> Unit)? = null,
) {
    val toastColor = type.getTopColor()

    CompositionLocalProvider(
        LocalTextStyle provides ZTheme.typography.bodyRegularB3.medium(),
        LocalContentColor provides toastColor.iconColor,
        LocalZTextButtonColor provides ZTextButtonColor.White,
    ) {
        Surface(
            shadowElevation = if (showShadow) 8.dp else 0.dp,
            color = toastColor.containerColor,
            modifier = modifier,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ZCommonLabel(
                    label = message,
                    maxWidth = true,
                    maxLine = maxMessageLine,
                    textAlign = TextAlign.Start,
                    leading = {
                        if(isProgress) {
                            ZCircularLoader(
                                modifier = Modifier
                                    .size(16.dp),
                                strokeWidth = 2.dp,
                                color = toastColor.containerColor.copy(alpha = .4f)
                            )
                        }else {
                            ZIcon(
                                icon = type.getIcon(),
                            )
                        }
                    },
                    trailing = if (ctaText.isEmpty().not()) {
                        {
                            ZTextButton(
                                title = ctaText,
                                onClick = {
                                    onClick?.invoke()
                                },
                                tagId = "toast_cta",
                            )
                        }
                    } else if (showClose) {
                        {
                            ZIconButton(
                                icon = ZIcons.ic_cross.asZIcon,
                                onClick = {
                                    onClick?.invoke()
                                },
                            )
                        }
                    } else null,
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZToast() {
    ZBackgroundPreviewContainer {
        ZToast(
            title = "Toast",
            message = "Subtext",
            type = ZToastType.Success,
            onClose = {},
        )
        ZToast(
            title = "Toast",
            message = "Subtext",
            type = ZToastType.Info,
            onClose = {},

            )
        ZToast(
            title = "Toast",
            message = "Subtext",
            type = ZToastType.Error,
            onClose = {},
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZTopToast() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(vertical = 16.dp)) {
        ZTopToast(
            message = "Send disabled: Instant deposit active",
            type = ZToastType.Info,
        )
        ZTopToast(
            message = "Send disabled: Instant deposit active",
            type = ZToastType.Error,
            showClose = true,
            onClick = {

            },
        )
        ZTopToast(
            message = "Send disabled: Instant deposit active",
            type = ZToastType.Success,
            ctaText = "Okay",
        )
    }
}