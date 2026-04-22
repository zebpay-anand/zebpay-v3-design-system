package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZDecorationItemColor
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import com.zebpay.ui.v3.components.utils.LocalZTextButtonColor
import com.zebpay.ui.v3.components.utils.ZDecorationItemColor
import kotlinx.coroutines.launch

/**
 * Defines the colors used by a [ZInputField] in different states.
 * Mimics the structure of TextFieldColors.
 */
@Stable
interface ZInputFieldColors {

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

    /** Represents the color used for the label. */
    @Composable
    fun labelColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the top text next to the label. */
    @Composable
    fun textTopColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the placeholder text. */
    @Composable
    fun placeholderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the leading icon. */
    @Composable
    fun leadingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the trailing icon. */
    @Composable
    fun trailingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the leading icon. */
    @Composable
    fun leadingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the trailing icon. */
    @Composable
    fun trailingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color>

    /** Represents the color used for the bottom text (helper/error). */
    @Composable
    fun textBottomColor(
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
 * Default colors for [ZInputField]. Uses MaterialTheme colors as a base.
 */
object ZInputFieldDefaults {

    // Define default padding values
    val OutlineContentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    val ContentPadding = PaddingValues(horizontal = 0.dp, vertical = 8.dp)
    val IconPadding = 8.dp
    val LabelBottomPadding = 4.dp

    val OutlineTextBottomPadding = 4.dp
    val TextBottomPadding = 2.dp
    val MinHeight = 46.dp // Minimum height for the input field area
    val FormHeight = 80.dp

    // Default shape
    val shape: Shape @Composable get() = ZTheme.shapes.medium // Or RoundedCornerShape(8.dp)

    @Composable
    fun colors(
        // Focused state colors
        focusedTextColor: Color = ZTheme.color.text.primary,
        focusedBorderColor: Color = ZTheme.color.inputField.borderActive,
        focusedBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault, // Or surfaceVariant
        focusedLabelColor: Color = ZTheme.color.text.primary,
        focusedTextTopColor: Color = ZTheme.color.text.secondary,
        focusedPlaceholderColor: Color = ZTheme.color.text.secondary,
        focusedLeadingTextColor: Color = ZTheme.color.text.primary,
        focusedTrailingTextColor: Color = ZTheme.color.text.primary,
        focusedLeadingIconColor: Color = ZTheme.color.icon.singleTonePrimary,
        focusedTrailingIconColor: Color = ZTheme.color.icon.singleTonePrimary,
        focusedTextBottomColor: Color = ZTheme.color.text.secondary,
        focusedCtaTextColor: ZTextButtonColor = ZTextButtonColor.Blue,

        // Unfocused/Default state colors
        unfocusedTextColor: Color = ZTheme.color.text.primary,
        unfocusedBorderColor: Color = ZTheme.color.inputField.borderDefault,
        unfocusedBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault, // Or surfaceVariant
        unfocusedLabelColor: Color = ZTheme.color.text.secondary,
        unfocusedTextTopColor: Color = ZTheme.color.text.secondary,
        unfocusedPlaceholderColor: Color = ZTheme.color.text.secondary,
        unfocusedLeadingTextColor: Color = ZTheme.color.text.primary,
        unfocusedTrailingTextColor: Color = ZTheme.color.text.primary,
        unfocusedLeadingIconColor: Color = ZTheme.color.icon.singleTonePrimary,
        unfocusedTrailingIconColor: Color = ZTheme.color.icon.singleTonePrimary,
        unfocusedTextBottomColor: Color = ZTheme.color.text.secondary,
        unfocusedCtaTextColor: ZTextButtonColor = ZTextButtonColor.Blue,

        // Disabled state colors
        disabledTextColor: Color = ZTheme.color.text.disabled,
        disabledBorderColor: Color = ZTheme.color.inputField.borderDisabled,
        disabledBackgroundColor: Color = ZTheme.color.inputField.backgroundDisabled,
        disabledLabelColor: Color = ZTheme.color.text.disabled,
        disabledTextTopColor: Color = ZTheme.color.text.disabled,
        disabledPlaceholderColor: Color = ZTheme.color.text.disabled,
        disabledLeadingTextColor: Color = ZTheme.color.text.disabled,
        disabledTrailingTextColor: Color = ZTheme.color.text.disabled,
        disabledLeadingIconColor: Color = ZTheme.color.icon.singleToneDisable,
        disabledTrailingIconColor: Color = ZTheme.color.icon.singleToneDisable,
        disabledTextBottomColor: Color = ZTheme.color.text.disabled,
        disabledCtaTextColor: ZTextButtonColor = ZTextButtonColor.Grey,

        // Error state colors
        errorTextColor: Color = ZTheme.color.text.primary,
        errorBorderColor: Color = ZTheme.color.inputField.borderError,
        errorBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault, // Or surfaceVariant
        errorLabelColor: Color = ZTheme.color.text.red,
        errorTextTopColor: Color = ZTheme.color.text.secondary,
        errorPlaceholderColor: Color = ZTheme.color.text.primary, // Keep placeholder subtle?
        errorLeadingTextColor: Color = ZTheme.color.text.primary,
        errorTrailingTextColor: Color = ZTheme.color.text.primary,
        errorLeadingIconColor: Color = ZTheme.color.icon.singleTonePrimary, // Or error color?
        errorTrailingIconColor: Color = ZTheme.color.icon.singleTonePrimary, // Often error icon
        errorTextBottomColor: Color = ZTheme.color.text.red, // Error message color
        errorCtaTextColor: ZTextButtonColor = ZTextButtonColor.Blue,
    ): ZInputFieldColors = DefaultZInputFieldColors(
        focusedTextColor = focusedTextColor,
        focusedBorderColor = focusedBorderColor,
        focusedBackgroundColor = focusedBackgroundColor,
        focusedLabelColor = focusedLabelColor,
        focusedTextTopColor = focusedTextTopColor,
        focusedPlaceholderColor = focusedPlaceholderColor,
        focusedLeadingTextColor = focusedLeadingTextColor,
        focusedTrailingTextColor = focusedTrailingTextColor,
        focusedLeadingIconColor = focusedLeadingIconColor,
        focusedTrailingIconColor = focusedTrailingIconColor,
        focusedTextBottomColor = focusedTextBottomColor,
        focusedCtaTextColor = focusedCtaTextColor,
        unfocusedTextColor = unfocusedTextColor,
        unfocusedBorderColor = unfocusedBorderColor,
        unfocusedBackgroundColor = unfocusedBackgroundColor,
        unfocusedLabelColor = unfocusedLabelColor,
        unfocusedTextTopColor = unfocusedTextTopColor,
        unfocusedPlaceholderColor = unfocusedPlaceholderColor,
        unfocusedLeadingTextColor = unfocusedLeadingTextColor,
        unfocusedTrailingTextColor = unfocusedTrailingTextColor,
        unfocusedLeadingIconColor = unfocusedLeadingIconColor,
        unfocusedTrailingIconColor = unfocusedTrailingIconColor,
        unfocusedTextBottomColor = unfocusedTextBottomColor,
        unfocusedCtaTextColor = unfocusedCtaTextColor,
        disabledTextColor = disabledTextColor,
        disabledBorderColor = disabledBorderColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledLabelColor = disabledLabelColor,
        disabledTextTopColor = disabledTextTopColor,
        disabledPlaceholderColor = disabledPlaceholderColor,
        disabledLeadingTextColor = disabledLeadingTextColor,
        disabledTrailingTextColor = disabledTrailingTextColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        disabledTextBottomColor = disabledTextBottomColor,
        disabledCtaTextColor = disabledCtaTextColor,
        errorTextColor = errorTextColor,
        errorBorderColor = errorBorderColor,
        errorBackgroundColor = errorBackgroundColor,
        errorLabelColor = errorLabelColor,
        errorTextTopColor = errorTextTopColor,
        errorPlaceholderColor = errorPlaceholderColor,
        errorLeadingTextColor = errorLeadingTextColor,
        errorTrailingTextColor = errorTrailingTextColor,
        errorLeadingIconColor = errorLeadingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,
        errorTextBottomColor = errorTextBottomColor,
        errorCtaTextColor = errorCtaTextColor,
    )
}

/**
 * Default implementation of ZInputFieldColors.
 */
@Immutable
private class DefaultZInputFieldColors(
    // Focused state colors
    private val focusedTextColor: Color,
    private val focusedBorderColor: Color,
    private val focusedBackgroundColor: Color,
    private val focusedLabelColor: Color,
    private val focusedTextTopColor: Color,
    private val focusedPlaceholderColor: Color,
    private val focusedLeadingTextColor: Color,
    private val focusedTrailingTextColor: Color,
    private val focusedLeadingIconColor: Color,
    private val focusedTrailingIconColor: Color,
    private val focusedTextBottomColor: Color,
    private val focusedCtaTextColor: ZTextButtonColor,
    // Unfocused/Default state colors
    private val unfocusedTextColor: Color,
    private val unfocusedBorderColor: Color,
    private val unfocusedBackgroundColor: Color,
    private val unfocusedLabelColor: Color,
    private val unfocusedTextTopColor: Color,
    private val unfocusedPlaceholderColor: Color,
    private val unfocusedLeadingTextColor: Color,
    private val unfocusedTrailingTextColor: Color,
    private val unfocusedLeadingIconColor: Color,
    private val unfocusedTrailingIconColor: Color,
    private val unfocusedTextBottomColor: Color,
    private val unfocusedCtaTextColor: ZTextButtonColor,
    // Disabled state colors
    private val disabledTextColor: Color,
    private val disabledBorderColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledLabelColor: Color,
    private val disabledTextTopColor: Color,
    private val disabledLeadingTextColor: Color,
    private val disabledTrailingTextColor: Color,
    private val disabledPlaceholderColor: Color,
    private val disabledLeadingIconColor: Color,
    private val disabledTrailingIconColor: Color,
    private val disabledTextBottomColor: Color,
    private val disabledCtaTextColor: ZTextButtonColor,
    // Error state colors
    private val errorTextColor: Color,
    private val errorBorderColor: Color,
    private val errorBackgroundColor: Color,
    private val errorLabelColor: Color,
    private val errorTextTopColor: Color,
    private val errorPlaceholderColor: Color,
    private val errorLeadingTextColor: Color,
    private val errorTrailingTextColor: Color,
    private val errorLeadingIconColor: Color,
    private val errorTrailingIconColor: Color,
    private val errorTextBottomColor: Color,
    private val errorCtaTextColor: ZTextButtonColor,
) : ZInputFieldColors {

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
            else -> unfocusedBorderColor
        }
        // Animate color changes? For now, just return the state.
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
            else -> unfocusedBackgroundColor
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
                // Could differentiate focused/unfocused text color if needed
                val focused by interactionSource.collectIsFocusedAsState()
                if (focused) focusedTextColor else unfocusedTextColor
            }
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun labelColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledLabelColor
            isError -> errorLabelColor
            focused -> focusedLabelColor
            else -> unfocusedLabelColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun textTopColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledTextTopColor
            isError -> errorTextTopColor // Text Top might change color on error
            focused -> focusedTextTopColor
            else -> unfocusedTextTopColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun placeholderColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState() // Placeholder might change on focus too
        val targetColor = when {
            !enabled -> disabledPlaceholderColor
            isError -> errorPlaceholderColor // Placeholder usually doesn't change color on error
            focused -> focusedPlaceholderColor
            else -> unfocusedPlaceholderColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun leadingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState() // Placeholder might change on focus too
        val targetColor = when {
            !enabled -> disabledLeadingTextColor
            isError -> errorLeadingTextColor // Placeholder usually doesn't change color on error
            focused -> focusedLeadingTextColor
            else -> unfocusedLeadingTextColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun trailingTextColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState() // Placeholder might change on focus too
        val targetColor = when {
            !enabled -> disabledTrailingTextColor
            isError -> errorTrailingTextColor // Placeholder usually doesn't change color on error
            focused -> focusedTrailingTextColor
            else -> unfocusedTrailingTextColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun leadingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledLeadingIconColor
            isError -> errorLeadingIconColor // Leading icon might change color on error
            focused -> focusedLeadingIconColor
            else -> unfocusedLeadingIconColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun trailingIconColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val focused by interactionSource.collectIsFocusedAsState()
        val targetColor = when {
            !enabled -> disabledTrailingIconColor
            isError -> errorTrailingIconColor // Trailing icon often indicates error state
            focused -> focusedTrailingIconColor
            else -> unfocusedTrailingIconColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun textBottomColor(
        enabled: Boolean,
        isError: Boolean,
        interactionSource: InteractionSource,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledTextBottomColor
            isError -> errorTextBottomColor // Bottom text clearly shows error color
            else -> {
                val focused by interactionSource.collectIsFocusedAsState()
                if (focused) focusedTextBottomColor else unfocusedTextBottomColor
            }
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
                if (focused) focusedCtaTextColor else unfocusedCtaTextColor
            }
        }
        return rememberUpdatedState(targetColor)
    }
}


/**
 * A custom TextField composable built using BasicTextField.
 *
 * @param value The input text to be shown in the text field.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 * updated text comes as a parameter of the callback.
 * @param modifier Modifier for the outer Column layout.
 * @param enabled Controls the enabled state of the component. When `false`, this component will
 * not respond to user input, and it will appear visually disabled and disabled to accessibility
 * services.
 * @param readOnly Controls the editable state of the text field. When `true`, the text field can
 * not be modified, however, a user can focus it and copy text from it. Read-only text fields
 * are usually used to display pre-filled forms that user can not edit.
 * @param textStyle The style to be applied to the input text. Defaults to [LocalTextStyle].
 * @param label Optional composable lambda that represents the label for this text field.
 * @param textTop Optional composable lambda displayed next to the label.
 * @param placeholder Optional composable lambda displayed when the text field is empty.
 * @param leadingIcon Optional composable lambda that represents the leading icon for this text field.
 * @param trailingIcon Optional composable lambda that represents the trailing icon for this text field.
 * @param textBottom Optional composable lambda displayed below the input field (e.g., helper or error text).
 * @param supportView Optional composable lambda displayed support view right side of input (e.g., helper or error text).
 * @param ctaText Optional composable lambda displayed below the input field, typically aligned to the end (e.g., character count).
 * @param isError Indicates if the text field's current value is in error. If set to true, the
 * label, bottom text, and trailing icon colors will be updated to reflect the error state.
 * @param visualTransformation Transforms the visual representation of the input value.
 * @param keyboardOptions Software keyboard options that contains configuration such as
 * [KeyboardType] and [ImeAction].
 * @param keyboardActions When the input service emits an IME action, the corresponding callback
 * is called. Note that this IME action may be different from what you specified in
 * [KeyboardOptions.imeAction].
 * @param singleLine When set to true, this text field becomes a single horizontally scrolling
 * text field instead of wrapping onto multiple lines.
 * @param maxLines The maximum number of lines to be displayed in the text field.
 * @param interactionSource The [MutableInteractionSource] representing the stream of [Interaction]s
 * for this text field. You can create and pass in your own `remember`ed instance to observe
 * [Interaction]s and customize the appearance / behavior of this text field in different states.
 * @param shape Defines the shape of the text field's border/background.
 * @param colors [ZInputFieldColors] that will be used to resolve the colors used for this text field
 * in different states. See [ZInputFieldDefaults.colors].
 * @param borderWidth The width of the border.
 */
@Composable
fun ZInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    textTop: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    textBottom: @Composable (() -> Unit)? = null,
    supportView: @Composable (() -> Unit)? = null,
    isOutlineVariant: Boolean = true,
    cta: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZInputFieldDefaults.shape,
    colors: ZInputFieldColors = ZInputFieldDefaults.colors(),
    borderWidth: Dp = 1.dp, // Default border width
) {
    var isFocused = interactionSource.collectIsFocusedAsState().value
    val focusManager = LocalFocusManager.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val coroutineScope = rememberCoroutineScope()
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
    val mergedTextStyle = textStyle.copy(
        color = textColor,
        textAlign = textAlign,
    ).semiBold()
    val cursorBrush = SolidColor(
        colors.labelColor(
            enabled,
            isError,
            interactionSource,
        ).value,
    ) // Use label/primary color for cursor
    val borderColor = colors.borderColor(enabled, isError, interactionSource).value
    val backgroundColor = colors.backgroundColor(enabled, isError, interactionSource).value
    val textBottomColor = colors.textBottomColor(enabled, isError, interactionSource).value
    val ctaTextColor = colors.ctaTextColor(enabled, isError, interactionSource).value

    Column(modifier = modifier) {
        // --- Top Row: Label and Text Top ---
        if (label != null || textTop != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                // Provide default text style for label
                AnimatedVisibility (label != null) {
                    val labelColor = colors.labelColor(enabled, isError, interactionSource).value
                    CompositionLocalProvider(LocalContentColor provides labelColor,
                        LocalZLabelColor provides labelColor ) {
                        ProvideTextStyle(
                            value = textStyle.copy(
                                color = labelColor,
                                fontWeight = if (hasText) FontWeight.SemiBold else FontWeight.Normal,
                            ),
                        ) {
                            label?.invoke()
                        }
                    }
                }
                // Provide default text style for textTop
                AnimatedVisibility (textTop != null) {
                    val textTopColorVal =
                        colors.textTopColor(enabled, isError, interactionSource).value
                    CompositionLocalProvider(LocalContentColor provides textTopColorVal,
                        LocalZLabelColor provides textTopColorVal ) {
                        ProvideTextStyle(value = textStyle.copy(color = textTopColorVal)) {
                            textTop?.invoke()
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(ZInputFieldDefaults.LabelBottomPadding))
        }

        // --- Input Field Container ---
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(1f)
                    .conditional(isOutlineVariant) {
                        background(backgroundColor, shape)
                            .border(BorderStroke(borderWidth, borderColor), shape)
                    }
                    .conditional(isOutlineVariant.not()) {
                        drawBehind {
                            val strokeWidthPx = 1.dp.toPx()
                            val verticalOffset = size.height - 6.sp.toPx()
                            drawLine(
                                color = borderColor,
                                strokeWidth = strokeWidthPx,
                                start = Offset(0f, verticalOffset),
                                end = Offset(size.width, verticalOffset)
                            )
                        }
                    }
                    .focusRequester(focusRequester)
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                        if (it.isFocused) {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                        }
                    }
                    .conditional(singleLine && isOutlineVariant) {
                        height(ZInputFieldDefaults.MinHeight)
                    }
                    .conditional(singleLine.not() && isOutlineVariant) {
                        height(ZInputFieldDefaults.FormHeight)
                    },
                enabled = enabled,
                readOnly = readOnly,
                textStyle = mergedTextStyle,
                cursorBrush = cursorBrush,
                visualTransformation = visualTransformation,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                interactionSource = interactionSource,
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                decorationBox = @Composable { innerTextField ->
                    val alignment = if (singleLine) Alignment.CenterVertically else Alignment.Top
                    val padding = if(isOutlineVariant) ZInputFieldDefaults.OutlineContentPadding else ZInputFieldDefaults.ContentPadding

                    Row(
                        modifier = Modifier
                            .padding(padding), // Inner padding
                        verticalAlignment = alignment,
                    ) {
                        // --- Leading Icon ---
                        if (leading != null) {
                            val leadingIconColor =
                                colors.leadingIconColor(enabled, isError, interactionSource).value
                            val leadingTextColor =
                                colors.leadingTextColor(enabled, isError, interactionSource).value
                            Row(
                                modifier = Modifier.padding(end = ZInputFieldDefaults.IconPadding),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            ) {
                                CompositionLocalProvider(LocalContentColor provides leadingIconColor) {
                                    ProvideTextStyle(
                                        value = textStyle.copy(
                                            color = leadingTextColor,
                                            fontWeight = if (hasText) FontWeight.SemiBold else FontWeight.Normal,
                                        ),
                                    ) {
                                        leading()
                                    }
                                }
                            }
                        }

                        // --- Text Field Area (Placeholder and Input) ---
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = if (textAlign == TextAlign.Start)
                                Alignment.CenterStart else
                                Alignment.CenterEnd,
                        ) {
                            // Placeholder is displayed only when input text is empty
                            if (placeholder != null && !hasText) {
                                val placeholderColor =
                                    colors.placeholderColor(
                                        enabled,
                                        isError,
                                        interactionSource,
                                    ).value
                                ProvideTextStyle(value = textStyle.copy(color = placeholderColor)) {
                                    placeholder()
                                }
                            }
                            // Actual input field content
                            innerTextField()
                        }

                        // --- Trailing Icon ---
                        if (trailing != null) {
                            val trailingIconColor =
                                colors.trailingIconColor(enabled, isError, interactionSource).value
                            val trailingTextColor =
                                colors.trailingTextColor(enabled, isError, interactionSource).value
                            Row(
                                modifier = Modifier.padding(start = ZInputFieldDefaults.IconPadding),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                            ) {
                                val gradientColor = if(enabled)
                                    ZTheme.color.icon.singleToneActive
                                else ZTheme.color.inputField.borderDisabled.asZGradient()
                                CompositionLocalProvider(LocalContentColor provides trailingIconColor,
                                    LocalZGradientColor provides gradientColor,
                                    LocalZDecorationItemColor provides ZDecorationItemColor(
                                        textColor = textColor,
                                        iconColor = trailingIconColor,
                                        background = backgroundColor,
                                        border = borderColor,
                                        isError = isError,
                                        isDisable = enabled.not()
                                    )) {
                                    ProvideTextStyle(value = textStyle.copy(color = trailingTextColor)) {
                                        trailing()
                                    }
                                }
                            }
                        }
                    }
                },
            )
            AnimatedVisibility(supportView!=null) {
                supportView?.invoke()
            }
        }

