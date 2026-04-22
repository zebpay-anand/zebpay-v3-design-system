package com.zebpay.ui.v3.components.molecules.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
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
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.ZImage
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonGradientLabel
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColors
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.border


private data class ZCardColor(
    val containerColor: Color,
    val strokeColor: ZGradient,
    val titleColor: Color,
    val subTitleColor: Color,
    val iconColor: Color,
)

enum class ZInfoCardType {
    Yellow, Red, Blue, Grey , Green
}


@Composable
private fun ZInfoCardType.getColor(): ZCardColor {
    val blurHighlight = ZTheme.color.blurHighlight
    val cardColor = ZTheme.color.card
    val textColor = ZTheme.color.text
    val iconColor = ZTheme.color.icon

    return when (this) {
        ZInfoCardType.Yellow -> ZCardColor(
            containerColor = blurHighlight.yellow,
            strokeColor = cardColor.borderYellow.asZGradient(),
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleTonePrimary,
        )

        ZInfoCardType.Red -> ZCardColor(
            containerColor = blurHighlight.red,
            strokeColor = cardColor.borderRed.asZGradient(),
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleTonePrimary,
        )

        ZInfoCardType.Blue -> ZCardColor(
            containerColor = cardColor.selected,
            strokeColor = cardColor.borderBlue,
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleTonePrimary,
        )
        ZInfoCardType.Grey -> ZCardColor(
            containerColor = cardColor.fill,
            strokeColor = cardColor.border.asZGradient(),
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleTonePrimary,
        )

        ZInfoCardType.Green -> ZCardColor(
            containerColor = blurHighlight.green,
            strokeColor = cardColor.borderGreen.asZGradient(),
            titleColor = textColor.primary,
            subTitleColor = textColor.secondary,
            iconColor = iconColor.singleTonePrimary,
        )
    }
}

@Composable
fun ZInfoCard(
    heading: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtext: String? = "",
    showShadow: Boolean = false,
    maxMessageLine: Int = 5,
    arrowIconCenter:Boolean=false,
    prefixIcon: ZIcon = ZIcons.ic_glass_info.asZIcon,
    cardType : ZInfoCardType = ZInfoCardType.Yellow,
    illustrationType: ZIllustrationType = ZIllustrationType.YELLOW,
){

    val color = cardType.getColor()
    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        color = color.containerColor,
        modifier = modifier
            .drawWithContent {
                // First, draw the original content of the Surface
                drawContent()
                // Then, draw the line on top of the content
                drawLine(
                    brush = color.strokeColor.toTextBrush(), // Your border color
                    start = Offset(0f, 0f), // Start at the top-left corner
                    end = Offset(size.width, 0f), // End at the top-right corner
                    strokeWidth = 2.dp.toPx() // The thickness of your border
                )
            },
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
                    .padding(12.dp)
                    .fillMaxWidth(),
            ) {
                val (infoRef, titleRef, subtitleRef, arrowRef) = createRefs()

                // Title
                Text(
                    text = heading,
                    color = color.titleColor,
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
                        color = color.subTitleColor,
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
                    tint = color.iconColor,
                    size = 20.dp,
                )
            }
        }
    }
}

@Composable
fun ZActionInfoCard(
    modifier: Modifier = Modifier,
    heading: String,
    subtext: String = "",
    maxMessageLine: Int = 5,
    showShadow: Boolean = false,
    primaryText: String,
    prefixIcon: ZIcon = ZIcons.ic_glass_info.asZIcon,
    cardType : ZInfoCardType = ZInfoCardType.Yellow,
    innerPadding:PaddingValues= PaddingValues(16.dp,12.dp),
    illustrationType: ZIllustrationType = ZIllustrationType.YELLOW,
    onClick: () -> Unit,
){
    val color = cardType.getColor()
    Surface(
        shadowElevation = if (showShadow) 8.dp else 0.dp,
        color = color.containerColor,
        modifier = modifier
            .drawWithContent {
                // First, draw the original content of the Surface
                drawContent()
                // Then, draw the line on top of the content
                drawLine(
                    brush = color.strokeColor.toTextBrush(), // Your border color
                    start = Offset(0f, 0f), // Start at the top-left corner
                    end = Offset(size.width, 0f), // End at the top-right corner
                    strokeWidth = 2.dp.toPx() // The thickness of your border
                )
            },
    ) {
        Row(modifier = Modifier.padding(innerPadding)){
            Column(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center){
                ZLabel(
                    label = heading.uppercase(),
                    textStyle = ZTheme.typography.headlineRegularH4.bold(),
                    labelColor = ZTheme.color.text.primary
                )
                AnimatedVisibility(subtext.isNotEmpty()) {
                    ZLabel(
                        label = subtext,
                        textStyle = ZTheme.typography.bodyRegularB4,
                        labelColor = ZTheme.color.text.secondary,
                        maxLine = maxMessageLine
                    )
                }
                BlankHeight(8.dp)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ZTextButton(
                        title = primaryText,
                        onClick = {
                            onClick.invoke()
                        },
                        textButtonColor= ZTextButtonColor.Black,
                        tagId = "blocker_btn_action_$primaryText",
                        horizontalArrangement =Arrangement.spacedBy(4.dp),
                        trailing = {
                            ZIcon(
                                icon = ZIcons.ic_arrow_right.asZIcon
                            )
                        }
                    )
                }
            }
            BlankWidth(12.dp)
            ZIllustrationIcon(
                modifier = Modifier,
                icon = prefixIcon,
                illustrationType = illustrationType,
            )
        }
    }

}

@ThemePreviews
@Composable
private fun ZInfoCardYellowPreview(){
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        ZInfoCard(
            heading = "KYC: Complete Verification",
            subtext = "Verify to trade without any restrictions.",
            onClick = {},
        )

        ZActionInfoCard(
            heading = "KYC: Complete Verification",
            subtext = "Verify to trade without any restrictions.",
            primaryText = "Go Back",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ZInfoCardRedPreview(){
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        ZInfoCard(
            heading = "KYC: Complete Verification",
            subtext = "Verify to trade without any restrictions.",
            prefixIcon = ZIcons.ic_glass_error.asZIcon,
            illustrationType = ZIllustrationType.RED,
            cardType = ZInfoCardType.Red,
            onClick = {},
        )

        ZActionInfoCard(
            heading = "KYC: Complete Verification",
            subtext = "Verify to trade without any restrictions.",
            prefixIcon = ZIcons.ic_glass_error.asZIcon,
            illustrationType = ZIllustrationType.RED,
            cardType = ZInfoCardType.Red,
            primaryText = "Go Back",
            onClick = {},
        )
    }
}

@ThemePreviews
@Composable
private fun ZInfoCardBluePreview(){
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        ZInfoCard(
            heading = "Join 6M+ users on ZebPay",
            subtext = "Signup and start trading today!",
            prefixIcon = ZIcons.ic_zebpay.asZIcon,
            illustrationType = ZIllustrationType.BLUE,
            cardType = ZInfoCardType.Blue,
            onClick = {},
        )
    }
}