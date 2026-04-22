package com.zebpay.ui.v3.components.atoms.label

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.tags.ZStatusTag
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalFiatCurrencyCodeIcon

@Composable
fun ZIndicatorLabel(
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    isBold: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .size(4.dp, 16.dp)
                .background(color = color, shape = ZTheme.shapes.full)
                .clip(ZTheme.shapes.full),
        )
        val finalTextStyle = if (isBold) {
            textStyle.bold()
        } else {
            textStyle
        }
        Text(
            text = label,
            style = finalTextStyle,
            color = ZTheme.color.text.primary,
        )
    }
}


@Composable
fun ZAmountIndicatorLabel(
    label: String,
    color: Color,
    amount: String,
    modifier: Modifier = Modifier,
    currencyIcon: ZIcon = LocalFiatCurrencyCodeIcon.current,
    textStyle: TextStyle = LocalTextStyle.current,
    isBold: Boolean = true,
    status: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(
            modifier = Modifier
                .width(4.dp)
                .fillMaxHeight()
                .padding(vertical = 6.dp)
                .background(color = color, shape = ZTheme.shapes.full)
                .clip(ZTheme.shapes.full),
        )
        val finalTextStyle = if (isBold) {
            textStyle.semiBold()
        } else {
            textStyle
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
        ) {
            ZCommonLabel(
                label = label,
                trailing = status,
            )
            CompositionLocalProvider(
                LocalTextStyle provides finalTextStyle,
            ) {
                ZAmountLabel(
                    currencyIcon = currencyIcon,
                    amount = amount,
                    horizontalArrangement = Arrangement.spacedBy(1.dp)
                )
            }
        }

    }
}


@ThemePreviews
@Composable
fun PreviewZAmountIndicatorLabel() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            ZIndicatorLabel(
                "FIAT",
                color = ZTheme.color.graphics.glowRed,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZIndicatorLabel() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            ZAmountIndicatorLabel(
                label = "Fiat",
                isBold = false,
                color = ZTheme.color.graphics.glowRed,
                amount = "1,10,000.00",
                currencyIcon = ZIcons.ic_currency_inr.asZIcon,
            )

            ZAmountIndicatorLabel(
                label = "Crypto",
                isBold = false,
                color = ZTheme.color.graphics.glowRed,
                amount = "70,000.00",
                currencyIcon = ZIcons.ic_currency_inr.asZIcon,
                status = {
                    ZStatusTag(
                        primaryText = "50.00 %",
                        showIcon = true,
                    )
                },
            )
        }
    }
}