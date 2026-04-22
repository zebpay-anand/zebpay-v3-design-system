package com.zebpay.ui.v3.components.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RSRuntimeException
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import kotlin.math.max


/**
 * Applies a blur effect to the Composable this Modifier is applied to,
 * only if the [applyBlur] condition is true and [radius] is greater than 0.dp.
 *
 * This uses the standard `Modifier.blur()` which handles API level differences
 * (using RenderEffect on API 31+ and an approximation on older APIs without
 * using deprecated methods).
 *
 * @param applyBlur A boolean condition determining whether to apply the blur.
 * @param radius The radius of the blur. If 0.dp or less, or if [applyBlur] is false,
 *               no blur is applied.
 * @param edgeTreatment Defines how to handle edges when blurring. Defaults to
 *                      `BlurredEdgeTreatment.Rectangle`.
 * @return A Modifier that conditionally applies a blur.
 */
fun Modifier.conditionalBlur(
    applyBlur: Boolean,
    radius: Dp,
    edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangle, // Default from Modifier.blur
): Modifier = this.then(
    // 'this' refers to the Modifier it's called on
    if (applyBlur && radius > 0.dp) {
        // If condition is met and radius is positive, apply the blur
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Modifier.blur(
                radius = radius,
                edgeTreatment = edgeTreatment,
            )
        } else {
            Modifier.legacyFakeRadialBlurBackground(
                applyFakeBlur = true,
                averageColor = Color(0xE61A222C),
            )
        }
    } else {
        // Otherwise, return an empty Modifier, which means no change
        Modifier // same as Modifier.then(Modifier) or just Modifier
    },
)



/**
 * Applies a "fake" blur effect using radial gradients for visual approximation,
 * suitable for backgrounds on older APIs where Modifier.blur() might not be
 * visually prominent or performant.
 *
 * This is NOT a true Gaussian blur but aims to soften the background.
 *
 * @param applyFakeBlur Condition to apply this effect.
 * @param averageColor The dominant or average color of the content BEHIND this modifier.
 *                     This is crucial for the fake blur to look somewhat convincing.
 * @param intensity A factor from 0.0 (no effect) to 1.0 (strongest effect).
 *                  Controls how far the "blur" gradient spreads.
 * @param steps The number of color stops in the gradient. More steps can create a
 *              smoother transition but might be slightly more expensive.
 */
fun Modifier.legacyFakeRadialBlurBackground(
    applyFakeBlur: Boolean,
    averageColor: Color, // The color to "smear"
    intensity: Float = 1f, // 0.0 to 1.0
    steps: Int = 30,
): Modifier = this.then(
    if (applyFakeBlur && intensity > 0.0f) {
        Modifier.drawWithCache {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height / 2f)

            // Max radius for the gradient spread, influenced by intensity
            val maxGradientRadius = (max(width, height) / 2f) * intensity.coerceIn(0f, 1f)

            if (maxGradientRadius <= 0f) {
                // No real effect to draw, just draw content
                return@drawWithCache onDrawWithContent { drawContent() }
            }

            // Create color stops for the radial gradient
            // It will go from the averageColor to transparent
            val colorStops = mutableListOf<Pair<Float, Color>>()
            val baseAlpha = averageColor.alpha // Preserve original alpha for the core color

            for (i in 0..steps) {
                val fraction = i.toFloat() / steps.toFloat() // 0.0 to 1.0
                // Alpha decreases as we move outwards
                val currentAlpha = baseAlpha * (1f - fraction)
                colorStops.add(fraction to averageColor.copy(alpha = currentAlpha))
            }
            // Ensure the outermost color is fully transparent if averageColor wasn't already
            if (colorStops.last().second.alpha > 0.001f) {
                colorStops.removeAt(colorStops.lastIndex)
                colorStops.add(1.0f to averageColor.copy(alpha = 0f))
            }


            val radialBrush = Brush.radialGradient(
                colorStops = colorStops.toTypedArray(),
                center = center,
                radius = maxGradientRadius,
                tileMode = TileMode.Clamp,
            )

            onDrawWithContent {
                // Draw the original content of the composable this modifier is applied to
                drawContent()
                // Then draw the "fake blur" gradient on top or behind.
                // If drawing on top, it "obscures" the content.
                // If this modifier is on a background element, this gradient will be
                // drawn on that background element.
                drawRect(color = averageColor)
            }
        }
    } else {
        Modifier
    },
)


fun captureViewBitmap(view: View): Bitmap? {
    try {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    } catch (e: Exception) {
        return null
    }
}

