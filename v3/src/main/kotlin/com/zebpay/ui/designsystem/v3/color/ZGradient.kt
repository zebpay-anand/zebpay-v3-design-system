package com.zebpay.ui.designsystem.v3.color

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Sealed class representing different gradient types.
 */
sealed class ZGradient {

    abstract fun colorStops(): List<Pair<Float, Color>>
    abstract fun angle(): Float
    abstract fun asColorList(): List<Color>
    abstract fun toTextBrush(): Brush

    /**
     * Linear Gradient with Angle support.
     */
    data class Linear(
        val colorStops: List<Pair<Float, Color>>,
        val angle: Float = 90f,
    ) : ZGradient() {

        override fun colorStops(): List<Pair<Float, Color>> {
            return colorStops
        }

        override fun angle(): Float {
            return angle
        }

        override fun asColorList(): List<Color> {
            return colorStops.map { it.second }
        }

        override fun toTextBrush(): Brush {
            return Brush.horizontalGradient(colorStops = colorStops.toTypedArray())
        }
    }
}
