package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.v3.components.atoms.field.ZFieldDecorationItem
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZTextButtonColor
import com.zebpay.ui.v3.components.utils.LocalZDecorationItemColor
import com.zebpay.ui.v3.components.utils.ZDecorationItemColor

/**
 * Defines the colors used by a [ZInputField] in different states.
 * Mimics the structure of TextFieldColors.
 */
@Stable
interface ZPinFieldColors {

    /** Represents the color used for the border indicator. */
    @Composable
    fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the background. */
    @Composable
    fun backgroundColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the input field text. */
    @Composable
    fun textColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the top text label. */
    @Composable
    fun labelTopColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the top icon label. */
    @Composable
    fun topIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the bottom text (helper/error). */
    @Composable
    fun labelBottomColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the bottom text (helper/error). */
    @Composable
    fun bottomIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the bottom CTA text. */
    @Composable
    fun ctaTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<ZTextButtonColor>
}


/**
 * Default colors for [ZPinField]. Uses ZTheme colors as a base.
 */
object ZPinFieldDefaults {

    val LabelBottomPadding = 16.dp
    val FieldBottomPadding = 8.dp

    // Default shape
    val shape: Shape @Composable get() = ZTheme.shapes.full

    @Composable
    fun colors(): ZPinFieldColors = DefaultZPinFieldColors(
        focusedTextColor = ZTheme.color.text.primary,
        focusedBorderColor = ZTheme.color.inputField.borderActive,
        focusedBackgroundColor = ZTheme.color.inputField.backgroundDefault,
        focusedLabelColor = ZTheme.color.text.primary,
        focusedLabelIcon = ZTheme.color.icon.singleTonePrimary,
        focusedTextBottomColor = ZTheme.color.text.secondary,
        focusedBottomLabelIcon = Color.Unspecified,
        focusedCtaTextColor = ZTextButtonColor.Blue,
        defaultTextColor = ZTheme.color.text.primary,
        defaultBorderColor = ZTheme.color.inputField.borderDefault,
        defaultBackgroundColor = ZTheme.color.inputField.backgroundDefault,
        defaultLabelColor = ZTheme.color.text.primary,
        defaultLabelIcon = ZTheme.color.icon.singleTonePrimary,
        defaultTextBottomColor = ZTheme.color.text.secondary,
        defaultBottomLabelIcon = Color.Unspecified,
        defaultCtaTextColor = ZTextButtonColor.Blue,
        disabledTextColor = ZTheme.color.text.disabled,
        disabledBorderColor = ZTheme.color.inputField.borderDisabled,
        disabledBackgroundColor = ZTheme.color.inputField.backgroundDisabled,
        disabledLabelColor = ZTheme.color.text.primary,
        disabledLabelIcon = ZTheme.color.icon.singleToneDisable,
        disabledTextBottomColor = ZTheme.color.text.secondary,
        disabledBottomLabelIcon = Color.Unspecified,
        disabledCtaTextColor = ZTextButtonColor.Grey,
        errorTextColor = ZTheme.color.text.primary,
        errorBorderColor = ZTheme.color.inputField.borderError,
        errorBackgroundColor = ZTheme.color.inputField.backgroundDefault,
        errorLabelColor = ZTheme.color.text.primary,
        errorLabelIcon = ZTheme.color.icon.singleTonePrimary,
        errorTextBottomColor = ZTheme.color.text.red,
        errorBottomLabelIcon = Color.Unspecified,
        errorCtaTextColor = ZTextButtonColor.Blue,
    )
}


/**
 * Default implementation of ZInputFieldColors.
 */
