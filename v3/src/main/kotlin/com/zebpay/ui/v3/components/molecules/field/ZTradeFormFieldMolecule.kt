package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
fun ZTradeFormField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textAlign: TextAlign = TextAlign.End,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZInputFieldDefaults.shape,
    colors: ZInputFieldColors = ZInputFieldDefaults.colors(
        errorLeadingTextColor = ZTheme.color.text.red,
    ),
    borderWidth: Dp = 1.dp, // Default border width
) {
    ZInputField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        placeholder = placeholder,
        leading = leading,
        trailing = trailing,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textAlign = textAlign,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        borderWidth = borderWidth,
    )
}


@ThemePreviews
@Composable
fun PreviewZTradeFromField() {
    ZBackgroundPreviewContainer {
        var text1 by remember { mutableStateOf("0.00") }
        ZTradeFormField(
            value = text1,
            onValueChange = { text1 = it },
            placeholder = {
                Text("0.0")
            },
            leading = {
                Text("Label")
                ZIcon(
                    icon = ZIcons.ic_info.asZIcon,
                )
                ZIconButton(
                    icon = ZIcons.ic_arrow_down.asZIcon,
                    onClick = {},
                )
            },
            trailing = {
                Text("USDT")
                ZIconButton(
                    icon = ZIcons.ic_arrow_down.asZIcon,
                    onClick = {},
                )
            },
            enabled = true,
            isError = false,
            textStyle = ZTheme.typography.bodyRegularB3,
        )
    }
}