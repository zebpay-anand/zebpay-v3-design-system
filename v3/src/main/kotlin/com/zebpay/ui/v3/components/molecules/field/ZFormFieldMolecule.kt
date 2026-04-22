package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.molecules.button.ZTextButton

@Composable
fun ZFormField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    textTop: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    textBottom: @Composable (() -> Unit)? = null,
    cta: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZInputFieldDefaults.shape,
    colors: ZInputFieldColors = ZInputFieldDefaults.colors(),
    borderWidth: Dp = 1.dp, // Default border width
) {
    ZInputField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = false,
        maxLines = maxLines,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        textTop = textTop,
        textBottom = textBottom,
        cta = cta,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        interactionSource = interactionSource,
        shape = shape,
        colors = colors,
        borderWidth = borderWidth,
    )
}


@ThemePreviews
@Composable
private fun ZFormFieldPreview() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("Text") }
    var text3 by remember { mutableStateOf("Text") }
    var text4 by remember { mutableStateOf("") }
    var text5 by remember { mutableStateOf("Text") }


    ZBackgroundPreviewContainer {
        // Example 1: Basic with Label and Placeholder
        ZFormField(
            value = text1,
            onValueChange = { text1 = it },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Label") },
            placeholder = { Text("Placeholder") },
            textTop = { Text("Text Top") },
            textBottom = { Text("Helper text") },
            cta = {
                ZTextButton(
                    title = "CTA",
                    tagId = "form-id",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )

        // Example 2: Filled with Icons
        ZFormField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Label") },
            textStyle = ZTheme.typography.bodyRegularB3,
            textBottom = { Text("Helper text") },
        )

        // Example 3: Error State
        ZFormField(
            value = text3,
            onValueChange = { text3 = it },
            label = { Text("Label") },
            textStyle = ZTheme.typography.bodyRegularB3,
            isError = true,
            textBottom = {
                Text(
                    "This is an error message ",
                )
            },
            textTop = { Text("Text Top") },
            cta = {
                ZTextButton(
                    title = "CTA",
                    tagId = "form-id",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )

        // Example 4: Focused State (Simulate focus in Preview is tricky, shows default active colors)
        ZFormField(
            value = text4,
            onValueChange = { text4 = it },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Label (Focus)") },
            placeholder = { Text("Type here...") },
            interactionSource = remember { MutableInteractionSource() }, // Separate source
        )

        // Example 5: Disabled State
        ZFormField(
            value = text5,
            onValueChange = { text5 = it },
            label = {
                Text("Label")
            },
            textStyle = ZTheme.typography.bodyRegularB3,
            enabled = false,
            textBottom = {
                Text("This field is disabled")
            },
        )
    }

}