@Immutable
private class DefaultZPinFieldColors(
    // Focused state colors
    private val focusedTextColor: Color,
    private val focusedBorderColor: Color,
    private val focusedBackgroundColor: Color,
    private val focusedLabelColor: Color,
    private val focusedLabelIcon: Color,
    private val focusedTextBottomColor: Color,
    private val focusedBottomLabelIcon: Color,
    private val focusedCtaTextColor: ZTextButtonColor,
    // Unfocused/Default state colors
    private val defaultTextColor: Color,
    private val defaultBorderColor: Color,
    private val defaultBackgroundColor: Color,
    private val defaultLabelColor: Color,
    private val defaultLabelIcon: Color,
    private val defaultTextBottomColor: Color,
    private val defaultBottomLabelIcon: Color,
    private val defaultCtaTextColor: ZTextButtonColor,
    // Disabled state colors
    private val disabledTextColor: Color,
    private val disabledBorderColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledLabelColor: Color,
    private val disabledLabelIcon: Color,
    private val disabledTextBottomColor: Color,
    private val disabledBottomLabelIcon: Color,
    private val disabledCtaTextColor: ZTextButtonColor,
    // Error state colors
    private val errorTextColor: Color,
    private val errorBorderColor: Color,
    private val errorBackgroundColor: Color,
    private val errorLabelColor: Color,
    private val errorLabelIcon: Color,
    private val errorTextBottomColor: Color,
    private val errorBottomLabelIcon: Color,
    private val errorCtaTextColor: ZTextButtonColor,
) : ZPinFieldColors {

    @Composable
    override fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledBorderColor
            isError -> errorBorderColor
            focused -> focusedBorderColor
            else -> defaultBorderColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun backgroundColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledBackgroundColor
            isError -> errorBackgroundColor // Often same as default, but could differ
            focused -> focusedBackgroundColor
            else -> defaultBackgroundColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun textColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledTextColor
            isError -> errorTextColor // Text color might change on error
            else -> {
                val focused by interactionSource.collectIsFocusedAsState()
                if (focused) focusedTextColor else defaultTextColor
            }
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun labelTopColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledLabelColor
            isError -> errorLabelColor
            focused -> focusedLabelColor
            else -> defaultLabelColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun topIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledLabelIcon
            isError -> errorLabelIcon
            focused -> focusedLabelIcon
            else -> defaultLabelIcon
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun labelBottomColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledTextBottomColor
            isError -> errorTextBottomColor
            focused -> focusedTextBottomColor
            else -> defaultTextBottomColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun bottomIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledBottomLabelIcon
            isError -> errorBottomLabelIcon
            focused -> focusedBottomLabelIcon
            else -> defaultBottomLabelIcon
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun ctaTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<ZTextButtonColor> {
        val targetColor = when {
            !enabled -> disabledCtaTextColor
            isError -> errorCtaTextColor // CTA might change color on error
            else -> {
                val focused by interactionSource.collectIsFocusedAsState()
                if (focused) focusedCtaTextColor else defaultCtaTextColor
            }
        }
        return rememberUpdatedState(targetColor)
    }

}


