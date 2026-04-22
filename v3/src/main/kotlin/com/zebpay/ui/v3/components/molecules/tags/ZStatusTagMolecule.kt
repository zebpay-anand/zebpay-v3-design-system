package com.zebpay.ui.v3.components.molecules.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.shimmer.ShineHolder
import com.zebpay.ui.v3.components.resource.ZIcons


enum class TagVariant {
    Regular,
    Large
}

enum class ZTagColor {
    Green, Red, Yellow, Blue, Grey, Blue02
}


@Composable
fun ZPnlStatusTag(
    percentageText: String,
    status: PNLStatus,
    modifier: Modifier = Modifier,
) {
    ZStatusTag(
        percentageText,
        colorType = when (status) {
            PNLStatus.Loss -> ZTagColor.Red
            PNLStatus.Neutral -> ZTagColor.Grey
            PNLStatus.Profit -> ZTagColor.Green
        },
        showIcon = true,
        prefixZIcon = when (status) {
            PNLStatus.Loss -> ZIcons.ic_downwards.asZIcon
            PNLStatus.Neutral -> ZIcons.ic_subtract.asZIcon
            PNLStatus.Profit -> ZIcons.ic_upwards.asZIcon
        },
        modifier = modifier,
        animate = false,
    )
}

@ThemePreviews
@Composable
private fun PnlStatusTagPreviews() {
    ZBackgroundPreviewContainer {
        ZPnlStatusTag(
            percentageText = "10.5%",
            status = PNLStatus.Profit,
        )
        ZPnlStatusTag(
            percentageText = "-5.2%",
            status = PNLStatus.Loss,
        )
        ZPnlStatusTag(
            percentageText = "0.0%",
            status = PNLStatus.Neutral,
        )
    }
}

@Composable
fun ZStatusTag(
    primaryText: String,
    modifier: Modifier = Modifier,
    variant: TagVariant = TagVariant.Regular,
    colorType: ZTagColor = ZTagColor.Green,
    prefixZIcon: ZIcon = ZIcons.ic_upwards.asZIcon,
    helperText: String? = null,
    showIcon: Boolean = true,
    animate: Boolean = false,
) {
    val zColor = ZTheme.color
    val textColor = zColor.text.white
    val iconColor = zColor.icon.singleToneWhite

    val textStyle = when (variant) {
        TagVariant.Regular -> ZTheme.typography.bodyRegularB4
        TagVariant.Large -> ZTheme.typography.bodyRegularB3
    }

    val bgColor = when (colorType) {
        ZTagColor.Green -> zColor.status.green
        ZTagColor.Red -> zColor.status.red
        ZTagColor.Yellow -> zColor.status.yellow
        ZTagColor.Blue -> zColor.status.blue
        ZTagColor.Blue02 -> zColor.status.blue02
        ZTagColor.Grey -> zColor.status.grey
    }
    ShineHolder(
        animate = animate,
        modifier = modifier
            .clip(ZTheme.shapes.extraSmall)
            .background(bgColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            if (showIcon) {
                ZIcon(
                    icon = prefixZIcon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp),
                )
                Spacer(modifier = Modifier.width(2.dp))
            }
            Text(
                text = primaryText,
                color = textColor,
                style = textStyle.semiBold(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (helperText.isNullOrBlank().not()) {
                Icon(
                    painter = painterResource(id = ZIcons.ic_separator), contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(14.dp),
                )
                Text(
                    text = helperText.orEmpty(),
                    color = textColor,
                    style = textStyle.semiBold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}



@Composable
fun ZStatusLightTag(
    primaryText: String,
    modifier: Modifier = Modifier,
    variant: TagVariant = TagVariant.Regular,
    colorType: ZTagColor = ZTagColor.Green,
    prefixZIcon: ZIcon = ZIcons.ic_upwards.asZIcon,
    helperText: String? = null,
    showIcon: Boolean = true,
    animate: Boolean = false,
) {
    val zColor = ZTheme.color

    val textStyle = when (variant) {
        TagVariant.Regular -> ZTheme.typography.bodyRegularB4
        TagVariant.Large -> ZTheme.typography.bodyRegularB3
    }

    val bgColor = when (colorType) {
        ZTagColor.Green -> zColor.blurHighlight.green
        ZTagColor.Red -> zColor.blurHighlight.red
        ZTagColor.Yellow -> zColor.blurHighlight.yellow
        ZTagColor.Blue -> zColor.card.selected
        ZTagColor.Blue02 -> zColor.card.selected
        ZTagColor.Grey -> zColor.inputField.backgroundDisabled
    }

    val textColor = when (colorType) {
        ZTagColor.Green -> zColor.text.green
        ZTagColor.Red -> zColor.text.red
        ZTagColor.Yellow -> zColor.text.yellow
        ZTagColor.Blue -> zColor.text.blue
        ZTagColor.Blue02 -> zColor.text.blue
        ZTagColor.Grey -> zColor.text.grey
    }

    val iconColor = when (colorType) {
        ZTagColor.Green -> zColor.icon.singleToneGreen
        ZTagColor.Red -> zColor.icon.singleToneRed
        ZTagColor.Yellow -> zColor.icon.singleToneYellow
        ZTagColor.Blue -> zColor.icon.singleToneBlue
        ZTagColor.Blue02 -> zColor.icon.singleToneBlue
        ZTagColor.Grey -> zColor.icon.singleToneGrey
    }



    ShineHolder(
        animate = animate,
        modifier = modifier
            .clip(ZTheme.shapes.extraSmall)
            .background(bgColor),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.width(4.dp))
            if (showIcon) {
                ZIcon(
                    icon = prefixZIcon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(14.dp),
                )
                Spacer(modifier = Modifier.width(2.dp))
            }
            Text(
                text = primaryText,
                color = textColor,
                style = textStyle.semiBold(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (helperText.isNullOrBlank().not()) {
                Icon(
                    painter = painterResource(id = ZIcons.ic_separator), contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .size(14.dp),
                )
                Text(
                    text = helperText.orEmpty(),
                    color = textColor,
                    style = textStyle.semiBold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewPNLTag() {
    ZBackgroundPreviewContainer {
        Text(
            "Regular",
            style = ZTheme.typography.bodyRegularB4,
            color = ZTheme.color.text.primary,
        )

        ZStatusTag(
            primaryText = "Text 1",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Red,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Yellow,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Blue,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Grey,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        Text(
            "Large",
            style = ZTheme.typography.bodyRegularB4,
            color = ZTheme.color.text.primary,
        )

        ZStatusTag(
            primaryText = "Text 1",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Red,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Yellow,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Blue,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Blue02,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
            variant = TagVariant.Large,
        )

        ZStatusTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Grey,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
            variant = TagVariant.Large,
            animate = true
        )
    }
}


@ThemePreviews
@Composable
private fun PreviewLightStatusTag() {
    ZBackgroundPreviewContainer {
        Text(
            "Regular",
            style = ZTheme.typography.bodyRegularB4,
            color = ZTheme.color.text.primary,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            prefixZIcon = ZIcons.ic_upwards.asZIcon,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Red,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Yellow,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Blue,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )

        ZStatusLightTag(
            primaryText = "Text 1",
            helperText = "Text 2",
            colorType = ZTagColor.Grey,
            prefixZIcon = ZIcons.ic_downwards.asZIcon,
        )
    }
}