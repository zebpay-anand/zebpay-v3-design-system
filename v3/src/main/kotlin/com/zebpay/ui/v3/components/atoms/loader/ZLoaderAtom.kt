package com.zebpay.ui.v3.components.atoms.loader

import android.annotation.SuppressLint
import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.components.utils.background
import kotlin.random.Random

@Composable
fun ZCircularLoader(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth,
    color: Color = ZTheme.color.navigation.top.onSolidIconBg,
    trackColor: Color = ZTheme.color.icon.singleToneWhite,
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color,
        strokeWidth = strokeWidth,
        trackColor = trackColor,
        strokeCap = ProgressIndicatorDefaults.CircularIndeterminateStrokeCap,
    )
}

@ThemePreviews
@Composable
private fun PreviewZCircularLoader() {
    ZebpayTheme {
        ZCircularLoader(
            modifier = Modifier
                .padding(16.dp)
                .size(64.dp),
            strokeWidth = 4.dp,
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ZHorizontalProgress(
    @FloatRange(0.0, 1.0)
    progress: Float,
    modifier: Modifier = Modifier,
    shape: Shape = ZTheme.shapes.medium,
    barHeight: Dp = 8.dp,
    trackColor: Color = ZTheme.color.icon.singleToneWhite,
    foregroundColor: ZGradient = ZTheme.color.icon.dualToneGradient,
) {
    val density = LocalDensity.current
    var width by remember {
        mutableStateOf(0.dp)
    }
    val animateDp by animateDpAsState(
        targetValue = width,
        animationSpec = tween(500, easing = LinearEasing),
        label = "animation",
    )
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height = barHeight)
            .clip(shape = shape)
            .background(color = trackColor),
    ) {
        width = with(density) {
            (maxWidth.toPx() * progress).toDp()
        }
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .width(animateDp)
                .clip(shape = shape)
                .background(gradient = foregroundColor, shape = shape),
        )
    }
}

@Preview
@Composable
private fun PreviewZHorizontalProgress() {
    ZebpayTheme {
        var progress by remember {
            mutableFloatStateOf(.5f)
        }
        ZHorizontalProgress(
            progress,
            modifier = Modifier.clickable {
                progress = Random.nextFloat()
            },
        )
    }
}


//@Composable
//fun GradientCircularProgressIndicator(
//    modifier: Modifier = Modifier,
//    strokeWidth: Dp = 4.dp,
//    colors: ZGradient = ZGradientColors.Primary03
//) {
//    val transition = rememberInfiniteTransition()
//    val startAngle by transition.animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1200, easing = FastOutSlowInEasing)
//        )
//    )
//    val sweepAngle by transition.animateFloat(
//        initialValue = 30f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1200, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//    Canvas(
//        modifier = modifier
//    ) {
//        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
//        // Optional background circle
//        drawArc(
//            color = Color.White.copy(alpha = 0.5f),
//            startAngle = 0f,
//            sweepAngle = 360f,
//            useCenter = false,
//            style = stroke
//        )
//
//        // Gradient arc
//        drawArc(
//            brush = colors.toBrush(),
//            startAngle = startAngle,
//            sweepAngle = sweepAngle,
//            useCenter = false,
//            style = stroke
//        )
//    }
//}
