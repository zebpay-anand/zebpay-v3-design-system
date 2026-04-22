package com.zebpay.ui.designsystem.v3.utils

import androidx.annotation.FloatRange
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient

fun TextStyle.bold() = this.copy(fontWeight = FontWeight.Bold)

fun TextStyle.semiBold() = this.copy(fontWeight = FontWeight.SemiBold)

fun TextStyle.medium() = this.copy(fontWeight = FontWeight.Medium)

fun TextStyle.regular() = this.copy(fontWeight = FontWeight.Normal)

fun TextStyle.letterSpacing(spacing: TextUnit) = this.copy(letterSpacing = spacing)

fun TextStyle.lineHeight(lineHeight: TextUnit) = this.copy(lineHeight = lineHeight)

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }
}

@Composable
fun Modifier.safeClickable(
    enabled: Boolean = true,
    role: Role = Role.Button,
    tagId: String = "",
    debounceMillis: Long = 500,
    indication: Indication? = LocalIndication.current,
    interactionSource:MutableInteractionSource  = remember { MutableInteractionSource() },
    onClick: () -> Unit,
): Modifier {
    val haptic = LocalHapticFeedback.current
    var lastClickTime by remember { mutableLongStateOf(0L) }
    return if (enabled) {
        this.then(
            Modifier
                .clickable(
                    onClick = {
                        if (System.currentTimeMillis() - lastClickTime > debounceMillis) {
                            lastClickTime = System.currentTimeMillis()
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onClick.invoke()
                        }
                    },
                    indication = indication,
                    interactionSource = interactionSource,
                    role = role,
                )
                .testTag(tagId).semantics{
                    testTagsAsResourceId = true
                },
        )
    } else {
        this // no clickable at all, allows parent to receive clicks
    }
}

fun Color.alpha(@FloatRange(from = 0.0, to = 100.0) alpha: Float) = this.copy(alpha = alpha)

fun Color.asZGradient(): ZGradient = ZGradient.Linear(listOf(0f to this, 1f to this))

fun Float.toRadians(): Float = (this * Math.PI / 180).toFloat()

fun Modifier.dottedVerticalLine(
    color: Color,
    strokeWidth: Dp = 1.dp,
    dotSpacing: Dp = 6.dp,
    dashHeight: Float = 20f,
    offsetStart: Pair<Float, Float> = Pair(56f, 18f),
    offsetEnd: Pair<Float, Float> = Pair(56f, 18f),

    ) = this.drawWithContent {
    drawContent()
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashHeight, dotSpacing.toPx()))
    val paint = Paint().apply {
        this.color = color
        this.pathEffect = pathEffect
        this.strokeWidth = strokeWidth.toPx()
        this.style = PaintingStyle.Stroke
    }
    drawIntoCanvas { canvas ->
        canvas.drawLine(
            p1 = Offset(offsetStart.first, offsetStart.second),
            p2 = Offset(offsetEnd.first, size.height - (offsetEnd.second)),
            paint = paint,
        )
    }
}