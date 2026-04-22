package com.zebpay.ui.v3.components.molecules.tooltip


import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.utils.border
import kotlin.math.roundToInt


/**
 * tooltipContent - Content to display in tooltip.
 */
@Composable
fun ZTooltipPopup(
    modifier: Modifier = Modifier,
    enable:Boolean = false,
    backgroundShape: Shape = BubbleWithArrowShape(),
    backgroundColor: Color = ZTheme.color.card.selected,
    horizontalPadding: Dp = 8.dp,
    tooltipContent: @Composable RowScope.() -> Unit,
    requesterView: @Composable (Modifier) -> Unit,
) {
    var isShowTooltip by remember { mutableStateOf(false) }
    var position by remember { mutableStateOf(TooltipPopupPosition(
        alignment = ZTooltipAlignment.TopCenter
    )) }

    val view = LocalView.current.rootView

    if (isShowTooltip && enable) {
        TooltipPopup(
            onDismissRequest = {
                isShowTooltip = isShowTooltip.not()
            },
            backgroundColor = backgroundColor,
            backgroundShape = backgroundShape,
            horizontalPadding = horizontalPadding,
            position = position,
        ) {
            tooltipContent()
        }
    }
    requesterView(
        modifier
            .safeClickable(
                enabled = enable,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ){
                isShowTooltip = isShowTooltip.not()
            }
            .onGloballyPositioned { coordinates ->
                position = calculateTooltipPopupPosition(view, coordinates)
            }
    )
}

@Composable
fun TooltipPopup(
    position: TooltipPopupPosition,
    backgroundShape: Shape = BubbleWithArrowShape(),
    backgroundColor: Color = ZTheme.color.card.selected,
    arrowHeight: Dp = 5.dp,
    horizontalPadding: Dp = 8.dp,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    var alignment = Alignment.TopCenter
    var offset = position.offset

    with(LocalDensity.current) {
        val arrowPaddingPx = arrowHeight.toPx().roundToInt()

        when (position.alignment) {
            ZTooltipAlignment.TopCenter -> {
                alignment = Alignment.TopCenter
                offset = offset.copy(
                    y = position.offset.y + arrowPaddingPx
                )
            }
            ZTooltipAlignment.BottomCenter -> {
                alignment = Alignment.BottomCenter
                offset = offset.copy(
                    y = position.offset.y - arrowPaddingPx
                )
            }
        }
    }

    val popupPositionProvider = remember(alignment, offset) {
        TooltipAlignmentOffsetPositionProvider(
            alignment = alignment,
            offset = offset,
            centerPositionX = position.centerPositionX,
            requestViewWidth = position.width
        )
    }

    Popup(
        popupPositionProvider = popupPositionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(),
    ) {
        Box (modifier = Modifier
            .border(1.dp, ZGradientColors.Primary01, shape = backgroundShape)
            .background(color = backgroundColor, shape = backgroundShape)
        ){
            Row(
                modifier = Modifier
                    .padding(horizontalPadding),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                content()
            }
        }
    }
}

internal class TooltipAlignmentOffsetPositionProvider(
    val alignment: Alignment,
    val offset: IntOffset,
    val centerPositionX: Float,
    val requestViewWidth:Int,
) : PopupPositionProvider {

    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        var popupPosition = IntOffset(0, 0)

        // Get the aligned point inside the parent
        val parentAlignmentPoint = alignment.align(
            IntSize.Zero,
            IntSize(anchorBounds.width, anchorBounds.height),
            layoutDirection
        )
        // Get the aligned point inside the child
        val relativePopupPos = alignment.align(
            IntSize.Zero,
            IntSize(popupContentSize.width, popupContentSize.height),
            layoutDirection
        )

        // Add the position of the parent
        popupPosition += IntOffset(anchorBounds.left, anchorBounds.top)

        // Add the distance between the parent's top left corner and the alignment point
        popupPosition += parentAlignmentPoint

        // Subtract the distance between the children's top left corner and the alignment point
        popupPosition -= IntOffset(relativePopupPos.x, relativePopupPos.y)

        // Add the user offset
        val resolvedOffset = IntOffset(
            offset.x * (if (layoutDirection == LayoutDirection.Ltr) 1 else -1),
            offset.y
        )

        popupPosition += resolvedOffset


        val tooltipWidth = popupContentSize.width
        val halfPopupContentSize = popupContentSize.center.x


        val offsetThreshold =  (requestViewWidth - tooltipWidth)/2


        return IntOffset(centerPositionX.toInt()- halfPopupContentSize +offsetThreshold , popupPosition.y)
    }
}

