package com.zebpay.ui.v3.components.molecules.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationSize
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
private fun ZInfoCardType.getBorderColor(): ZGradient {
    return when (this) {
        ZInfoCardType.Yellow -> ZTheme.color.card.borderYellow.asZGradient()
        ZInfoCardType.Red -> ZTheme.color.card.borderRed.asZGradient()
        ZInfoCardType.Blue -> ZTheme.color.card.borderBlue
        ZInfoCardType.Grey -> ZTheme.color.card.border.asZGradient()
        ZInfoCardType.Green -> ZTheme.color.card.borderGreen.asZGradient()
    }
}


@Composable
fun ZCardRow(
    heading: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtext: String? = "",
    showShadow: Boolean = false,
    maxMessageLine: Int = 5,
    arrowIconCenter:Boolean=false,
    type: ZInfoCardType = ZInfoCardType.Grey,
    prefixIcon: ZIcon = ZIcons.ic_info.asZIcon,
    illustrationSize:ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.RED,
    innerPaddingValues: PaddingValues = PaddingValues(12.dp)
) {

    val cardColors = ZTheme.color.card
    val textColors = ZTheme.color.text

    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        shape = ZTheme.shapes.large,
        color = cardColors.fill,
        border = BorderStroke(width = 1.dp, brush = type.getBorderColor().toTextBrush()),
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .clickable { onClick.invoke() }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(innerPaddingValues)
                    .fillMaxWidth(),
            ) {
                val (infoRef, titleRef, subtitleRef, arrowRef) = createRefs()

                // Title
                Text(
                    text = heading,
                    color = textColors.primary,
                    maxLines = 1,
                    style = ZTheme.typography.bodyRegularB2.semiBold(),
                    modifier = Modifier
                        .constrainAs(titleRef) {
                            start.linkTo(infoRef.end, margin = 8.dp)
                            end.linkTo(arrowRef.start, margin = 8.dp)

                            if (!subtext.isNullOrEmpty()) {
                                top.linkTo(parent.top)
                            } else {
                                // Center vertically with icon
                                top.linkTo(infoRef.top)
                                bottom.linkTo(infoRef.bottom)
                            }

                            width = Dimension.fillToConstraints
                        },
                )

                // Subtext (conditionally shown)
                if (!subtext.isNullOrEmpty()) {
                    Text(
                        text = subtext,
                        color = textColors.secondary,
                        style = ZTheme.typography.bodyRegularB3,
                        maxLines = maxMessageLine,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.constrainAs(subtitleRef) {
                            top.linkTo(titleRef.bottom, margin = 4.dp)
                            start.linkTo(titleRef.start)
                            end.linkTo(arrowRef.start, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        },
                    )
                }

                // Icon
                ZIllustrationIcon(
                    modifier = Modifier.constrainAs(infoRef) {
                        start.linkTo(parent.start)

                        if (!subtext.isNullOrEmpty()) {
                            top.linkTo(titleRef.top)
                        } else {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    },
                    icon = prefixIcon,
                    illustrationSize=illustrationSize,
                    illustrationType = illustrationType,
                )

                // Arrow
                ZIcon(
                    modifier = Modifier.constrainAs(arrowRef) {
                        end.linkTo(parent.end)

                        if (arrowIconCenter) {
                            top.linkTo(parent.top,margin = 4.dp)
                            bottom.linkTo(parent.bottom)
                        }else if (!subtext.isNullOrEmpty()) {
                            top.linkTo(titleRef.bottom)
                            bottom.linkTo(subtitleRef.top)
                        } else {
                            top.linkTo(titleRef.top)
                            bottom.linkTo(titleRef.bottom)
                        }
                    },
                    icon = ZIcons.ic_arrow_right.asZIcon,
                    tint = ZTheme.color.icon.singleTonePrimary,
                    size = 20.dp,
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZCardRow() {
    ZBackgroundPreviewContainer {

        ZCardRow(
            heading = "Heading",
            onClick = {},
        )
        ZCardRow(
            heading = "Heading",
            subtext = "Subtext",
            onClick = {},
            type = ZInfoCardType.Red
        )
        ZCardRow(
            heading = "Heading",
            subtext = "These card components are used to display key information in a structured format. They support icons, headings, subtext, and navigation, making them suitable for lists, settings, or informational sections.",
            onClick = {},
            illustrationType = ZIllustrationType.YELLOW,
            type = ZInfoCardType.Yellow
        )
    }
}