@Composable
fun ZPinField(
    maxLength: Int,
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    isMasked: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    bottom: @Composable (() -> Unit)? = null,
    footer: @Composable (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZPinFieldDefaults.shape,
    colors: ZPinFieldColors = ZPinFieldDefaults.colors(),
    boxSize: DpSize = DpSize(50.dp, 56.dp),
    borderWidth: Dp = 1.dp,
) {

    var isFocused = interactionSource.collectIsFocusedAsState().value
    val focusManager = LocalFocusManager.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val imeBottom = WindowInsets.ime.getBottom(LocalDensity.current)
    // Track previous keyboard state
    var wasKeyboardVisible by remember { mutableStateOf(false) }

    // Watch keyboard visibility via ime bottom inset
    LaunchedEffect(imeBottom) {
        val keyboardVisible = imeBottom > 0
        if (!keyboardVisible && wasKeyboardVisible && isFocused) {
            focusManager.clearFocus()
        }
        wasKeyboardVisible = keyboardVisible
    }
    val hasText = value.isNotEmpty()
    // Resolve colors based on state
    val textColor = colors.textColor(enabled, isError, interactionSource).value
    val mergedTextStyle = textStyle.copy(color = textColor)
    val borderColor = colors.borderColor(enabled, isError, interactionSource).value
    val backgroundColor = colors.backgroundColor(enabled, isError, interactionSource).value


    Column(modifier = modifier) {
        if (label != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                val labelTopColor = colors.labelTopColor(enabled, isError, interactionSource).value
                val topIconColor = colors.topIconColor(enabled, isError, interactionSource).value
                CompositionLocalProvider(
                    LocalContentColor provides topIconColor,
                    LocalTextStyle provides ZTheme.typography.bodyRegularB2.copy(
                        color = labelTopColor,
                        fontWeight = FontWeight.SemiBold,
                    ),
                ) {
                    label()
                }
            }
            Spacer(modifier = Modifier.height(ZPinFieldDefaults.LabelBottomPadding))
        }
        CompositionLocalProvider(
            LocalZDecorationItemColor provides ZDecorationItemColor(
                textColor = textColor,
                iconColor = textColor,
                background = backgroundColor,
                border = borderColor,
            ),
            LocalTextStyle provides textStyle,
        ) {
            BasicTextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                enabled = enabled,
                readOnly = readOnly,
                value = value,
                textStyle = mergedTextStyle,
                onValueChange = {
                    if (it.isBlank())
                        onValueChange("")
                    else if (it.length <= maxLength && it.toIntOrNull() != null) {
                        onValueChange(it)
                    }
                },
                interactionSource = interactionSource,
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDone.invoke()
                    },
                ),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        repeat(maxLength) { index ->
                            val char = value.getOrNull(index)?.toString() ?: ""
                            ZFieldDecorationItem(
                                char = char,
                                masked = isMasked,
                                shape = shape,
                                borderWidth = borderWidth,
                                size = boxSize,
                            )
                        }
                    }
                },
            )
        }

        if (bottom != null) {
            Spacer(modifier = Modifier.height(ZPinFieldDefaults.FieldBottomPadding))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                val labelBottomColor =
                    colors.labelBottomColor(enabled, isError, interactionSource).value
                val bottomIconColor =
                    colors.bottomIconColor(enabled, isError, interactionSource).value
                val ctaTextColor = colors.ctaTextColor(enabled, isError, interactionSource).value
                CompositionLocalProvider(
                    LocalContentColor provides bottomIconColor,
                    LocalTextStyle provides ZTheme.typography.bodyRegularB3.copy(
                        color = labelBottomColor,
                    ),
                    LocalZTextButtonColor provides ctaTextColor,
                ) {
                    bottom()
                }
            }
            Spacer(modifier = Modifier.height(ZPinFieldDefaults.LabelBottomPadding))
        }
        if (footer != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides Color.Unspecified,
                    LocalTextStyle provides ZTheme.typography.bodyRegularB3.copy(
                        color = ZTheme.color.text.secondary,
                    ),
                    LocalZTextButtonColor provides ZTextButtonColor.Blue,
                ) {
                    footer()
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZPinField() {
    ZBackgroundPreviewContainer {
        var pin by remember { mutableStateOf("1234") }
        ZPinField(
            maxLength = 4,
            value = pin,
            textStyle = ZTheme.typography.displayRegularD3.bold(),
            label = {
                Text("Label")
                BlankWidth(4.dp)
                Text("Label")
                BlankWidth(8.dp)
                ZIcon(
                    icon = ZIcons.ic_edit.asZIcon,
                )
            },
            bottom = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    onClick = {},
                    tagId = "",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                )
            },
            footer = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    onClick = {},
                    tagId = "",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                )
            },
            onValueChange = {
                pin = it
            },
            onDone = {

            },
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZPinFieldError() {
    ZBackgroundPreviewContainer {
        var pin by remember { mutableStateOf("1234") }
        ZPinField(
            maxLength = 4,
            value = pin,
            textStyle = ZTheme.typography.displayRegularD3.bold(),
            label = {
                Text("Label")
                BlankWidth(4.dp)
                Text("Label")
                BlankWidth(8.dp)
                ZIcon(
                    icon = ZIcons.ic_edit.asZIcon,
                )
            },
            bottom = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    tagId = "",
                    onClick = {},
                )
            },
            onValueChange = {
                pin = it
            },
            onDone = {

            },
            isError = true,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZPinFieldDisabled() {
    ZBackgroundPreviewContainer {
        var pin by remember { mutableStateOf("1234") }
        ZPinField(
            maxLength = 4,
            value = pin,
            textStyle = ZTheme.typography.displayRegularD3.bold(),
            label = {
                Text("Label")
                BlankWidth(4.dp)
                Text("Label")
                BlankWidth(8.dp)
                ZIcon(
                    icon = ZIcons.ic_edit.asZIcon,
                )
            },
            bottom = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    onClick = {},
                    tagId = "",
                )
            },
            footer = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    onClick = {},
                    tagId = "",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                )
            },
            enabled = false,
            onValueChange = {
                pin = it
            },
            onDone = {

            },
            isError = true,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZPinFieldMasked() {
    ZBackgroundPreviewContainer {
        var pin by remember { mutableStateOf("1234") }
        ZPinField(
            maxLength = 4,
            value = pin,
            textStyle = ZTheme.typography.displayRegularD3.bold(),
            label = {
                Text("Label")
                BlankWidth(4.dp)
                Text("Label")
                BlankWidth(8.dp)
                ZIcon(
                    icon = ZIcons.ic_edit.asZIcon,
                )
            },
            bottom = {
                ZIcon(
                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                )
                BlankWidth(4.dp)
                Text("Text 1")
                BlankWidth(4.dp)
                ZTextButton(
                    title = "CTA",
                    tagId = "",
                    onClick = {},
                )
            },
            enabled = true,
            onValueChange = {
                pin = it
            },
            onDone = {

            },
            isMasked = true,
        )
    }
}