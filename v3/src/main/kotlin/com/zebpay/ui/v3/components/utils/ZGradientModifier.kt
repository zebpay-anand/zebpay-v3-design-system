package com.zebpay.ui.v3.components.utils

import android.os.Build
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * Calculates the start and end offsets for a linear gradient based on an angle
 * (in degrees, counter-clockwise from the positive X-axis) and the size of the area.
 *
 * @param degrees The angle in degrees (0 = right, 90 = up, 180 = left, 270 = down).
 * @param size The size of the area the gradient will be applied to.
 * @return A Pair containing the start Offset and end Offset for Brush.linearGradient.
 */
internal fun calculateGradientOffsets(degrees: Float, size: Size): Pair<Offset, Offset> {
    // Convert degrees to radians
    val radians = Math.toRadians(degrees.toDouble())
    // Calculate the trigonometric values
    val cosRad = cos(radians).toFloat()
    val sinRad = sin(radians).toFloat() // Compose Y is inverted, but calculation handles it

    // Calculate the center of the area
    val centerX = size.width / 2f
    val centerY = size.height / 2f

    // Calculate the magnitude required to ensure the gradient covers the entire area.
    val magnitude = (abs(size.width * cosRad) + abs(size.height * sinRad)) / 2f

    // Calculate the start and end points relative to the center
    val start = Offset(
        x = centerX - cosRad * magnitude,
        y = centerY - sinRad * magnitude,
    )
    val end = Offset(
        x = centerX + cosRad * magnitude,
        y = centerY + sinRad * magnitude,
    )

    return start to end
}

/**
 * A custom modifier that clips the composable to the given shape and draws a linear gradient
 * background behind the content, respecting the specified angle.
 *
 * @param gradient The ZGradient object containing angle and color stops.
 * @param shape The Shape to clip the composable and the gradient background to.
 */
fun Modifier.background(gradient: ZGradient, shape: Shape = RoundedCornerShape(0.dp)): Modifier =
    composed {
        // Use composed to create a stateful modifier if needed, though not strictly necessary here
        // It ensures that remember works correctly within the modifier chain.

        // Remember the list of color stops to avoid creating new arrays on every recomposition
        val rememberedColorStops = remember(gradient.colorStops()) {
            gradient.colorStops().toTypedArray() // Convert to Array for Brush vararg
        }
        // Apply clipping first
        this
            .clip(shape)
            // Then draw the gradient behind the content within the clipped bounds
            .drawBehind {
                // Convert CSS angle (0deg=top, 90deg=right) to mathematical angle (0deg=right, 90deg=up)
                // Math Angle = (90 - CSS Angle + 360) % 360
                val mathAngle = (90f - gradient.angle() + 360f) % 360f

                // Calculate the gradient offsets based on the mathematical angle and the drawing area size
                val (startOffset, endOffset) = calculateGradientOffsets(mathAngle, size)

                // Create the linear gradient brush
                val brush = Brush.linearGradient(
                    colorStops = rememberedColorStops,
                    start = startOffset,
                    end = endOffset,
                )

                // Draw the rectangle covering the whole composable area with the gradient
                drawRect(brush = brush)
            }
    }


/**
 * A custom modifier that draws a border around the composable with a linear gradient,
 * clipped to the specified shape.
 *
 * @param width The width of the border stroke.
 * @param zgradient The ZGradient object containing angle and color stops for the border brush.
 * @param shape The Shape to clip the composable and draw the border along.
 */
