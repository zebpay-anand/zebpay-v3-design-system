package com.zebpay.ui.v3.components.molecules.bottomsheet

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationSize
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.setTestingTag


@Composable
fun ZBottomSheetHeader(
    title: String,
    subTitle : String = "",
    modifier: Modifier = Modifier,
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    onBackPress:(()->Unit)?=null,
    radius: Dp = 110.dp,
    maxTitleLines: Int = 1,
    illustrationIcon: @Composable () -> Unit,
) {
    val backgroundColor = ZTheme.color.background.default
    val sheetActionController = LocalSheetCloseController.current
    Box(modifier = modifier) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = (radius.value / 2).dp)
                .clip(
                    RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp),
                )
                .background(backgroundColor)
                .fillMaxWidth(),
        ) {
            val (subTitleRef,titleRef, crossRef, backIconRef) = createRefs()
            if (allowDismiss) {
                CompositionLocalProvider(LocalZIconSize provides 14.dp) {
                    ZToolbarIconAction(
                        tagID = "close",
                        modifier = Modifier.constrainAs(crossRef) {
                            top.linkTo(parent.top, margin = 18.dp)
                            end.linkTo(parent.end, margin = 18.dp)
                        },
                        icon = ZIcons.ic_cross.asZIcon,
                        size = 24.dp,
                        onClick = {
                            sheetActionController.close()
                        },
                    )
                }
            }

            if (allowBack) {
                CompositionLocalProvider(LocalZIconSize provides 14.dp) {
                    ZToolbarIconAction(
                        modifier = Modifier.constrainAs(backIconRef) {
                            top.linkTo(parent.top, margin = 18.dp)
                            start.linkTo(parent.start, margin = 18.dp)
                        },
                        icon = ZIcons.ic_arrow_left.asZIcon,
                        size = 24.dp,
                        tagID = "back",
                        onClick = {
                            onBackPress?.invoke()
                        },
                    )
                }
            }

            Text(
                text = title,
                color = ZTheme.color.text.primary,
                maxLines = maxTitleLines,
                overflow = TextOverflow.Ellipsis,
                style = ZTheme.typography.bodyRegularB1.semiBold(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth().setTestingTag("title").semantics{
                        testTagsAsResourceId = true
                    }
                    .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .constrainAs(titleRef) {
                        if (allowDismiss) {
                            top.linkTo(crossRef.bottom)
                        } else {
                            top.linkTo(parent.top, margin = 38.dp)
                        }
                        if (subTitle.isEmpty()) {
                            bottom.linkTo(parent.bottom)
                        } else {
                            bottom.linkTo(subTitleRef.top)
                        }
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
            )
            if (subTitle.isEmpty().not()) {
                Text(
                    text = subTitle,
                    color = ZTheme.color.text.secondary,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = ZTheme.typography.bodyRegularB3,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
                        .fillMaxWidth()
                        .constrainAs(subTitleRef) {
                            top.linkTo(titleRef.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                )
            }

        }
        HalfCircle(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 1.dp),
            color = backgroundColor,
            circleSize = radius,
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            illustrationIcon()
        }
    }
}

@Composable
fun ZBottomSheetHeader(
    title: String,
    illustrationIcon: ZIcon,
    modifier: Modifier = Modifier,
    subTitle : String = "",
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    illustrationSize: ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
) {

    ZBottomSheetHeader(
        title = title,
        subTitle = subTitle,
        modifier = modifier,
        allowDismiss = allowDismiss,
        allowBack = allowBack,
        onBackPress = onBackPress,
        maxTitleLines = maxTitleLines,
    ) {
        ZIllustrationIcon(
            modifier = Modifier,
            icon = illustrationIcon,
            illustrationSize = illustrationSize,
            illustrationType = illustrationType,
        )
    }
}

@Composable
private fun HalfCircle(
    modifier: Modifier = Modifier,
    circleSize: Dp = 110.dp,
    color: Color = ZTheme.color.background.default,
) {
    Canvas(modifier = modifier.size(circleSize)) {
        drawArc(
            color = color,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = true, // true for pie slice, false for arc
            topLeft = Offset(0f, 0f),
            size = size,
        )
    }
}

@ThemePreviews
@Composable
fun PreviewZBottomSheetHeader() {
    ZSheetPreviewContainer {
        Box(modifier = Modifier.background(color = Color.LightGray)) {
            ZBottomSheetHeader(
                title = "Headline",
                subTitle = "Add funds in your Crypto wallet before you can transfer them to Futures wallet.",
                illustrationIcon = ZIcons.ic_coins.asZIcon,
            )
        }
    }
}
