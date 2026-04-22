package com.zebpay.ui.v3.components.atoms.misc

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * A custom shape for the tooltip bubble with a triangular pointer at the bottom.
 */
class BubbleShape(
    private val cornerRadius: Dp,
    private val pointerWidth: Dp,
    private val pointerHeight: Dp
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val cornerRadiusPx = with(density) { cornerRadius.toPx() }
        val pointerWidthPx = with(density) { pointerWidth.toPx() }
        val pointerHeightPx = with(density) { pointerHeight.toPx() }

        val path = Path().apply {
            val bodyHeight = size.height - pointerHeightPx
            addRoundRect(RoundRect(0f, 0f, size.width, bodyHeight, cornerRadiusPx, cornerRadiusPx))

            // Draw the triangular pointer at the bottom center
            moveTo(size.width / 2 - pointerWidthPx / 2, bodyHeight)
            lineTo(size.width / 2, size.height)
            lineTo(size.width / 2 + pointerWidthPx / 2, bodyHeight)
            close()
        }
        return Outline.Generic(path)
    }
}

/**
 * A reusable slider component with discrete, snapping stoppage points and a value bubble.
 *
 * @param modifier Modifier for the root container of the slider.
 * @param stoppages A list of Float values between 0.0f and 1.0f representing the allowed stops.
 * @param onStoppageChange A callback invoked with the selected stoppage value when the user releases the thumb.
 * @param valueFormatter A function to format the float stoppage value into a displayable string for the bubble.
 * @param currentValue The index of the initial value.
 * @param progressColor The color of the active part of the slider track.
 * @param trackColor The color of the inactive part of the slider track.
 * @param bubbleColor The background color of the value bubble.
 * @param bubbleTextStyle The TextStyle for the text inside the bubble.
 */
