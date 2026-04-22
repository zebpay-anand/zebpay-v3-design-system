package com.zebpay.ui.v3.components.atoms.misc


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.min

/**
 * A composable function that draws a donut chart with two segments based on input values,
 * featuring rounded ends for the segments.
 *
 * @param modifier Modifier for the Canvas.
 * @param valueX The first value.
 * @param valueY The second value.
 * @param colorX The color for the first value's segment.
 * @param colorY The color for the second value's segment.
 * @param strokeWidth The thickness of the donut ring.
 * @param gapAngle The angle in degrees for the gap between the two segments. A smaller value creates a smaller visual gap.
 * @param startAngle The starting angle in degrees for the first segment (0 is 3 o'clock, -90 is 12 o'clock).
 */
@Composable
fun ZAmountValuePieChart(
    modifier: Modifier = Modifier,
    valueX: Double,
    valueY: Double,
    colorX: Color = Color(0xFF309BF2),
    colorY: Color = Color(0xFFA76AF6),
    strokeWidth: Dp = 10.dp,
    gapAngle: Float = 2f,
    gapWidth: Dp = 2.dp, // Desired visual gap width
    animate: Boolean = false,
    animationDurationMillis: Int = 1000, // 1 second animation
    startAngle: Float = -90f, // Start at the top (12 o'clock)
) {
    val finalGapAngle: Float = if (valueX != 0.0 && valueY != 0.0) gapAngle else 0f
    val finalGapWidth: Dp = if (valueX != 0.0 && valueY != 0.0) gapWidth else 0.dp

    // Ensure values are non-negative Floats for calculations
    val safeValue1 = valueX.coerceAtLeast(0.0).toFloat()
    val safeValue2 = valueY.coerceAtLeast(0.0).toFloat()
    val totalValue = safeValue1 + safeValue2

    // Animation state: Animates a progress factor from 0.0 to 1.0
    val animationProgress = remember { Animatable(if (animate) 0f else 1f) }

    // Trigger animation on launch if enabled
    LaunchedEffect(
        animate,
        valueX,
        valueY,
    ) { // Re-trigger if values change significantly? Or just on initial animate flag?
        if (animate) {
            animationProgress.snapTo(0f) // Reset if values change and animation is on
            launch { // Launch coroutine for animation
                animationProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = animationDurationMillis),
                )
            }
        } else {
            // If animation is disabled, jump straight to the end state
            animationProgress.snapTo(1f)
        }
    }

    val density = LocalDensity.current

    Canvas(modifier = modifier) {
        // Calculate geometry inside Canvas where size is known
        val strokeWidthPx = with(density) { strokeWidth.toPx() }
        val gapWidthPx = with(density) { finalGapWidth.toPx() }

        // Calculate the outer diameter and radius
        val diameter = min(size.width, size.height)
        val radius = (diameter / 2f) - (strokeWidthPx / 2f) // Radius to the middle of the stroke


        // Calculate available angle and sweep angles based on TOTAL values (before animation)
        val totalDrawableAngle = (360f - (2 * finalGapAngle)).coerceAtLeast(0f)
        val targetSweepAngle1 =
            if (totalValue > 0f) (safeValue1 / totalValue) * totalDrawableAngle else 0f
        val targetSweepAngle2 =
            if (totalValue > 0f) (safeValue2 / totalValue) * totalDrawableAngle else 0f

        // Calculate animated sweep angles based on progress
        val animatedSweepAngle1 = targetSweepAngle1 * animationProgress.value
        val animatedSweepAngle2 = targetSweepAngle2 * animationProgress.value

        // Calculate start angle for the second segment
        val startAngle2 = startAngle + animatedSweepAngle1 + finalGapAngle

        // Define drawing area for arcs
        val inset = strokeWidthPx / 2f
        val arcSize =
            Size((diameter - 2 * inset).coerceAtLeast(0f), (diameter - 2 * inset).coerceAtLeast(0f))
        val topLeftOffset =
            Offset(inset + (size.width - diameter) / 2f, inset + (size.height - diameter) / 2f)

        // Draw the arc for value 1
        if (animatedSweepAngle1 > 0.01f) { // Draw only if angle is large enough to be visible
            drawArc(
                color = colorX,
                startAngle = startAngle,
                sweepAngle = animatedSweepAngle1,
                useCenter = false,
                topLeft = topLeftOffset,
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt), // Rounded ends
            )
        }

        // Draw the arc for value 2
        if (animatedSweepAngle2 > 0.01f) { // Draw only if angle is large enough to be visible
            drawArc(
                color = colorY,
                startAngle = startAngle2,
                sweepAngle = animatedSweepAngle2,
                useCenter = false,
                topLeft = topLeftOffset,
                size = arcSize,
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt), // Rounded ends
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZPieChart() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            ZAmountValuePieChart(
                modifier = Modifier.size(60.dp), // Size of the chart
                valueX = 133330.00,
                valueY = 483843.00,
                colorX = Color(0xFF309BF2), // Medium Purple (similar to image)
                colorY = Color(0xFFA76AF6), // Cornflower Blue (similar to image)
                strokeWidth = 10.dp,       // Adjust thickness
                gapAngle = 1f,
                animate = false,
            )
        }
    }
}
