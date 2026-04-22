package com.zebpay.ui.v3.components.atoms.misc

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ZIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 6.dp,
    activeDotWidth: Dp = 30.dp,
    spacing: Dp = 4.dp,
    activeColors: ZGradient = ZTheme.color.graphics.badgeActive,
    inactiveColor: ZGradient = ZTheme.color.graphics.badgeDefault.asZGradient(),
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        for (i in 0 until totalDots) {
            val isSelected = i == selectedIndex

            val width by animateDpAsState(
                targetValue = if (isSelected) activeDotWidth else dotSize,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
                label = "dotWidth",
            )

            val gradientColor = if (isSelected) {
                activeColors
            } else {
                inactiveColor
            }

            Box(
                modifier = Modifier
                    .height(dotSize)
                    .width(width)
                    .clip(ZTheme.shapes.full)
                    .background(gradient = gradientColor, shape = ZTheme.shapes.full),
            )
        }
    }
}