@Composable
fun ZProgressStoppageSlider(
    modifier: Modifier = Modifier,
    stoppages: List<Float>,
    onStoppageChange: (Float) -> Unit,
    valueFormatter: (Float) -> String,
    currentValue: Float = 0f,
    progressColor:Color = ZTheme.color.icon.singleToneBlue,
    trackColor:Color = ZTheme.color.separator.solidDefault,
    bubbleColor:Color = ZTheme.color.icon.singleToneBlue,
    bubbleTextStyle :TextStyle = ZTheme.typography.bodyRegularB5.copy(
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.SemiBold
    )) {
    // Basic validation
    require(stoppages.isNotEmpty()) { "Stoppages list cannot be empty." }

    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()

    var sliderWidth by remember { mutableFloatStateOf(0f) }
    val thumbRadius = 12.dp
    val thumbRadiusPx = with(density) { thumbRadius.toPx() }
    val hapticFeedback = LocalHapticFeedback.current

    // Convert stoppages from fractions (0-1) to pixel offsets
    val stoppageOffsets = remember(stoppages, sliderWidth) {
        stoppages.map { (it * sliderWidth).coerceIn(0f, sliderWidth) }
    }

    // Animatable for the thumb's horizontal position in pixels
    val offsetX = remember { Animatable(0f) }

    // State to show/hide the bubble during drag
    var isDragging by remember { mutableStateOf(false) }

    var currentHapticStop:Int = currentValue.roundToInt()

    // Initialize the slider to the initial stoppage point
    LaunchedEffect(sliderWidth, currentValue) {
        if (sliderWidth > 0 && isDragging.not()) {
            val initialOffset = currentValue * sliderWidth
            offsetX.snapTo(initialOffset)
        }
    }


    // The main container for the slider
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(28.dp) // Ensure the Box wraps its content's height
            .onSizeChanged { sliderWidth = (it.width - 2 * thumbRadiusPx).coerceAtLeast(0f) }
            .padding(horizontal = thumbRadius) // Padding to ensure thumb doesn't go out of bounds
    ) {
        // --- Track and Stoppage Points ---
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    val clickedX = tapOffset.x.coerceIn(0f, sliderWidth)
                    val progress = (clickedX / sliderWidth).coerceIn(0f, 1f)
                    onStoppageChange(progress)
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.Confirm)
                    coroutineScope.launch {
                        offsetX.snapTo(clickedX)
                    }
                }
            }
            .height(4.dp)
            .align(Alignment.Center)) {
            // Draw the main background track
            drawLine(trackColor, start = Offset(0f, center.y), end = Offset(size.width, center.y), strokeWidth = size.height, cap = StrokeCap.Round)
            // Draw the progress track
            drawLine(progressColor, start = Offset(0f, center.y), end = Offset(offsetX.value, center.y), strokeWidth = size.height, cap = StrokeCap.Round)
            // Draw stoppage points
            stoppageOffsets.forEach { stopOffset ->
                drawCircle(color = if(stopOffset > offsetX.value)
                    trackColor else progressColor,
                    radius = 4.dp.toPx(), center = Offset(stopOffset, center.y))
            }
        }

        // --- Draggable Thumb ---
        Box(
            modifier = Modifier
                .offset { IntOffset((offsetX.value.roundToInt() - (thumbRadiusPx.roundToInt()/2)), thumbRadius.toPx().roundToInt()/2 +(2.dp.toPx().roundToInt())) }
                .size(thumbRadius)
                .shadow(4.dp, CircleShape)
                .background(ZTheme.color.icon.singleToneBlue, CircleShape)
                .padding(2.dp)
                .background(ZTheme.color.icon.singleToneWhite, CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newOffset = (offsetX.value + delta).coerceIn(0f, sliderWidth)
                        coroutineScope.launch { offsetX.snapTo(newOffset) }
                    },
                    onDragStarted = {
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
                        isDragging = true
                    },
                    onDragStopped = {
                        isDragging = false
                        val progress = (offsetX.value / sliderWidth).coerceIn(0f, 1f)
                        onStoppageChange(progress)
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
                    }
                )
        )

        // --- Value Bubble ---
        if (isDragging) {
            val progress = (offsetX.value / sliderWidth).coerceIn(0f, 1f)
            val bubbleText = valueFormatter(progress)
            currentHapticStop = (progress * 100).roundToInt()

            LaunchedEffect(currentHapticStop) {
                hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)
            }

            hapticFeedback.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick)

            val textLayoutResult = remember(bubbleText) { textMeasurer.measure(bubbleText, bubbleTextStyle) }
            val bubbleSize = with(density) {
                Size(
                    width = textLayoutResult.size.width + 8.dp.toPx(),
                    height = textLayoutResult.size.height + 6.dp.toPx()
                )
            }

            Canvas(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = (offsetX.value - bubbleSize.width / 2).roundToInt(),
                            y = (-bubbleSize.height + thumbRadiusPx.roundToInt()/4).roundToInt()
                        )
                    }
                    .size(
                        width = with(density) { (bubbleSize.width).toDp() },
                        height = with(density) { (bubbleSize.height).toDp() }
                    )
            ) {
                val outline = BubbleShape(4.dp, 9.dp, 7.dp).createOutline(size, layoutDirection, this)
                if (outline is Outline.Generic) {
                    drawPath(path = outline.path, color = bubbleColor)
                }
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = (size.width - textLayoutResult.size.width) / 2,
                        y = (size.height - 6.dp.toPx() - textLayoutResult.size.height) / 2
                    )
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun StoppageSliderPreview() {
    val stoppages = listOf(0f, 0.25f, 0.5f, 0.75f, 1f)
    var selectedValue by remember { mutableFloatStateOf(stoppages[0]) }

    ZBackgroundPreviewContainer {
        ZCommonLabel(
            label = "Selected Value: ${(selectedValue * 100).roundToInt()}",
            textStyle = ZTheme.typography.bodyRegularB3,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        ZProgressStoppageSlider(
            stoppages = stoppages,
            onStoppageChange = { newValue ->
                selectedValue = newValue
            },
            valueFormatter = { stoppageFraction ->
                // Example: Convert fraction to a value from 0 to 10
                "${(stoppageFraction * 100).roundToInt()}%"
            },
            currentValue = 0.0f, // Start at 0.25f
            progressColor = ZTheme.color.icon.singleToneBlue,
            trackColor = ZTheme.color.separator.solidDefault,
            bubbleColor = ZTheme.color.icon.singleToneBlue,
            bubbleTextStyle = ZTheme.typography.bodyRegularB4.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.SemiBold
            ),
        )
    }
}