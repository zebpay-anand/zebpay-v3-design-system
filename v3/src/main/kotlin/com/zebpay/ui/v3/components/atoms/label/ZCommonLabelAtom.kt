package com.zebpay.ui.v3.components.atoms.label

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Dimension.Companion.matchParent
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import com.zebpay.ui.v3.components.utils.setTestingTag


/**
 * Configuration for the dashed underline style.
 *
 * @param dashLength The length of each dash in the underline.
 * @param gapLength The length of the gap between dashes.
 * @param strokeWidth The thickness of the underline.
 * @param underlineOffset The vertical offset of the underline from the bottom of the text's bounding box.
 *                        A positive value moves the line up, a negative value moves it down.
 */
@Immutable
data class DashedUnderline(
    val dashLength: Dp = 4.dp,
    val gapLength: Dp = 2.dp,
    val strokeWidth: Dp = 1.dp,
    val underlineOffset: Dp = 2.dp,
    val dashLineColor : Color
)


object DashedUnderlineDefaults {

    @Composable
    fun defaultConfig(
        dashLength: Dp = 2.dp,
        gapLength: Dp = 2.dp,
        strokeWidth: Dp = (1.2).dp,
        underlineOffset: Dp = 2.dp,
        dashLineColor: Color = LocalZLabelColor.current
    ):DashedUnderline = DashedUnderline(
        dashLength = dashLength,
        gapLength= gapLength,
        strokeWidth = strokeWidth,
        underlineOffset = underlineOffset,
        dashLineColor=dashLineColor
    )
}


@Composable
fun ZLabel(
    label: String,
    modifier: Modifier = Modifier,
    underLine: Boolean = false,
    dashLine: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    labelColor: Color = LocalZLabelColor.current,
    textAlign: TextAlign = TextAlign.Start,
    maxLine: Int=1,
    tagId: String="",
    dashLineConfig: DashedUnderline = DashedUnderlineDefaults.defaultConfig(),
    hasOverFlowCall: (Boolean)->Unit = {}
) {

    val density = LocalDensity.current
    // Convert Dp values to Px once outside the drawBehind lambda, as drawBehind
    // is called frequently.
    val dashLengthPx = remember(dashLineConfig.dashLength) { with(density) { dashLineConfig.dashLength.toPx() } }
    val gapLengthPx = remember(dashLineConfig.gapLength) { with(density) { dashLineConfig.gapLength.toPx() } }
    val strokeWidthPx = remember(dashLineConfig.strokeWidth) { with(density) { dashLineConfig.strokeWidth.toPx() } }
    val underlineOffsetPx = remember(dashLineConfig.underlineOffset) { with(density) { dashLineConfig.underlineOffset.toPx() } }

    // Create the PathEffect for dashing once and remember it.
    // The `floatArrayOf` takes [on_length, off_length, on_length, off_length, ...].
    // `0f` is the phase (offset) of the pattern.
    val pathEffect = remember(dashLengthPx, gapLengthPx) {
        PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)
    }

    Text(
        modifier = modifier.setTestingTag(tagId).conditional(underLine){
            drawBehind {
                val strokeWidthPx = 1.dp.toPx()
                val verticalOffset = size.height - 2.sp.toPx()
                drawLine(
                    color = labelColor,
                    strokeWidth = strokeWidthPx,
                    start = Offset(0f, verticalOffset),
                    end = Offset(size.width, verticalOffset)
                )
            }
        }.conditional(dashLine){
            drawBehind {
            val verticalOffset = size.height - underlineOffsetPx
            drawLine(
                color = dashLineConfig.dashLineColor,
                strokeWidth = strokeWidthPx,
                start = Offset(0f, verticalOffset),
                end = Offset(size.width, verticalOffset),
                pathEffect = pathEffect
            ) }
        },
        text = label,
        style = textStyle,
        color = labelColor,
        textAlign = textAlign,
        maxLines = maxLine,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val hasOverflow = textLayoutResult.hasVisualOverflow
            hasOverFlowCall.invoke(hasOverflow) // Notify external callback
        }
    )
}

