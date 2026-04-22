package com.zebpay.ui.v3.components.atoms.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme

@Immutable
class ShimmerState internal constructor(val state: State<Float>)

@Composable
fun rememberShimmerState(durationMillis: Int = 800): ShimmerState {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val alpha = transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
        label = "alpha",
    )
    return ShimmerState(alpha)
}

fun Modifier.shimmer(
    shape: Shape = RoundedCornerShape(0.dp),
    color: Color? = null,
    shimmerState: ShimmerState? = null,
) = composed {
    val state = shimmerState ?: rememberShimmerState()
    clip(shape)
        .background(
            Brush.linearGradient(
                listOf(Color.Transparent) +
                        (color?:ZTheme.color.card.selected),
                start = Offset.Zero,
                end = Offset(
                    x = state.state.value,
                    y = state.state.value,
                ),
            ),
            shape,
        )
}