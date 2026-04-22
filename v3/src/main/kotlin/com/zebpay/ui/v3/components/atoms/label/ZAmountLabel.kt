package com.zebpay.ui.v3.components.atoms.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.medium
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.tooltip.ZTooltipPopup
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalFiatCurrencyCodeIcon
import com.zebpay.ui.v3.components.utils.LocalZAmountMasked
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZLabelColor

@Composable
fun textToAstrixSize(textSize: TextUnit): Dp {
    return when {
        textSize >= 32.sp -> 26.dp
        textSize >= 28.sp -> 24.dp
        textSize >= 24.sp -> 22.dp
        textSize >= 20.sp -> 18.dp
        textSize >= 16.sp -> 16.dp
        textSize >= 14.sp -> 14.dp
        else -> 12.dp
    }
}

@Composable
fun ZAmountLabel(
    modifier: Modifier = Modifier,
    amount: String,
    amountStyle: TextStyle = LocalTextStyle.current,
    currencyIcon: ZIcon = LocalFiatCurrencyCodeIcon.current,
    masked: Boolean = LocalZAmountMasked.current,
    amountColor: Color = LocalZLabelColor.current,
    iconColor: Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    isApprox: Boolean = false,
    showCurrency: Boolean = true,
    subText:String = "",
    tagID: String = "",
    subTextColor:Color = LocalZLabelColor.current,
    subTextStyle: TextStyle = LocalTextStyle.current,
    overrideIconSize: Boolean=false,
    iconSize:Dp = LocalZIconSize.current,
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.spacedBy(4.dp)
) {
    CompositionLocalProvider(
        LocalTextStyle provides amountStyle,
        LocalZAmountMasked provides masked,
        LocalZLabelColor provides amountColor,
        LocalContentColor provides iconColor,
        LocalZIconSize provides if(overrideIconSize) iconSize else textToAstrixSize(amountStyle.fontSize),
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = horizontalArrangement,
        ) {
            var enableToolTip by remember { mutableStateOf(false) }
            var subTextOverlap by remember { mutableStateOf(false) }
            AnimatedVisibility(isApprox) {
                ZIcon(
                    icon = ZIcons.ic_approximate.asZIcon,
                    tint = iconColor,
                )
            }
            AnimatedVisibility(showCurrency) {
                ZIcon(
                    icon = currencyIcon,
                    tint = iconColor,
                )
            }
            AnimatedContent(
                modifier = Modifier
                    .conditional(subTextOverlap && masked.not()){
                    weight(1f)
                },
                targetState = masked,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(
                        animationSpec = tween(
                            300,
                        ),
                    )
                },
                label = "MaskedAmountAnimation",
            ) { isMasked ->
                Box(
                    modifier = Modifier.height(IntrinsicSize.Max),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    val alpha by animateFloatAsState(
                        targetValue = if (isMasked.not()) 1f else 0f, label = "amount_visibility",
                    )
                    ZTooltipPopup(
                        modifier = Modifier
                            .fillMaxHeight()
                            .alpha(alpha)
                            .conditional(isMasked) {
                                width(1.dp)
                            },
                        enable = enableToolTip && masked.not(),
                        tooltipContent = {
                            AnimatedVisibility(isApprox) {
                                ZIcon(
                                    icon = ZIcons.ic_approximate.asZIcon,
                                    tint = ZTheme.color.icon.singleTonePrimary,
                                    size = 14.dp,
                                )
                            }
                            AnimatedVisibility(showCurrency) {
                                ZIcon(
                                    icon = currencyIcon,
                                    tint = ZTheme.color.icon.singleTonePrimary,
                                    size = 14.dp,
                                )
                            }
                            ZCommonLabel(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                label = amount,
                                tagId = tagID,
                                textStyle = ZTheme.typography.bodyRegularB3.medium(),
                                labelColor = ZTheme.color.text.primary,
                            )
                            AnimatedVisibility(subText.isNotEmpty()) {
                                ZLabel(
                                    modifier = Modifier.padding(horizontal = 2.dp),
                                    tagId = tagID,
                                    label = subText,
                                    textStyle = ZTheme.typography.bodyRegularB3.medium(),
                                    labelColor = ZTheme.color.text.primary,
                                )
                            }
                        },
                    ) {
                        ZLabel(
                            label = amount,
                            tagId = tagID,
                            textAlign = textAlign,
                            modifier = it,
                            hasOverFlowCall = { hasOverFlow->
                                enableToolTip = hasOverFlow
                            }
                        )
                    }
                    if (isMasked) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            modifier = Modifier.fillMaxHeight(),
                        ) {
                            repeat(5) {
                                ZIcon(
                                    icon = ZIcons.ic_asterisk.asZIcon,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(subText.isNotEmpty()) {
                ZLabel(
                    modifier = Modifier.padding(horizontal = 2.dp),
                    label = subText,
                    textStyle = subTextStyle,
                    labelColor = subTextColor,
                    hasOverFlowCall = { hasOverFlow->
                        if(hasOverFlow) {
                            subTextOverlap = true
                        }
                    }
                )
            }
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewAmountVisibilityContainer() {
    ZBackgroundPreviewContainer(modifier = Modifier) {
        var isMasked by remember { mutableStateOf(false) }
        val amount by remember { mutableStateOf("2330.1991") }
        CompositionLocalProvider(
            LocalZAmountMasked provides isMasked,
        ) {
            ZAmountLabel(
                amount = amount,
                amountStyle = ZTheme.typography.displayRegularD3.semiBold(),
                amountColor = ZTheme.color.text.primary,
                iconColor = ZTheme.color.icon.singleTonePrimary,
            )

            ZAmountLabel(
                amount = amount,
                amountStyle = ZTheme.typography.displayRegularD3.semiBold(),
                amountColor = ZTheme.color.text.primary,
                iconColor = ZTheme.color.icon.singleTonePrimary,
                isApprox = true,
            )
        }

        ZOutlineButton(
            title = if (isMasked) "Un-Masked" else "Masked",
            onClick = {
                isMasked = isMasked.not()
            },
            tagId = "",
        )
    }
}