        // --- Bottom Row: Text Bottom and CTA Text ---
        AnimatedVisibility (textBottom != null || cta != null) {
            Column {
                BlankHeight(if (isOutlineVariant) ZInputFieldDefaults.OutlineTextBottomPadding else ZInputFieldDefaults.TextBottomPadding)
                Row(
                    modifier = Modifier.fillMaxWidth(), // Align with input content
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Provide default text style for textBottom
                    if (textBottom != null) {
                        CompositionLocalProvider(
                            LocalContentColor provides textBottomColor,
                            LocalZLabelColor provides textBottomColor
                        ) {
                            ProvideTextStyle(value = textStyle.copy(color = textBottomColor)) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp)
                                ) {
                                    textBottom()
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f)) // Occupy space if only CTA is present
                    }
                    // Provide default text style for ctaText
                    if (cta != null) {
                        CompositionLocalProvider(LocalZTextButtonColor provides ctaTextColor) {
                            ProvideTextStyle(value = textStyle) {
                                cta()
                            }
                        }
                    }
                }
            }
        }
    }
}


// --- Preview ---

@ThemePreviews
@Composable
private fun ZInputFieldOutlinePreview() {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("Filled Text") }
    var text3 by remember { mutableStateOf("Error Text") }
    var text4 by remember { mutableStateOf("") }
    var text5 by remember { mutableStateOf("Disabled") }

    ZBackgroundPreviewContainer {
        // Example 1: Basic with Label and Placeholder
        ZInputField(
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
                    tagId = "input-cta",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )

        ZInputField(
            value = text1,
            onValueChange = { text1 = it },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Label") },
            placeholder = { Text("Placeholder") },
            textTop = { Text("Text Top") },
            textBottom = { Text("Helper text") },
            supportView = {
                val current by remember { mutableIntStateOf(-9) }
                CompositionLocalProvider(
                    LocalTextStyle provides ZTheme.typography.bodyRegularB3.semiBold(),
                    LocalZLabelColor provides ZTheme.color.text.primary,
                    LocalZIconSize provides 18.dp
                ) {
                    ZCounterOutlineField(
                        currentValue = "$current%",
                        onAdd = {},
                        onMinus = {}
                    )
                }
            },
            cta = {
                ZTextButton(
                    title = "CTA",
                    tagId = "input-cta",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )

        // Example 2: Filled with Icons
        ZInputField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Label") },
            textStyle = ZTheme.typography.bodyRegularB3,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_info.asZIcon,
                    contentDescription = null,
                )
            },
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_coins.asZIcon,
                    contentDescription = null,
                )
            },
            textBottom = { Text("Helper text") },
        )

        // Example 3: Error State
        ZInputField(
            value = text3,
            onValueChange = { text3 = it },
            label = { Text("Label") },
            textStyle = ZTheme.typography.bodyRegularB3,
            isError = true,
            trailing = {
                ZIcon(
                    icon = ZIcons.ic_arrow_right.asZIcon,
                    contentDescription = null,
                )
            },
            leading = {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ZIcon(
                        icon = ZIcons.ic_arrow_left.asZIcon,
                        contentDescription = null,
                    )
                    Text("Text")
                }
            },
            textBottom = {
                Text(
                    "This is an error message ",
                )
            },
            textTop = { Text("Text Top") },
            cta = {
                ZTextButton(
                    title = "CTA",
                    tagId = "input-cta",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )

        // Example 4: Focused State (Simulate focus in Preview is tricky, shows default active colors)
        ZInputField(
            value = text4,
            onValueChange = { text4 = it },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Label (Focus)") },
            placeholder = { Text("Type here...") },
            interactionSource = remember { MutableInteractionSource() }, // Separate source
        )

        // Example 5: Disabled State
        ZInputField(
            value = text5,
            onValueChange = { text5 = it },
            label = {
                Text("Label")
            },
            textStyle = ZTheme.typography.bodyRegularB3,
            enabled = false,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_info.asZIcon,
                    contentDescription = null,
                )
            },
            textBottom = {
                Text("This field is disabled")
            },
        )
    }
}


