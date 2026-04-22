package com.zebpay.ui.v3.components.molecules.bottomsheet


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.medium
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationSize
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.border
import com.zebpay.ui.v3.components.utils.setTestingTag


/**
 * A shape that represents a rectangle with rounded top corners and a full circle
 * overlapping its top edge. The bottom corners are squared.
 *
 * @param circleRadius The radius of the overlapping circle.
 * @param circleOffsetY The vertical offset of the circle's center from the top edge
 *                      of the *visible* rectangular body. A positive value moves the
 *                      circle upwards, making less of it overlap. A value of 0 would
 *                      center the circle on the top edge. A negative value would move
 *                      it downwards, more into the body. For the image, it seems the
 *                      circle's center is slightly above the top flat edge of the cutout.
 *                      Let's define it as the distance from the *top of the bounding box*
 *                      to the circle's center.
 * @param topCornerRadius The radius for the top-left and top-right corners of the main body,
 *                        where it doesn't interact with the circle.
 */
class TopCircleOverlapShape(
    private val circleRadius: Dp,
    private val circleCenterYFromTop: Dp, // How far down from the shape's top bound the circle's center is
    private val topCornerRadius: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val circleRadiusPx = with(density) { circleRadius.toPx() }
        val topCornerRadiusPx = with(density) { topCornerRadius.toPx() }
        val circleCenterXPx = size.width / 2
        val circleCenterYPx = with(density) { circleCenterYFromTop.toPx() }

        // The Y coordinate where the flat part of the rectangular body's top edge would be
        // if there were no circle. This is where the concave arcs will meet the circle.
        // This needs to be below the circle's center by some amount for the overlap.
        // Let's assume the top "cut" into the rectangle is at `circleCenterYPx`.
        val topBodyEdgeY = circleCenterYPx

        // Calculate intersection points of the circle with the topBodyEdgeY line
        // (x - cx)^2 + (y - cy)^2 = r^2
        // (x - cx)^2 = r^2 - (y - cy)^2
        // We are interested in the intersection with the *bottom* of the circle for the cutout path.
        // The top edge of the rectangle will be where y = topBodyEdgeY.
        // The path will effectively create a rectangle and then union it with a circle path.
        // This is much easier than calculating complex intersection arcs.

        val rectPath = Path().apply {
            // Start at top-left, after corner
            moveTo(0f, topBodyEdgeY + topCornerRadiusPx)
            arcTo(
                rect = Rect(0f, topBodyEdgeY, 2 * topCornerRadiusPx, topBodyEdgeY + 2 * topCornerRadiusPx),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Top edge to where circle would start cutout (approximate for straight line)
            // This line will be effectively "behind" the circle.
            lineTo(size.width - topCornerRadiusPx, topBodyEdgeY)
            arcTo(
                rect = Rect(size.width - 2 * topCornerRadiusPx, topBodyEdgeY, size.width, topBodyEdgeY + 2 * topCornerRadiusPx),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(size.width, size.height) // Bottom-right (sharp)
            lineTo(0f, size.height)        // Bottom-left (sharp)
            close()
        }

        val circlePath = Path().apply {
            addOval(
                Rect(
                    center = Offset(circleCenterXPx, circleCenterYPx),
                    radius = circleRadiusPx
                )
            )
        }

        // Combine the two paths using Union.
        // The rectangular part will effectively be the "base" and the circle will be added on top.
        val combinedPath = Path.combine(
            operation = PathOperation.Union,
            path1 = rectPath,
            path2 = circlePath
        )

        return Outline.Generic(combinedPath)
    }
}


@Composable
fun ZBottomSheetGradientHeader(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    tagID: String = "",
    allowDismiss: Boolean = false,
    allowBack: Boolean = false,
    showConfetti: Boolean= false,
    onBackPress:(()->Unit)?=null,
    radius: Dp = 100.dp,
    gradient: ZGradient = LocalZGradientColor.current,
    maxTitleLines: Int = 1,
    illustrationIcon: @Composable () -> Unit,
) {
    val sheetActionController = LocalSheetCloseController.current
    Box(modifier = modifier) {
        Column (
            modifier = Modifier
                .clip(
                    shape = TopCircleOverlapShape(
                        circleRadius =(radius.value / 2).dp,
                        // Center the circle so its top edge is at Y=0 of the shape's bounds
                        circleCenterYFromTop = (radius.value / 2).dp,
                        topCornerRadius = 20.dp
                    )
                )
                .background(gradient = gradient)
                .fillMaxWidth(),
        ) {
            BlankHeight(height = (radius.value / 2).dp)
            Box(modifier = Modifier.fillMaxWidth()){
                if(showConfetti) {
                    Image(
                        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                        painter = painterResource(ZIcons.ic_success_confetti),
                        contentDescription = ""
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()){
                    if (allowDismiss || allowBack) {
                        CompositionLocalProvider(LocalZIconSize provides 14.dp) {
                            Box(modifier = Modifier.fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                                if(allowBack) {
                                    ZToolbarIconAction(
                                        modifier = Modifier.align(
                                            Alignment.TopStart
                                        ),
                                        icon = ZIcons.ic_arrow_left.asZIcon,
                                        size = 24.dp,
                                        tagID = "back",
                                        onGradient = false,
                                        onClick = {
                                            onBackPress?.invoke()
                                        },
                                    )
                                }
                                if(allowDismiss) {
                                    Box(
                                        modifier =  Modifier.align(Alignment.TopEnd)
                                            .size(22.dp)
                                            .border(1.dp,ZTheme.color.icon.singleTonePrimary,ZTheme.shapes.full)
                                            .clickable {
                                                sheetActionController.close()
                                            },
                                    ) {
                                        ZIconButton(
                                            modifier = Modifier.align(Alignment.Center).setTestingTag(tagID).semantics{
                                                testTagsAsResourceId = true
                                            },
                                            icon = ZIcons.ic_cross.asZIcon,
                                            size = 14.dp,
                                            tagId = "close",
                                            color = ZTheme.color.icon.singleTonePrimary,
                                            onClick = {
                                                sheetActionController.close()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Text(
                        text = title,
                        color = ZTheme.color.text.primary,
                        maxLines = maxTitleLines,
                        overflow = TextOverflow.Ellipsis,
                        style = ZTheme.typography.bodyRegularB1.semiBold(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.setTestingTag("title").semantics{testTagsAsResourceId=true}
                            .padding(start = 16.dp, end = 16.dp, top = if(allowDismiss) 0.dp else 40.dp, bottom =
                                if(subTitle.isNotEmpty()) 12.dp else 16.dp)
                            .fillMaxWidth()
                    )
                    AnimatedVisibility(subTitle.isNotEmpty()) {
                        Text(
                            text = subTitle,
                            color = ZTheme.color.text.primary,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = ZTheme.typography.bodyRegularB3.medium(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp),
                        )
                    }
                }
            }
        }
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
fun ZBottomSheetGradientHeader(
    title: String,
    illustrationIcon: ZIcon,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    allowDismiss: Boolean = true,
    showConfetti: Boolean= false,
    allowBack: Boolean = false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    illustrationSize: ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
) {

    ZBottomSheetGradientHeader(
        title = title,
        subTitle = subTitle,
        modifier = modifier,
        allowDismiss = allowDismiss,
        showConfetti=showConfetti,
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


@ThemePreviews
@Composable
private fun PreviewZBottomSheetGradientHeader() {
    ZSheetPreviewContainer {
        Box(modifier = Modifier.background(color = Color.LightGray)) {
            ZBottomSheetGradientHeader(
                title = "{{BTC}} Buy Order Failed",
                subTitle = "Please try placing your order again.",
                allowDismiss = true,
                gradient = ZTheme.color.bottomSheet.green
            ){
                ZIllustrationIcon(
                    modifier = Modifier,
                    icon = ZIcons.ic_glass_error.asZIcon,
                    illustrationSize = ZIllustrationSize.Regular,
                    illustrationType = ZIllustrationType.RED,
                )
            }
        }
    }
}