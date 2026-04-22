package com.zebpay.ui.v3.components.atoms.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.Shapes
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional


@Composable
fun ShimmerCard(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = ZTheme.color.card.fill,
    shapes: CornerBasedShape = ZTheme.shapes.small,
    shimmer: ShimmerState = rememberShimmerState(),
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)
        .background(ZTheme.color.inputField.backgroundDefault)) {
        Box(modifier = Modifier.alpha(0f)) {
            content()
        }
        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .shimmer(shape = shapes, color = color, shimmerState = shimmer),
        )
    }
}


@Composable
fun ShimmerCard(
    modifier: Modifier = Modifier,
    color: Color = ZTheme.color.card.fill,
    showBackground: Boolean=false,
    background: Color = ZTheme.color.inputField.backgroundDefault,
    shapes: CornerBasedShape = ZTheme.shapes.small,
    shimmer: ShimmerState = rememberShimmerState(),
    content: @Composable () -> Unit,
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)
        .conditional(showBackground){
            background(background)
        }) {
        Box(modifier = Modifier.alpha(0f)) {
            content()
        }
        Box(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .shimmer(shape = shapes, color = color, shimmerState = shimmer),
        )
    }
}

@ThemePreviews
@Composable
private fun ShimmerCardPreview() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)){
        ShimmerCard(
            content = {
                Spacer(modifier = Modifier.fillMaxWidth().height(90.dp))
            },
        )
    }
}