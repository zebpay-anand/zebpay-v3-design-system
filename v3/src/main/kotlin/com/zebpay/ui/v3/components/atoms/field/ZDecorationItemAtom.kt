package com.zebpay.ui.v3.components.atoms.field

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZDecorationItemColor

@Composable
fun ZFieldDecorationItem(
    char: String = "",
    textStyle: TextStyle = LocalTextStyle.current,
    textColor: Color = LocalZDecorationItemColor.current.textColor,
    background: Color = LocalZDecorationItemColor.current.background,
    border: Color = LocalZDecorationItemColor.current.border,
    masked: Boolean = false,
    shape: Shape = ZTheme.shapes.full,
    size: DpSize = DpSize(50.dp, 56.dp),
    borderWidth: Dp = 1.dp,
    contentPadding: PaddingValues = PaddingValues(12.dp),
) {
    Box(
        modifier = Modifier
            .clip(shape)
            .size(size)
            .background(background)
            .border(width = borderWidth, color = border, shape = shape)
            .padding(contentPadding),
    ) {
        Crossfade(
            targetState = masked,
            modifier = Modifier.align(Alignment.Center),
            label = "value",
        ) {
            when (it) {
                true -> {
                    if (char.isNotBlank()) {
                        ZIcon(
                            icon = ZIcons.ic_asterisk.asZIcon,
                            contentDescription = null,
                            tint = textColor,
                            modifier = Modifier,
                        )
                    }
                }

                false -> Text(
                    text = char,
                    style = textStyle,
                    color = textColor,
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZFieldDecorationItem() {
    ZBackgroundPreviewContainer {
        ZFieldDecorationItem(
            char = "1",
            masked = false,
            textStyle = ZTheme.typography.displayRegularD3,
            textColor = ZTheme.color.text.primary,
            border = ZTheme.color.inputField.borderActive,
            background = ZTheme.color.inputField.backgroundDefault,
        )

        ZFieldDecorationItem(
            char = "1",
            masked = true,
            textStyle = ZTheme.typography.displayRegularD3,
            textColor = ZTheme.color.text.primary,
            border = ZTheme.color.inputField.borderActive,
            background = ZTheme.color.inputField.backgroundDefault,
        )
    }
}