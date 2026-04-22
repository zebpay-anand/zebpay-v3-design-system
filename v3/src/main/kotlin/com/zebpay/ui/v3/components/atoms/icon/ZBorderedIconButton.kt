package com.zebpay.ui.v3.components.atoms.icon

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ZBorderedIconBox(
    size: Dp = 44.dp,
    width: Dp = 1.dp,
    icon: ZIcon,
    borderColor: androidx.compose.ui.graphics.Color = ZTheme.color.card.border,
    background : ZGradient = ZTheme.color.buttons.secondary.fillActive.asZGradient(),
    enabled: Boolean,
    shape: androidx.compose.ui.graphics.Shape = ZTheme.shapes.medium,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(ZTheme.shapes.medium)
            .border(
                color = borderColor,
                width = width,
                shape = shape
            )
            .background(background)
            .safeClickable(enabled = enabled) {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center,
    ) {
        ZGradientIcon(
            icon = icon,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZBorderedIconBox() {
    ZBackgroundPreviewContainer {
        ZBorderedIconBox(
            icon = ZIcons.ic_currency_euro.asZIcon,
            onClick = {},
            enabled = true
        )
    }
}