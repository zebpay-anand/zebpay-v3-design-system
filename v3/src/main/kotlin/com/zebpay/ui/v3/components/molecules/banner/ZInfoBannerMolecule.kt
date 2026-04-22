package com.zebpay.ui.v3.components.molecules.banner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

private data class ZInfoBannerColor(
    val containerColor: Color,
    val strokeColor: Color,
    val titleColor: Color,
    val iconColor: Color,
)

enum class ZInfoCard {
    Yellow, Red, Default
}


@Composable
private fun ZInfoCard.getColor(): ZInfoBannerColor {
    val toastColor = ZTheme.color.toast
    val noteColor = ZTheme.color.note
    val textColor = ZTheme.color.text
    val iconColor = ZTheme.color.icon

    return when (this) {
        ZInfoCard.Yellow -> ZInfoBannerColor(
            containerColor = toastColor.defaultBackgroundYellow,
            strokeColor = toastColor.defaultBackgroundYellow,
            titleColor = textColor.primary,
            iconColor = iconColor.singleToneGreen,
        )

        ZInfoCard.Red -> ZInfoBannerColor(
            containerColor = ZTheme.color.graphics.glowRed,
            strokeColor = ZTheme.color.graphics.glowRed,
            titleColor = textColor.red,
            iconColor = iconColor.singleToneRed,
        )

        ZInfoCard.Default -> ZInfoBannerColor(
            containerColor = noteColor.backgroundDefault,
            strokeColor = noteColor.borderDefault,
            titleColor = noteColor.textYellow,
            iconColor = iconColor.singleToneYellow,
        )
    }
}


@Composable
fun ZInfoBanner(
    title: String,
    modifier: Modifier = Modifier,
    type: ZInfoCard,
    showShadow: Boolean = false,
    onClick: () -> Unit,
) {
    val infoColor = type.getColor()

    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        shape = ZTheme.shapes.large,
        color = infoColor.containerColor,
        border = BorderStroke(1.dp, infoColor.strokeColor),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.clickable {
                onClick.invoke()
            }.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ZIcon(
                        icon = ZIcons.ic_info.asZIcon,
                        contentDescription = null,
                        tint = infoColor.iconColor,
                    )
                    Text(
                        text = title,
                        color = infoColor.titleColor,
                        style = ZTheme.typography.bodyRegularB2.semiBold(),
                        modifier = Modifier.weight(1f),
                    )
                    ZIcon(
                        icon = ZIcons.ic_arrow_right.asZIcon,
                        contentDescription = null,
                        tint = ZTheme.color.icon.singleTonePrimary,
                    )
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZNote() {
    ZBackgroundPreviewContainer {
        ZInfoBanner(
            title = "Text",
            type = ZInfoCard.Yellow,
            onClick = {},
        )

        ZInfoBanner(
            title = "Text",
            type = ZInfoCard.Red,
            onClick = {},
        )

        ZInfoBanner(
            title = "Text",
            type = ZInfoCard.Default,
            onClick = {},
        )
    }
}