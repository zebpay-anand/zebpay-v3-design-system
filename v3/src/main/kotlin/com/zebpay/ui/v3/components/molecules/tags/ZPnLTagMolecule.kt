package com.zebpay.ui.v3.components.molecules.tags

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.extension.toDecimalString
import com.zebpay.ui.v3.components.resource.ZIcons


sealed interface PNLStatus {
    data object Profit : PNLStatus
    data object Loss : PNLStatus
    data object Neutral : PNLStatus
}

val Double.asPnLTagStatus
    get() = when {
        this == 0.0 -> PNLStatus.Neutral
        this > 0.0 -> PNLStatus.Profit
        else -> PNLStatus.Loss
    }

@Composable
@ReadOnlyComposable
private fun PNLStatus.colors(): PNLTagColor {
    return when (this) {
        PNLStatus.Profit -> PNLTagColor(
            icon = ZTheme.color.icon.singleToneGreen,
            text = ZTheme.color.text.green,
        )

        PNLStatus.Loss -> PNLTagColor(
            icon = ZTheme.color.icon.singleToneRed,
            text = ZTheme.color.text.red,
        )

        PNLStatus.Neutral -> PNLTagColor(
            icon = ZTheme.color.icon.singleToneGrey,
            text = ZTheme.color.text.grey,
        )
    }
}

private data class PNLTagColor(
    val icon: Color,
    val text: Color,
)

private val PNLStatus.icon: Int
    get() = when (this) {
        PNLStatus.Loss -> ZIcons.ic_downwards
        PNLStatus.Neutral -> ZIcons.ic_subtract
        PNLStatus.Profit -> ZIcons.ic_upwards
    }


@Composable
fun PnLTag(
    percentage: Double,
    modifier: Modifier = Modifier,
    helperText: String? = null,
    showIcon: Boolean = true,
) {

    PnLTag(
        percentage.asPnLTagStatus,
        percentage = percentage,
        modifier = modifier,
        helperText = helperText,
        showIcon = showIcon,
    )
}


@Composable
fun PnLTag(
    status: PNLStatus,
    modifier: Modifier = Modifier,
    percentage: Double? = null,
    helperText: String? = null,
    primaryText: String? = null,
    showIcon: Boolean = true,
) {

    val pnlTagColor = status.colors()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(status.icon),
                contentDescription = null,
                tint = pnlTagColor.icon,
                modifier = Modifier.size(14.dp),
            )
            Spacer(modifier = Modifier.width(2.dp))
        }
        Text(
            text = if (primaryText.isNullOrBlank()) percentage.toDecimalString()
                .plus("%") else primaryText,
            color = pnlTagColor.text,
            style = ZTheme.typography.bodyRegularB3.semiBold(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (helperText.isNullOrBlank().not()) {
            Icon(
                painter = painterResource(id = ZIcons.ic_separator), contentDescription = null,
                tint = pnlTagColor.icon,
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .size(14.dp),
            )
            Text(
                text = helperText.orEmpty(),
                color = pnlTagColor.text,
                style = ZTheme.typography.bodyRegularB3.semiBold(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewPNLTag() {
    ZBackgroundPreviewContainer {
        PnLTag(percentage = 99.0)
        PnLTag(status = PNLStatus.Profit, percentage = 99.0)
        PnLTag(status = PNLStatus.Loss, percentage = 99.0)
        PnLTag(status = PNLStatus.Neutral, percentage = 99.0)
        PnLTag(status = PNLStatus.Profit, percentage = 99.0, helperText = "90H")
        PnLTag(status = PNLStatus.Loss, percentage = 99.0, helperText = "90H")
        PnLTag(status = PNLStatus.Neutral, percentage = 99.0, helperText = "90H")
    }
}


