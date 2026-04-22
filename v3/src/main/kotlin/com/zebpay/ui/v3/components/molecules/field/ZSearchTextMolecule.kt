package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
fun ZSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search",
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    ZInputField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = ZTheme.typography.bodyRegularB3,
        leading = {
            ZIcon(
                icon = ZIcons.ic_search.asZIcon,
                contentDescription = null,
            )
        },
        trailing = {
            AnimatedVisibility(value.isNotEmpty()) {
                ZIconButton(
                    icon = ZIcons.ic_cross.asZIcon,
                    onClick = { onValueChange.invoke("") },
                    contentDescription = null,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone.invoke()
            },
        ),
        placeholder = { Text(placeholder) },
        interactionSource = interactionSource, // Separate source
    )
}

@ThemePreviews
@Composable
private fun ZInputFieldPreview() {
    var searchTxt by remember { mutableStateOf("") }

    ZBackgroundPreviewContainer {
        ZSearchField(
            value = searchTxt,
            onValueChange = {
                searchTxt = it
            },
            onDone = {

            },
        )
        ZSearchField(
            value = "Bitcoin",
            onValueChange = {
                searchTxt = it
            },
            onDone = {

            },
        )
    }
}