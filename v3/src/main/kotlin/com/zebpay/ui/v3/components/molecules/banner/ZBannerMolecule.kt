package com.zebpay.ui.v3.components.molecules.banner

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.background


enum class SummaryType {
    BIG,
    SMALL
}

@Composable
fun ZBannerInfoContainer(
    modifier: Modifier = Modifier,
    innerPaddingValues: PaddingValues = PaddingValues(16.dp),
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .clip(ZTheme.shapes.large)
            .border(1.dp, color = ZTheme.color.card.border, ZTheme.shapes.large)
            .background(gradient = ZTheme.color.banner.default, ZTheme.shapes.large),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(innerPaddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            content = content,
        )
    }
}


@Composable
fun ZSummaryBanner(
    amount: String,
    currency: String,
    modifier: Modifier = Modifier,
    summary: String? = null,
    summaryMaxLine: Int = 5,
    summaryType: SummaryType = SummaryType.BIG,
) {
    val textStyle = when (summaryType) {
        SummaryType.BIG -> ZTheme.typography.displayRegularD1.bold()
        SummaryType.SMALL -> ZTheme.typography.displayRegularD3.bold()
    }

    val currencyStyle = when (summaryType) {
        SummaryType.BIG -> ZTheme.typography.bodyRegularB1
        SummaryType.SMALL -> ZTheme.typography.bodyRegularB2
    }
    val innerPaddingValues = when (summaryType) {
        SummaryType.BIG -> PaddingValues(12.dp)
        SummaryType.SMALL -> PaddingValues(8.dp)
    }

    ZBannerInfoContainer(
        modifier = modifier,
        innerPaddingValues = innerPaddingValues,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = amount,
                style = textStyle,
                color = ZTheme.color.text.primary,
            )
            Text(
                text = currency,
                style = currencyStyle,
                color = ZTheme.color.text.secondary,
            )
        }
        if (!summary.isNullOrEmpty()) {
            Text(
                text = summary,
                style = ZTheme.typography.bodyRegularB3,
                color = ZTheme.color.text.primary,
                maxLines = summaryMaxLine,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@Composable
fun ZAppGraphicBanner(
    modifier: Modifier = Modifier,
    headline: String,
    maxLine: Int = 1,
    primaryCta: (@Composable () -> Unit)? = null,
    secondaryCta: (@Composable () -> Unit)? = null,
) {
    ZBannerInfoContainer(
        modifier = modifier,
        innerPaddingValues = PaddingValues(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = headline,
                    maxLines = maxLine,
                    overflow = TextOverflow.Ellipsis,
                    style = ZTheme.typography.bodyRegularB2.semiBold(),
                    color = ZTheme.color.text.primary,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    primaryCta?.invoke()
                    if (secondaryCta != null) {
                        Icon(
                            painter = painterResource(id = ZIcons.ic_separator),
                            contentDescription = null,
                            tint = ZTheme.color.icon.singleToneSecondary,
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .size(16.dp),
                        )
                        secondaryCta.invoke()
                    }
                }
            }
            ZIllustrationIcon(
                icon = ZIcons.ic_info.asZIcon,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZSummaryBanner() {
    ZBackgroundPreviewContainer {
        ZSummaryBanner(
            amount = "0.00",
            currency = "INR",
            summary = "Text",
        )

        ZSummaryBanner(
            amount = "0.00",
            currency = "INR",
            summary = "Text",
            summaryType = SummaryType.SMALL,
        )
    }
}

@ThemePreviews
@Composable
fun PreviewZAppGraphicBanner() {
    ZBackgroundPreviewContainer {
        ZAppGraphicBanner(
            headline = "Heading",
            primaryCta = {
                ZTextButton(
                    title = "CTA 01",
                    onClick = {},
                    tagId = "",
                    textButtonColor = ZTextButtonColor.Black,
                )
            },
            secondaryCta = {
                ZTextButton(
                    title = "CTA 02",
                    onClick = {},
                    tagId = "",
                    textButtonColor = ZTextButtonColor.Black,
                )
            },
        )
    }
}