@ThemePreviews
@Composable
private fun ZInputFieldPreview() {
    var text1 by remember { mutableStateOf("") }
    ZBackgroundPreviewContainer {
        ZInputField(
            value = text1,
            onValueChange = { text1 = it },
            textStyle = ZTheme.typography.displayRegularD3,
            label = { ZLabel("Label", textStyle = ZTheme.typography.bodyRegularB3) },
            placeholder = { ZLabel("Value") },
            enabled = true,
            leading = {
                ZIcon(
                    icon = ZIcons.ic_currency_inr.asZIcon
                )
            },
            textTop = { ZLabel(label = "Text Top",textStyle = ZTheme.typography.bodyRegularB3) },
            isOutlineVariant = false,
            textBottom = { ZLabel("Helper text",textStyle = ZTheme.typography.bodyRegularB3) },
            trailing = {
                val current by remember { mutableIntStateOf(-1) }
                ZCounterField(
                    currentValue = current.toString(),
                    textStyle = ZTheme.typography.bodyRegularB3.semiBold(),
                    onAdd = {},
                    onMinus = {},
                    iconSize = 20.dp,
                    innerPaddingValues = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
                )
            },
            cta = {
                ZTextButton(
                    title = "CTA",
                    tagId = "input-cta",
                    innerPaddingContent = PaddingValues(horizontal = 8.dp),
                    onClick = {},
                )
            },
        )
    }

}

