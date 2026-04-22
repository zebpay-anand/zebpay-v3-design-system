package com.zebpay.ui.v3.components.atoms.shimmer

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ShineHolder(
    animate: Boolean,
    modifier: Modifier = Modifier,
    duration: Int = 2000,
    delay: Int = 1000,
    barWidth: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    val density = LocalDensity.current
    val animator = rememberInfiniteTransition(label = "infinite")
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    Box(
        modifier.onGloballyPositioned {
            size = it.size
        },
        contentAlignment = Alignment.Center,
    ) {
        val maxWith = size.width
        val startPosition = with(density) {
            (barWidth * 10).toPx()
        }
        val anim = animator.animateFloat(
            initialValue = - maxWith / 3f - startPosition,
            targetValue = (maxWith + startPosition),
            label = "shimmer",
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = delay,
                    easing = LinearOutSlowInEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
        )
        Bar(
            barHeight = with(density) {
                size.height.toDp()
            },
            barWidth = barWidth,
            modifier = Modifier
                .alpha(if (animate) 1f else 0f)
                .conditional(animate) {
                    graphicsLayer {
                        translationX = anim.value
                    }
                },
        )
        content()
    }
}

@Composable
private fun Bar(
    barHeight: Dp, barWidth: Dp, modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
            .height(barHeight)
            .width(barWidth * 2),
        onDraw = {
            val cHeight = size.height
            val cWidth = size.width
            drawLine(
                start = Offset(x = cWidth, y = -16f),
                end = Offset(
                    x = 0f,
                    y = with(density) {
                        (cHeight.toDp() + 10.dp).toPx()
                    },
                ),
                strokeWidth = with(density) {
                    barWidth.toPx()
                },
                color = Color.White.copy(alpha = 0.4f),
            )
        },
    )
}

@Preview
@Composable
private fun PreviewShimmerButton() {
    ZebpayTheme {
        ShineHolder(true, modifier = Modifier.background(gradient = ZGradientColors.Primary01)) {
            Text(text = "Hello There")
        }
    }
}