@Composable
fun ZCommonLabel(
    label: String,
    modifier: Modifier = Modifier,
    onLabelClick: (() -> Unit)? = null,
    tagId: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    labelColor: Color = LocalZLabelColor.current,
    leading: @Composable (() -> Unit)? = null,
    leadingColor: Color = LocalContentColor.current,
    trailing: @Composable (() -> Unit)? = null,
    trailingColor: Color = LocalContentColor.current,
    maxWidth: Boolean = false,
    underLine: Boolean = false,
    dashLine: Boolean = false,
    dashLineConfig: DashedUnderline = DashedUnderlineDefaults.defaultConfig(),
    maxLine: Int = 1,
    textAlign: TextAlign = TextAlign.Center,
    hasOverFlowCall: (Boolean)->Unit = {},
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
) {
    var widthMaxRequired by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    // Convert Dp values to Px once outside the drawBehind lambda, as drawBehind
    // is called frequently.
    val dashLengthPx = remember(dashLineConfig.dashLength) { with(density) { dashLineConfig.dashLength.toPx() } }
    val gapLengthPx = remember(dashLineConfig.gapLength) { with(density) { dashLineConfig.gapLength.toPx() } }
    val strokeWidthPx = remember(dashLineConfig.strokeWidth) { with(density) { dashLineConfig.strokeWidth.toPx() } }
    val underlineOffsetPx = remember(dashLineConfig.underlineOffset) { with(density) { dashLineConfig.underlineOffset.toPx() } }

    // Create the PathEffect for dashing once and remember it.
    // The `floatArrayOf` takes [on_length, off_length, on_length, off_length, ...].
    // `0f` is the phase (offset) of the pattern.
    val pathEffect = remember(dashLengthPx, gapLengthPx) {
        PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        if (leading != null) {
            CompositionLocalProvider(
                LocalContentColor provides leadingColor,
                LocalTextStyle provides textStyle,
            ) {
                leading()
            }
        }
        Text(
            text = label,
            style = textStyle,
            modifier = Modifier
                .safeClickable(enabled = onLabelClick != null, tagId = tagId) {
                    onLabelClick?.invoke()
                }.conditional(maxWidth || widthMaxRequired) {
                    weight(1f)
                }.conditional(underLine){
                    drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = labelColor,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
                }.conditional(dashLine){
                    drawBehind {
                        val verticalOffset = size.height - underlineOffsetPx
                        drawLine(
                            color = dashLineConfig.dashLineColor,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset),
                            pathEffect = pathEffect
                        ) }
                },
            color = labelColor,
            textAlign = textAlign,
            maxLines = maxLine,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                widthMaxRequired = textLayoutResult.hasVisualOverflow
                hasOverFlowCall.invoke(widthMaxRequired) // Notify external callback
            }
        )
        if (trailing != null) {
            CompositionLocalProvider(
                LocalContentColor provides trailingColor,
                LocalTextStyle provides textStyle,
            ) {
                trailing()
            }
        }
    }
}