data class TooltipPopupPosition(
    val offset: IntOffset = IntOffset(0, 0),
    val alignment: ZTooltipAlignment = ZTooltipAlignment.TopCenter,
    val centerPositionX: Float = 0f,
    val width:Int = 0
)

fun calculateTooltipPopupPosition(
    view: View,
    coordinates: LayoutCoordinates?,
): TooltipPopupPosition {
    coordinates ?: return TooltipPopupPosition()

    val visibleWindowBounds = android.graphics.Rect()
    view.getWindowVisibleDisplayFrame(visibleWindowBounds)

    val boundsInWindow = coordinates.boundsInWindow()

    val heightAbove = boundsInWindow.top - visibleWindowBounds.top
    val heightBelow = visibleWindowBounds.bottom - visibleWindowBounds.top - boundsInWindow.bottom

    val centerPositionX = boundsInWindow.right - (boundsInWindow.right - boundsInWindow.left) / 2

    val offsetX = centerPositionX - visibleWindowBounds.centerX()

    return if (heightAbove < heightBelow) {
        val offset = IntOffset(
            y = coordinates.size.height,
            x = offsetX.toInt()
        )
        TooltipPopupPosition(
            offset = offset,
            alignment = ZTooltipAlignment.TopCenter,
            centerPositionX = centerPositionX,
            width = coordinates.size.width
        )
    } else {
        TooltipPopupPosition(
            offset = IntOffset(
                y = coordinates.size.height,
                x = offsetX.toInt()
            ),
            alignment = ZTooltipAlignment.TopCenter,
            centerPositionX = centerPositionX,
            width = coordinates.size.width
        )
    }
}

enum class ZTooltipAlignment {
    BottomCenter,
    TopCenter,
}


class BubbleWithArrowShape(
    private val cornerRadius: Dp = 8.dp,
    private val arrowWidth: Dp = 12.dp,
    private val arrowHeight: Dp = 8.dp,
    private val arrowOffsetFromRight: Dp = 18.dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val radius = with(density) { cornerRadius.toPx() }
        val arrowW = with(density) { arrowWidth.toPx() }
        val arrowH = with(density) { arrowHeight.toPx() }
        val arrowOffset = with(density) { arrowOffsetFromRight.toPx() }

        val path = Path().apply {
            moveTo(radius, 0f)
            lineTo(size.width - arrowOffset - arrowW, 0f)

            // Arrow
            lineTo(size.width - arrowOffset - arrowW / 2, -arrowH)
            lineTo(size.width - arrowOffset, 0f)

            // Top-right corner
            arcTo(
                Rect(size.width - radius * 2, 0f, size.width, radius * 2),
                -90f, 90f, false
            )
            lineTo(size.width, size.height - radius)

            // Bottom-right corner
            arcTo(
                Rect(size.width - radius * 2, size.height - radius * 2, size.width, size.height),
                0f, 90f, false
            )
            lineTo(radius, size.height)

            // Bottom-left corner
            arcTo(
                Rect(0f, size.height - radius * 2, radius * 2, size.height),
                90f, 90f, false
            )
            lineTo(0f, radius)

            // Top-left corner
            arcTo(
                Rect(0f, 0f, radius * 2, radius * 2),
                180f, 90f, false
            )
            close()
        }

        return Outline.Generic(path)
    }
}


@ThemePreviews
@Composable
fun BubbleBackgroundPreview(){
    ZBackgroundPreviewContainer(modifier = Modifier, innerPaddingValues = PaddingValues(20.dp)) {
        Box( modifier = Modifier
            .background(
                color = ZTheme.color.card.fill,
                shape = BubbleWithArrowShape(),
            )
            .border(1.dp, ZGradientColors.Primary01, shape = BubbleWithArrowShape()),) {
            ZLabel(
                label = "98,989,090.78076",
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