fun Modifier.border(
    width: Dp = 1.dp,
    gradient: ZGradient,
    shape: Shape = RoundedCornerShape(4.dp),
): Modifier = composed {
    val rememberedColorStops = remember(gradient.colorStops()) {
        gradient.colorStops().toTypedArray()
    }

    val offset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
          3f
    }else{
        0f
    }

    // Use drawBehind to draw the border under the content, clipped to the shape
    this.clip(shape = shape)
        .drawBehind {
            // Convert border width to pixels
            val widthPx = with(density) { width.toPx() + offset } //pixel offset
            // Don't draw if width is zero or negative
            if (widthPx <= 0) return@drawBehind

            // Convert CSS angle to mathematical angle
            val mathAngle = (90f - gradient.angle() + 360f) % 360f

            // Calculate gradient offsets
            val (startOffset, endOffset) = calculateGradientOffsets(mathAngle, size)

            // Create the brush
            val brush = Brush.linearGradient(
                colorStops = rememberedColorStops,
                start = startOffset,
                end = endOffset,
            )

            // Create the outline from the shape
            // Important: Use the available size from the drawing scope
            val outline: Outline = shape.createOutline(size, layoutDirection, this)

            // Draw the outline using the brush and stroke style
            drawOutline(
                outline = outline,
                brush = brush,
                style = Stroke(width = widthPx),
            )
        }
}

/**
 * A custom modifier that draws an animated border around the composable.
 * The animation sweeps a gradient from left to right with opacity 0→100→0.
 * Based on Figma spec: linear gradient 0-100-0 with color #001764
 *
 * @param width The width of the border stroke.
 * @param color The color for the animated gradient (default: #001764 dark blue).
 * @param shape The Shape to clip the composable and draw the border along.
 * @param duration Animation duration in milliseconds.
 * @param delay Delay between animation cycles in milliseconds.
 */
fun Modifier.animatedBorder(
    width: Dp = 1.dp,
    color: Color = Color(0xFF001764),
    shape: Shape = RoundedCornerShape(8.dp),
    duration: Int = 2000,
    delay: Int = 1000,
): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "border_animation")
    val animatedProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = duration,
                delayMillis = delay,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "border_sweep",
    )

    val offset = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        3f
    } else {
        0f
    }

    this.clip(shape = shape)
        .drawBehind {
            val widthPx = with(density) { width.toPx() + offset }
            if (widthPx <= 0) return@drawBehind

            // Calculate the gradient sweep position based on animation progress
            val sweepWidth = size.width * 0.4f // Width of the gradient sweep
            val totalDistance = size.width + sweepWidth
            val currentPosition = -sweepWidth + (totalDistance * animatedProgress)

            // Create animated gradient brush (0→100→0 opacity sweep)
            val brush = Brush.horizontalGradient(
                colorStops = arrayOf(
                    0.0f to color.copy(alpha = 0f),
                    0.3f to color.copy(alpha = 0.5f),
                    0.5f to color.copy(alpha = 1f),
                    0.7f to color.copy(alpha = 0.5f),
                    1.0f to color.copy(alpha = 0f),
                ),
                startX = currentPosition,
                endX = currentPosition + sweepWidth,
            )

            val outline: Outline = shape.createOutline(size, layoutDirection, this)

            drawOutline(
                outline = outline,
                brush = brush,
                style = Stroke(width = widthPx),
            )
        }
}


fun Modifier.gradientIcon(gradient: ZGradient): Modifier = composed {
    val rememberedColorStops = remember(gradient.colorStops()) {
        gradient.colorStops().toTypedArray()
    }
    this
        .graphicsLayer(alpha = 0.99f)
        .drawWithCache {
            // Convert CSS angle to mathematical angle
            val mathAngle = (90f - gradient.angle() + 360f) % 360f
            // Calculate gradient offsets
            val (startOffset, endOffset) = calculateGradientOffsets(mathAngle, size)
            // Create the brush
            val brush = Brush.linearGradient(
                colorStops = rememberedColorStops,
                start = startOffset,
                end = endOffset,
            )
            onDrawWithContent {
                drawContent()
                drawRect(brush, blendMode = BlendMode.SrcAtop)
            }
        }
}


@Preview
@Composable
fun PreviewGradientColor() {
    ZBackgroundPreviewContainer {
        Spacer(
            modifier = Modifier
                .size(80.dp)
                .background(gradient = ZGradientColors.Green03, shape = CircleShape),
        )

        BlankHeight(16.dp)
        Spacer(
            modifier = Modifier
                .size(80.dp)
                .border(gradient = ZGradientColors.Green03, shape = CircleShape),
        )

    }
}