/**
 * Applies a blur effect to a bitmap.
 *
 * This function provides an alternative to the deprecated RenderScript API for blurring images.
 * It uses a combination of downscaling, fast blur, and upscaling to achieve a similar effect.
 *
 * @param bitmap The bitmap to blur.
 * @param context The application context.
 * @param radius The blur radius. Higher values result in a stronger blur.
 *               Should be between 0.1f and 25f.
 * @param scaleFactor The factor by which to scale the bitmap down before blurring.
 *                    A higher value results in faster processing but potentially lower quality.
 *                    Should be between 0.1f and 1.0f.
 * @return The blurred bitmap, or the original bitmap if an error occurs.
 */
fun applyBlur(
    bitmap: Bitmap,
    context: Context,
    radius: Float = 25f,
    scaleFactor: Float = 0.2f,
): Bitmap? {
    val rsBlur = RsBlur(context)
    return rsBlur.blur(bitmap, radius, scaleFactor)
}

/**
 * Helper class for applying blur effects using RenderScript or a fallback method.
 */
class RsBlur(private val context: Context) {
    /**
     * Applies a blur effect to a bitmap.
     *
     * This function provides an alternative to the deprecated RenderScript API for blurring images.
     * It uses a combination of downscaling, fast blur, and upscaling to achieve a similar effect.
     *
     * @param bitmap The bitmap to blur.
     * @param radius The blur radius. Higher values result in a stronger blur.
     *               Should be between 0.1f and 25f.
     * @param scaleFactor The factor by which to scale the bitmap down before blurring.
     *                    A higher value results in faster processing but potentially lower quality.
     *                    Should be between 0.1f and 1.0f.
     * @return The blurred bitmap, or the original bitmap if an error occurs.
     */
    fun blur(bitmap: Bitmap, radius: Float, scaleFactor: Float): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurRenderScript(bitmap, radius)
        } else {
            fastBlur(bitmap, radius, scaleFactor)
        }
    }

    /**
     * Applies a blur effect to a bitmap using RenderScript.
     *
     * @param bitmap The bitmap to blur.
     * @param radius The blur radius. Higher values result in a stronger blur.
     *               Should be between 0.1f and 25f.
     * @return The blurred bitmap, or the original bitmap if an error occurs.
     */
    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurRenderScript(bitmap: Bitmap, radius: Float): Bitmap? {
        var rs: RenderScript? = null
        var input: Allocation? = null
        var output: Allocation? = null
        var script: ScriptIntrinsicBlur? = null
        try {
            rs = RenderScript.create(context)
            input = Allocation.createFromBitmap(rs, bitmap)
            output = Allocation.createTyped(rs, input.type)
            script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            script.setRadius(radius.coerceIn(0.1f, 25f)) // RenderScript max radius is 25
            script.setInput(input)
            script.forEach(output)
            // Create a new bitmap for the output, or copy to the original
            val blurredBitmap =
                createBitmap(bitmap.width, bitmap.height, bitmap.config!!)
            output.copyTo(blurredBitmap)
            return blurredBitmap
        } catch (e: RSRuntimeException) {
            return fastBlur(bitmap, radius, 0.2f)
        } catch (e: Exception) {
            return null // Return original on error
        } finally {
            // Release RenderScript resources
            input?.destroy()
            output?.destroy()
            script?.destroy()
            rs?.destroy()
        }
    }

    /**
     * Applies a fast blur effect to a bitmap.
     *
     * This method uses a combination of downscaling, simple blur, and upscaling to achieve a blur effect.
     * It's faster than RenderScript but may produce a slightly lower quality result.
     *
     * @param bitmap The bitmap to blur.
     * @param radius The blur radius. Higher values result in a stronger blur.
     * @param scaleFactor The factor by which to scale the bitmap down before blurring.
     *                    A higher value results in faster processing but potentially lower quality.
     * @return The blurred bitmap.
     */
    private fun fastBlur(bitmap: Bitmap, radius: Float, scaleFactor: Float): Bitmap? {
        try {
            val width = (bitmap.width * scaleFactor).toInt()
            val height = (bitmap.height * scaleFactor).toInt()

            val smallBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

            val blurredBitmap = Bitmap.createBitmap(smallBitmap)
            val canvas = Canvas(blurredBitmap)
            val paint = Paint()
            paint.maskFilter =
                android.graphics.BlurMaskFilter(radius, android.graphics.BlurMaskFilter.Blur.NORMAL)
            canvas.drawBitmap(
                smallBitmap,
                Rect(0, 0, smallBitmap.width, smallBitmap.height),
                Rect(0, 0, blurredBitmap.width, blurredBitmap.height),
                paint,
            )

            return Bitmap.createScaledBitmap(blurredBitmap, bitmap.width, bitmap.height, false)
        } catch (e: Exception) {
            return null
        }
    }
}