@Composable
fun ZCommonGradientLabel(
    label: String,
    modifier: Modifier = Modifier,
    onLabelClick: (() -> Unit)? = null,
    tagId: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    labelColor: ZGradient = LocalZGradientColor.current,
    leading: @Composable (() -> Unit)? = null,
    leadingColor: ZGradient = LocalZGradientColor.current,
    trailing: @Composable (() -> Unit)? = null,
    trailingColor: ZGradient = LocalZGradientColor.current,
    maxWidth: Boolean = false,
    underLine: Boolean = false,
    dashLine: Boolean = false,
    dashLineConfig: DashedUnderline = DashedUnderlineDefaults.defaultConfig(),
    textAlign: TextAlign = TextAlign.Center,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    hasOverFlowCall: (Boolean)->Unit = {}
) {

    val density = LocalDensity.current
    // Convert Dp values to Px once outside the drawBehind lambda, as drawBehind
    // is called frequently.
    val dashLengthPx = remember(dashLineConfig.dashLength) { with(density) { dashLineConfig.dashLength.toPx() } }
    val gapLengthPx = remember(dashLineConfig.gapLength) { with(density) { dashLineConfig.gapLength.toPx() } }
    val strokeWidthPx = remember(dashLineConfig.strokeWidth) { with(density) { dashLineConfig.strokeWidth.toPx() } }
    val underlineOffsetPx = remember(dashLineConfig.underlineOffset) { with(density) { dashLineConfig.underlineOffset.toPx() } }

    // Create the PathEffect for dashing once and remember it.
    // The `floatArrayOf` takes [on_length, off_length, on_length, off_length, ...].
    // `0f` is the phase (offset) of the pattern.
    val pathEffect = remember(dashLengthPx, gapLengthPx) {
        PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = horizontalArrangement,
    ) {
        if (leading != null) {
            CompositionLocalProvider(
                LocalZGradientColor provides leadingColor,
                LocalTextStyle provides textStyle,
            ) {
                leading()
            }
        }
        Text(
            text = label,
            style = textStyle.copy(brush = labelColor.toTextBrush()),
            modifier = Modifier.conditional(maxWidth) {
                weight(1f)
            }.conditional(onLabelClick != null) {
                testTag(tagId).semantics{ testTagsAsResourceId = true}
                    .clickable(onLabelClick != null) {
                        onLabelClick?.invoke()
                    }
            }.conditional(underLine){
                drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        brush = labelColor.toTextBrush(),
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                }
            }.conditional(dashLine){
                drawBehind {
                    val verticalOffset = size.height - underlineOffsetPx
                    drawLine(
                        brush = labelColor.toTextBrush(),
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset),
                        pathEffect = pathEffect
                    ) }
            },
            textAlign = textAlign,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                val hasOverflow = textLayoutResult.hasVisualOverflow
                hasOverFlowCall.invoke(hasOverflow) // Notify external callback
            }
        )
        if (trailing != null) {
            CompositionLocalProvider(
                LocalZGradientColor provides trailingColor,
                LocalTextStyle provides textStyle,
            ) {
                trailing()
            }
        }
    }
}



