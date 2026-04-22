package com.zebpay.ui.v3.components.utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * A shape for the bottom navigation bar with a circular cutout in the top center.
 *
 * @param fabDiameter The diameter of the FAB that will sit in the cutout.
 * @param cutoutMargin The margin between the FAB and the edge of the cutout curve.
 */
class BottomNavCutoutShape(
    private val fabDiameter: Dp,
    private val cutoutMargin: Dp = 32.dp, // Space around FAB
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val fabDiameterPx = with(density) { fabDiameter.toPx() }
        val cutoutMarginPx = with(density) { cutoutMargin.toPx() }

        val cutoutRadius = (fabDiameterPx / 2f) + cutoutMarginPx
        val cutoutCenterX = size.width / 2f

        val cutoutWidth = cutoutRadius * 2f
        val curveControlYOffset = cutoutRadius * 0.20f // Adjust 0.4f for deeper/shallow curve

        val startX = cutoutCenterX - cutoutRadius
        val endX = cutoutCenterX + cutoutRadius

        val path = Path().apply {
            moveTo(0f, 0f)
            lineTo(startX, 0f)

            // Left curve (from startX to center)
            cubicTo(
                startX + cutoutRadius * 0.25f,
                0f, // Control point closer to start
                cutoutCenterX - cutoutRadius * 0.5f,
                curveControlYOffset, // Control point going down
                cutoutCenterX,
                curveControlYOffset, // Mid point (bottom of dip)
            )

            // Right curve (from center to endX)
            cubicTo(
                cutoutCenterX + cutoutRadius * 0.5f, curveControlYOffset, // Control point going up
                endX - cutoutRadius * 0.25f, 0f, // Control point closer to end
                endX, 0f,
            )

            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        return Outline.Generic(path)
    }
}