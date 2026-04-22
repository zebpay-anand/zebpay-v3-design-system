package com.zebpay.ui.v3.components.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer

fun bottomSheetMorphShape(): Shape = GenericShape { size, _ ->
    val width = size.width
    val height = size.height
    val radius = 150f
    val circleCenterX = width / 2
    moveTo(0f, radius)
    // Left to before arc
    lineTo(circleCenterX - radius, radius)
    // Arc top center
    arcTo(
        rect = Rect(
            left = circleCenterX - radius,
            top = radius - radius,
            right = circleCenterX + radius,
            bottom = radius + radius,
        ),
        startAngleDegrees = -180f,
        sweepAngleDegrees = 180f,
        forceMoveTo = false,
    )
    // Arc to top right
    lineTo(width, radius)
    // Down to bottom right
    lineTo(width, height)
    // Bottom left
    lineTo(0f, height)
    // Back to start
    close()
}


@ThemePreviews
@Composable
fun PreviewZBottomSheetGradientHeader() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(bottomSheetMorphShape())
                .background(ZGradientColors.Green01, shape = bottomSheetMorphShape()),
        )
    }
}