@Composable
fun ZConstraintCommonLabel(
    label: String,
    modifier: Modifier = Modifier,
    onLabelClick: (() -> Unit)? = null,
    tagId: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    labelColor: Color = LocalZLabelColor.current,
    leading: @Composable (() -> Unit)? = null,
    leadingColor: Color = LocalContentColor.current,
    trailing: @Composable (() -> Unit)? = null,
    trailingColor: Color = LocalContentColor.current,
    maxWidth: Boolean = false,
    underLine: Boolean = false,
    dashLine: Boolean = false,
    maxLine: Int = 1,
    dashLineConfig: DashedUnderline = DashedUnderlineDefaults.defaultConfig(),
    textAlign: TextAlign = TextAlign.Center,
) {

    val density = LocalDensity.current
    // Convert Dp values to Px once outside the drawBehind lambda, as drawBehind
    // is called frequently.
    val dashLengthPx = remember(dashLineConfig.dashLength) { with(density) { dashLineConfig.dashLength.toPx() } }
    val gapLengthPx = remember(dashLineConfig.gapLength) { with(density) { dashLineConfig.gapLength.toPx() } }
    val strokeWidthPx = remember(dashLineConfig.strokeWidth) { with(density) { dashLineConfig.strokeWidth.toPx() } }
    val underlineOffsetPx = remember(dashLineConfig.underlineOffset) { with(density) { dashLineConfig.underlineOffset.toPx() } }

    // Create the PathEffect for dashing once and remember it.
    // The `floatArrayOf` takes [on_length, off_length, on_length, off_length, ...].
    // `0f` is the phase (offset) of the pattern.
    val pathEffect = remember(dashLengthPx, gapLengthPx) {
        PathEffect.dashPathEffect(floatArrayOf(dashLengthPx, gapLengthPx), 0f)
    }
    ConstraintLayout(modifier = modifier) {
        val (leadingRef, textRef, trailingRef) = createRefs()
        // Leading Composable
        if (leading != null) {
            Box(
                modifier = Modifier.constrainAs(leadingRef) {
                    start.linkTo(parent.start)
                    centerVerticallyTo(parent)
                },
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalContentColor provides leadingColor) {
                    leading()
                }
            }
        }
        // Trailing Composable
        if (trailing != null) {
            Box(
                modifier = Modifier.constrainAs(trailingRef) {
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                },
                contentAlignment = Alignment.Center
            ) {
                CompositionLocalProvider(LocalContentColor provides trailingColor) {
                    trailing()
                }
            }
        }

        // Text Composable
        Text(
            text = label,
            style = textStyle,
            modifier = Modifier.constrainAs(textRef) {
                // Chain to leading if it exists, otherwise to parent start
                start.linkTo(
                    anchor = if (leading != null) leadingRef.end else parent.start,
                    margin = if (leading != null) 8.dp else 0.dp
                )
                // Chain to trailing if it exists, otherwise to parent end
                end.linkTo(
                    anchor = if (trailing != null) trailingRef.start else parent.end,
                    margin = if (trailing != null) 8.dp else 0.dp
                )
                centerVerticallyTo(parent)
                width = if(maxWidth) matchParent else Dimension.fillToConstraints
            }.safeClickable(enabled = onLabelClick != null, tagId = tagId) {
                onLabelClick?.invoke()
            }.conditional(underLine){
                drawBehind {
                    val strokeWidthPx = 1.dp.toPx()
                    val verticalOffset = size.height - 2.sp.toPx()
                    drawLine(
                        color = labelColor,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset)
                    )
                }
            }.conditional(dashLine){
                drawBehind {
                    val verticalOffset = size.height - underlineOffsetPx
                    drawLine(
                        color = dashLineConfig.dashLineColor,
                        strokeWidth = strokeWidthPx,
                        start = Offset(0f, verticalOffset),
                        end = Offset(size.width, verticalOffset),
                        pathEffect = pathEffect
                    ) }
            },
            color = labelColor,
            textAlign = textAlign,
            maxLines = maxLine,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZCommonLabel() {
    ZBackgroundPreviewContainer {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB4,
            LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
            LocalZLabelColor provides ZTheme.color.text.primary,
            LocalZGradientColor provides ZTheme.color.icon.dualToneGradient,
        ) {
            ZLabel(
                label = "Label",
                dashLine = true,
                dashLineConfig = DashedUnderlineDefaults.defaultConfig(
                    dashLineColor = ZTheme.color.separator.solidDefault
                )
            )

            ZCommonLabel(
                label = "Label",
                underLine = true,
                onLabelClick = {

                },
            )
            ZCommonLabel(
                label = "Label",
                underLine = false,
            )
            ZCommonLabel(
                label = "Label",
                leading = {
                    ZIcon(
                        icon = ZIcons.ic_arrow_left.asZIcon,
                    )
                },
            )
            ZCommonLabel(
                label = "Label",
                leading = {
                    ZIcon(
                        icon = ZIcons.ic_arrow_left.asZIcon,
                    )
                },
                trailing = {
                    ZIcon(
                        icon = ZIcons.ic_arrow_right.asZIcon,
                    )
                },
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZCommonGradientLabel() {
    ZBackgroundPreviewContainer {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB4,
            LocalContentColor provides ZTheme.color.icon.singleTonePrimary,
            LocalZGradientColor provides ZTheme.color.icon.dualToneGradient,
        ) {

            ZCommonGradientLabel(
                label = "Label",
                dashLine = true,
            )

            ZCommonGradientLabel(
                label = "Label",
                underLine = true,
            )

            ZCommonGradientLabel(
                label = "Label",
            )

            ZCommonGradientLabel(
                label = "Label",
                leading = {
                    ZGradientIcon(
                        icon = ZIcons.ic_arrow_left.asZIcon,
                    )
                },
            )
            ZCommonGradientLabel(
                label = "Label",
                leading = {
                    ZGradientIcon(
                        icon = ZIcons.ic_arrow_left.asZIcon,
                    )
                },
                trailing = {
                    ZGradientIcon(
                        icon = ZIcons.ic_arrow_right.asZIcon,
                    )
                },
            )
        }
    }
}
