package com.zebpay.ui.v3.components.atoms.seperator

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ZHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}

@Composable
fun ZHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    onGradient: Boolean = false,
) {
    val color = if (onGradient) {
        ZTheme.color.separator.solidOnGradient
    } else {
        ZTheme.color.separator.solidDefault
    }
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}


@Composable
fun ZGradientDivider(
    modifier: Modifier = Modifier,
    color: ZGradient = ZTheme.color.separator.gradientDefault,
) {
    Spacer(
        modifier = modifier
            .clip(CircleShape)
            .background(color, shape = CircleShape),
    )
}


@ThemePreviews
@Composable
fun PreviewZDivider() {
    ZBackgroundPreviewContainer {
        ZHorizontalDivider(
            modifier = Modifier.width(100.dp),
        )
        ZHorizontalDivider(
            modifier = Modifier.width(100.dp),
            onGradient = true,
        )
        ZGradientDivider(
            modifier = Modifier.width(100.dp)
                .height(1.dp),
        )
    }
}