package com.zebpay.ui.v3.components.templates.pnlcard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.tags.PNLStatus
import com.zebpay.ui.v3.components.molecules.tags.PnLTag
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
private fun ZPnLGradientBox(
    modifier: Modifier = Modifier,
    gradientColor: Color,
    shape: Shape = ZTheme.shapes.large,
    background: Color = ZTheme.color.card.fill,
    tagId: String = "",
    click: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .defaultMinSize(minWidth = 175.dp)
            .clip(shape)
            .border(1.dp, color = ZTheme.color.card.border, shape)
            .background(color = background, shape = shape)
            .safeClickable(enabled = click != null, tagId = tagId) {
                click?.invoke()
            }
            .drawWithContent {
                val radius = sqrt(size.width.pow(1.8f) + size.height.pow(1.9f))
                drawRect(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            gradientColor,      // Start color at the center (bottom-left)
                            Color.Transparent,   // Fade to transparent at the radius edge
                        ),
                        center = Offset(0f, size.height), // Gradient origin: bottom-left corner
                        radius = radius, // Spread the gradient across the calculated radius
                    ),
                    size = size,
                )
                drawContent()
            },
    ) {
        content()
    }
}

@Composable
fun ZPnLTrendCoinCard(
    modifier: Modifier = Modifier,
    icon: ZIcon,
    coinName: String,
    coinAliasName: String,
    amount: String,
    percentage: String,
    pnlStatus: PNLStatus,
    isFavourite: Boolean = false,
    isXpress: Boolean = false,
    currencyIcon: ZIcon,
    tagId: String = "",
    click: (() -> Unit)? = null,
) {
    val gradientColor = when (pnlStatus) {
        PNLStatus.Profit -> {
            ZTheme.color.graphics.glowGreen
        }
        PNLStatus.Loss -> {
            ZTheme.color.graphics.glowRed
        }
        else -> {
            Color.Transparent
        }
    }
    ZPnLGradientBox(
        modifier = modifier,
        gradientColor = gradientColor,
        tagId = tagId,
        click = click,
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center,
                ) {
                    ZIcon(
                        icon = icon,
                        tint = Color.Unspecified,
                        size = 38.dp,
                    )
                    if (isXpress) {
                        ZIcon(
                            icon = ZIcons.ic_xpress.asZIcon,
                            tint = Color.Unspecified,
                            size = 38.dp,
                        )
                    }
                }
                Column {
                    CompositionLocalProvider(
                        LocalZLabelColor provides ZTheme.color.text.primary,
                        LocalTextStyle provides ZTheme.typography.bodyRegularB3.semiBold(),
                        LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
                        LocalZIconSize provides 14.dp,
                    ) {
                        ZCommonLabel(
                            label = coinName,
                            trailing = if (isFavourite) {
                                {
                                    ZGradientIcon(
                                        icon = ZIcons.ic_star_filled.asZIcon,
                                    )
                                }
                            } else null,
                        )
                    }
                    CompositionLocalProvider(
                        LocalTextStyle provides ZTheme.typography.bodyRegularB4,
                        LocalZLabelColor provides ZTheme.color.text.secondary,
                    ) {
                        ZCommonLabel(
                            label = coinAliasName,
                        )
                    }
                }
            }
            BlankHeight(8.dp)
            CompositionLocalProvider(
                LocalTextStyle provides ZTheme.typography.bodyRegularB2.semiBold(),
                LocalZLabelColor provides ZTheme.color.text.primary,
            ) {
                ZCommonLabel(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    label = amount,
                    leading = {
                        ZIcon(
                            icon = currencyIcon,
                            size = 14.dp,
                            tint = ZTheme.color.icon.singleTonePrimary,
                        )
                    },
                )
            }
            PnLTag(
                primaryText = percentage,
                status = pnlStatus,
            )
        }
    }

}


@ThemePreviews
@Composable
private fun PreviewPortfolioPnLGradientBox() {
    ZBackgroundPreviewContainer {

        ZPnLTrendCoinCard(
            coinName = "BTC",
            coinAliasName = "Bitcoin",
            amount = "1000.00",
            percentage = "15.00",
            pnlStatus = PNLStatus.Profit,
            icon = ZIcons.ic_bitcoin_btc.asZIcon,
            currencyIcon = ZIcons.ic_currency_inr.asZIcon
        )

        ZPnLTrendCoinCard(
            coinName = "BTC",
            coinAliasName = "Bitcoin",
            amount = "1000.00",
            percentage = "15.00",
            pnlStatus = PNLStatus.Loss,
            isFavourite = true,
            isXpress = true,
            icon = ZIcons.ic_bitcoin_btc.asZIcon,
            currencyIcon = ZIcons.ic_currency_usd.asZIcon
        )
    }
}