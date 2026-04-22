package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.UserLocalProvider
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.CyclicAnimatedText
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.utils.*
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZDecorationItemColor
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import com.zebpay.ui.v3.components.utils.LocalZTextButtonColor
import com.zebpay.ui.v3.components.utils.ZDecorationItemColor
import kotlinx.coroutines.launch


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
 * @param visualTransformation The VisualTransformation to apply (e.g., PriceVisualTransformation() for automatic separator removal).
 */

/**
 * VisualTransformation for price input fields that automatically handles separator removal.
 * Use this when you want automatic removal of trailing commas and decimal points.
 *
 * Example usage:
 * ```
 * ZOutlineInputField(
 *     value = priceText,
 *     onValueChange = { priceText = it },
 *     visualTransformation = PriceVisualTransformation(),
 *     // ... other parameters
 * )
 * ```
 */

sealed interface ZVisualTransformationType {
    object Normal : ZVisualTransformationType
    data class Price(val precision: Int, val maxDigit: Int = 20) : ZVisualTransformationType
}

/**
 * Helper class to handle all text formatting logic for ZOutlineInputField
 */
private class ZOutlineInputFieldParser private constructor(
    private val transformationType: ZVisualTransformationType,
    private val isInternationalCurrency: Boolean = false,
) {

    companion object Companion {
        /**
         * Create a formatter and process text change in one call
         */
        fun processText(
            transformationType: ZVisualTransformationType,
            isInternationalCurrency: Boolean,
            newValue: String,
            oldValue: String,
            newSelection: Int,
            oldSelection: Int,
        ): Pair<String, Int> {
            return ZOutlineInputFieldParser(transformationType, isInternationalCurrency)
                .processTextChange(newValue, oldValue, newSelection, oldSelection)
        }
    }

    /**
     * Process text field value changes with appropriate formatting
     */
    private fun processTextChange(
        newValue: String,
        oldValue: String,
        newSelection: Int,
        oldSelection: Int,
    ): Pair<String, Int> {
        return when (transformationType) {
            is ZVisualTransformationType.Price -> {
                val precision = transformationType.precision
                val maxDigit = transformationType.maxDigit
                // Check if the new value exceeds maximum pricing digits limit
                if (!isValidPricingInput(newValue, precision, maxDigit)) {
                    // If invalid, keep the old value
                    return oldValue to minOf(oldSelection, oldValue.length)
                }

                // First handle separator removal for deletions
                val (processedText, processedSelection) = handleSeparatorRemoval(
                    newValue, oldValue, newSelection, oldSelection, precision,
                )

                // Check again after processing separator removal
                if (!isValidPricingInput(processedText, precision, maxDigit)) {
                    return oldValue to minOf(oldSelection, oldValue.length)
                }

                // Then apply number formatting with commas
                val formattedText = formatNumberWithCommas(processedText, precision)

                // Calculate new cursor position after formatting
                val cursorAdjustment = formattedText.length - processedText.length
                val adjustedSelection = maxOf(0, minOf(processedSelection + cursorAdjustment, formattedText.length))

                formattedText to adjustedSelection
            }

            ZVisualTransformationType.Normal -> newValue to newSelection
        }
    }

    /**
     * Validates pricing input to ensure whole number part doesn't exceed maxPricingDigits
     * and decimal part doesn't exceed pricePrecision
     */
    private fun isValidPricingInput(input: String, precision: Int, maxDigit: Int): Boolean {
        if (input.isEmpty()) return true

        // Split by decimal point
        val parts = input.replace(",", "").split('.')
        val wholePart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else ""

        // Check if whole part contains only digits
        if (wholePart.isNotEmpty() && !wholePart.all { it.isDigit() }) {
            return false
        }

        // Check if decimal part contains only digits
        if (decimalPart.isNotEmpty() && !decimalPart.all { it.isDigit() }) {
            return false
        }

        // Check maximum digits in whole part (excluding commas)
        if (wholePart.length > maxDigit) {
            return false
        }

        // Check maximum decimal places
        if (decimalPart.length > precision) {
            return false
        }

        return true
    }

    private fun handleSeparatorRemoval(
        newValue: String,
        oldValue: String,
        newSelection: Int,
        oldSelection: Int,
        precision: Int,
    ): Pair<String, Int> {
        // Only process if text got shorter (deletion operation)
        if (newValue.length >= oldValue.length) return newValue to newSelection

        // Determine what character was deleted by comparing old and new values
        val deletedCharIndex = findDeletedCharacterIndex(oldValue, newValue, oldSelection, precision)
        val deletedChar = if (deletedCharIndex >= 0 && deletedCharIndex < oldValue.length) {
            oldValue[deletedCharIndex]
        } else null

        // Handle different deletion scenarios based on deleted character and cursor position
        when {
            // Case 1: Decimal point was deleted - merge decimal digits with whole number
            deletedChar == '.' -> {
                // Remove commas from the whole number part for clean merging
                val beforeDecimal = oldValue.take(deletedCharIndex).replace(",", "")
                val afterDecimal = if (deletedCharIndex + 1 < oldValue.length) {
                    oldValue.substring(deletedCharIndex + 1)
                } else ""

                // Merge: whole number + decimal digits
                val mergedDigits = beforeDecimal + afterDecimal
                val newCursorPos = beforeDecimal.length

                // Let the formatting function handle comma placement
                return mergedDigits to newCursorPos
            }

            // Case 2: Comma was deleted - Handle cursor navigation over commas
            deletedChar == ',' -> {
                return handleCommaNavigation(oldValue, deletedCharIndex, oldSelection)
            }

            // Case 3: Regular character deletion - check for trailing separators
            else -> {
                val cleanedText = newValue.dropLastWhile { it in setOf(',', '.') }
                val adjustedSelection = maxOf(0, minOf(newSelection, cleanedText.length))
                return cleanedText to adjustedSelection
            }
        }
    }

    private fun handleCommaNavigation(
        oldValue: String,
        deletedCharIndex: Int,
        oldSelection: Int,
    ): Pair<String, Int> {
        // Determine cursor position relative to comma for proper behavior
        val cursorWasOnComma = deletedCharIndex == oldSelection
        val cursorWasAfterComma = deletedCharIndex == oldSelection - 1

        return when {
            // Case 2A: Cursor was positioned ON comma (Forward Delete Key)
            // Example: "12|,345" → DELETE → "12|345" (removes comma, cursor stays)
            cursorWasOnComma -> {
                val resultText = oldValue.removeRange(deletedCharIndex, deletedCharIndex + 1)
                resultText to deletedCharIndex
            }

            // Case 2B: Cursor was positioned AFTER comma (Backspace Navigation)
            // Example: "1,233,|312" → BACKSPACE → "1,233|,312" (cursor jumps left)
            // Example: "1,|234" → BACKSPACE → "1|,234" (cursor jumps left)
            cursorWasAfterComma -> {
                // Don't delete anything, just move cursor to left of comma
                oldValue to deletedCharIndex
            }

            // Case 2C: Other comma deletion scenarios - default removal
            else -> {
                val resultText = oldValue.removeRange(deletedCharIndex, deletedCharIndex + 1)
                resultText to maxOf(0, deletedCharIndex)
            }
        }
    }

    private fun findDeletedCharacterIndex(
        oldValue: String,
        newValue: String,
        oldSelection: Int,
        pricePrecision: Int,
    ): Int {
        // If new text is shorter by exactly 1 character, find where the deletion occurred
        if (oldValue.length != newValue.length + 1) return -1

        // Compare character by character to find the deletion point
        for (i in 0 until minOf(oldValue.length, newValue.length)) {
            if (i < newValue.length && oldValue[i] != newValue[i]) {
                return i
            }
        }

        // If no difference found in the middle, deletion was at the end
        return if (oldValue.length > newValue.length) newValue.length else -1
    }

    private fun formatNumberWithCommas(input: String, pricePrecision: Int): String {
        if (input.isEmpty()) return input

        // Preserve decimal point if input ends with it
        val endsWithDecimal = input.endsWith('.')

        // Split by decimal point to handle whole and decimal parts separately
        val parts = input.replace(",", "").split('.')
        val wholePart = parts[0]
        val decimalPart = if (parts.size > 1) parts[1] else ""

        // Validate that wholePart contains only digits
        if (wholePart.isNotEmpty() && !wholePart.all { it.isDigit() }) {
            return input // Return original if invalid
        }

        // Format whole part with thousand separators
        val formattedWhole = formatWholePartWithSeparators(wholePart, isInternationalCurrency)

        // Handle decimal part (limit to 8 digits for all cases)
        val limitedDecimalPart = if (decimalPart.length > pricePrecision) {
            decimalPart.take(pricePrecision)
        } else {
            decimalPart
        }

        // Combine parts
        return when {
            limitedDecimalPart.isNotEmpty() -> "$formattedWhole.$limitedDecimalPart"
            endsWithDecimal -> "$formattedWhole."
            else -> formattedWhole
        }
    }

    /**
     * Format whole number part with thousand separators based on locale
     * @param wholePart String containing only digits
     * @param isInternational true for US format (1,234,567), false for Indian format (12,34,567)
     */
    private fun formatWholePartWithSeparators(wholePart: String, isInternational: Boolean): String {
        if (wholePart.isEmpty() || wholePart.length <= 3) return wholePart

        return if (isInternational) {
            // US format: group every 3 digits from right (1,234,567)
            wholePart.reversed()
                .chunked(3)
                .joinToString(",")
                .reversed()
        } else {
            // Indian format: first group of 3, then groups of 2 (12,34,567)
            val reversed = wholePart.reversed()
            val result = StringBuilder()

            // First group of 3 digits
            if (reversed.length > 3) {
                result.append(reversed.substring(0, 3))

                // Remaining digits in groups of 2
                val remaining = reversed.substring(3)
                remaining.chunked(2).forEach { chunk ->
                    result.append(",").append(chunk)
                }
            } else {
                result.append(reversed)
            }

            result.toString().reversed()
        }
    }
}

/**
 * VisualTransformation for phone number formatting (example for future use)
 * You can add more transformations like this for different input types
 */
class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Future implementation for phone number formatting
        return TransformedText(
            text = text,
            offsetMapping = OffsetMapping.Identity,
        )
    }
}

/**
 * VisualTransformation for currency formatting (example for future use)
 */
class CurrencyVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Future implementation for currency formatting
        return TransformedText(
            text = text,
            offsetMapping = OffsetMapping.Identity,
        )
    }
}

@Composable
fun ZOutlineInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    supportTextStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    textTop: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    textBottom: @Composable (() -> Unit)? = null,
    supportView: @Composable (() -> Unit)? = null,
    cta: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: ZVisualTransformationType = ZVisualTransformationType.Normal,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    autoFocus: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZTheme.shapes.large,
    topSpace: Dp = 4.dp,
    colors: ZInputFieldColors = ZInputFieldDefaults.colors(),
    borderWidth: Dp = 1.dp, // Default border width
) {
    // Use TextFieldValue internally to track cursor position for separator removal
    var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }
    // Update internal TextFieldValue when external value changes
    LaunchedEffect(value) {
        if (textFieldValue.text != value) {
            textFieldValue = textFieldValue.copy(text = value)
        }
    }
    val isInternationalCurrency = UserLocalProvider.current.isInternationUser

    ZOutlineInputField(
        value = textFieldValue,
        onValueChange = { newTextFieldValue ->
            val (processedText, processedSelection) = ZOutlineInputFieldParser.processText(
                transformationType = visualTransformation,
                isInternationalCurrency = isInternationalCurrency,
                newValue = newTextFieldValue.text,
                oldValue = textFieldValue.text,
                newSelection = newTextFieldValue.selection.start,
                oldSelection = textFieldValue.selection.start,
            )

            // Ensure selection is within valid bounds
            val safeSelection = maxOf(0, minOf(processedSelection, processedText.length))

            val finalTextFieldValue = newTextFieldValue.copy(
                text = processedText,
                selection = androidx.compose.ui.text.TextRange(safeSelection),
            )
            textFieldValue = finalTextFieldValue
            onValueChange(processedText)
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        supportTextStyle = supportTextStyle,
        label = label,
        textTop = textTop,
        placeholder = placeholder,
        leading = leading,
        trailing = trailing,
        textBottom = textBottom,
        supportView = supportView,
        cta = cta,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        autoFocus = autoFocus,
        maxLines = maxLines,
        minLines = minLines,
        textAlign = textAlign,
        interactionSource = interactionSource,
        shape = shape,
        topSpace = topSpace,
        colors = colors,
        borderWidth = borderWidth,
    )
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
 * @param visualTransformation The VisualTransformation to apply (e.g., PriceVisualTransformation() for automatic separator removal).
 */
@Composable
fun ZOutlineInputField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    supportTextStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    textTop: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    textBottom: @Composable (() -> Unit)? = null,
    supportView: @Composable (() -> Unit)? = null,
    cta: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: ZVisualTransformationType = ZVisualTransformationType.Normal,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    autoFocus: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    textAlign: TextAlign = TextAlign.Start,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZTheme.shapes.large,
    topSpace: Dp = 4.dp,
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
    val keyboardController = LocalSoftwareKeyboardController.current
    // Watch keyboard visibility via ime bottom inset
    LaunchedEffect(imeBottom) {
        val keyboardVisible = imeBottom > 0
        if (!keyboardVisible && wasKeyboardVisible && isFocused) {
            focusManager.clearFocus()
        }
        wasKeyboardVisible = keyboardVisible
    }

    LaunchedEffect(autoFocus) {
        if (autoFocus) {
            focusRequester.requestFocus()
            keyboardController?.show()
        }
    }

    val hasText = value.text.isNotEmpty()

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

    val isInternationalCurrency = UserLocalProvider.current.isInternationUser

    Column(modifier = modifier) {
        // --- Top Row: Label and Text Top ---
        Box(
            modifier = Modifier
                .background(backgroundColor, shape)
                .border(BorderStroke(borderWidth, borderColor), shape),
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
            ) {
                if (label != null || textTop != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        // Provide default text style for label
                        AnimatedVisibility(label != null) {
                            val labelColor =
                                colors.labelColor(enabled, isError, interactionSource).value
                            CompositionLocalProvider(
                                LocalContentColor provides labelColor,
                                LocalZLabelColor provides labelColor,
                                LocalZGradientColor provides if (enabled) ZTheme.color.buttons.secondary.textActive
                                else ZTheme.color.buttons.secondary.textDisable.asZGradient(),
                                LocalZDecorationItemColor provides ZDecorationItemColor(
                                    textColor = labelColor,
                                    iconColor = labelColor,
                                    background = backgroundColor,
                                    border = borderColor,
                                    isError = isError,
                                    isDisable = enabled.not(),
                                ),
                            ) {
                                ProvideTextStyle(
                                    value = supportTextStyle.copy(
                                        color = labelColor,
                                        fontWeight = if (hasText) FontWeight.Medium else FontWeight.Normal,
                                    ),
                                ) {
                                    label?.invoke()
                                }
                            }
                        }
                        // Provide default text style for textTop
                        AnimatedVisibility(textTop != null) {
                            val textTopColorVal =
                                colors.textTopColor(enabled, isError, interactionSource).value
                            CompositionLocalProvider(
                                LocalContentColor provides textTopColorVal,
                                LocalZLabelColor provides textTopColorVal,
                                LocalZGradientColor provides if (enabled) ZTheme.color.buttons.secondary.textActive
                                else ZTheme.color.buttons.secondary.textDisable.asZGradient(),
                                LocalZDecorationItemColor provides ZDecorationItemColor(
                                    textColor = textTopColorVal,
                                    iconColor = textTopColorVal,
                                    background = backgroundColor,
                                    border = borderColor,
                                    isError = isError,
                                    isDisable = enabled.not(),
                                ),
                            ) {
                                ProvideTextStyle(value = textStyle.copy(color = textTopColorVal)) {
                                    textTop?.invoke()
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(topSpace))
                }

                // --- Input Field Container ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    BasicTextField(
                        value = value,
                        onValueChange = { newTextFieldValue ->
                            val (processedText, processedSelection) = ZOutlineInputFieldParser.processText(
                                transformationType = visualTransformation,
                                isInternationalCurrency = isInternationalCurrency,
                                newValue = newTextFieldValue.text,
                                oldValue = value.text,
                                newSelection = newTextFieldValue.selection.start,
                                oldSelection = value.selection.start,
                            )

                            // Ensure selection is within valid bounds
                            val safeSelection = maxOf(0, minOf(processedSelection, processedText.length))

                            val finalTextFieldValue = newTextFieldValue.copy(
                                text = processedText,
                                selection = androidx.compose.ui.text.TextRange(safeSelection),
                            )
                            onValueChange(finalTextFieldValue)
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .bringIntoViewRequester(bringIntoViewRequester)
                            .onFocusChanged {
                                isFocused = it.isFocused
                                if (it.isFocused) {
                                    coroutineScope.launch {
                                        bringIntoViewRequester.bringIntoView()
                                    }
                                }
                            },
                        enabled = enabled,
                        readOnly = readOnly,
                        textStyle = mergedTextStyle,
                        cursorBrush = cursorBrush,
                        visualTransformation = VisualTransformation.None,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        interactionSource = interactionSource,
                        singleLine = singleLine,
                        maxLines = maxLines,
                        minLines = minLines,
                        decorationBox = @Composable { innerTextField ->
                            val alignment = Alignment.CenterVertically
                            Row(
                                modifier = Modifier,
                                verticalAlignment = alignment,
                            ) {
                                // --- Leading Icon ---
                                if (leading != null) {
                                    val leadingIconColor =
                                        colors.leadingIconColor(
                                            enabled,
                                            isError,
                                            interactionSource,
                                        ).value
                                    val leadingTextColor =
                                        colors.leadingTextColor(
                                            enabled,
                                            isError,
                                            interactionSource,
                                        ).value
                                    Row(
                                        modifier = Modifier.padding(end = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.Start,
                                        ),
                                    ) {
                                        CompositionLocalProvider(
                                            LocalContentColor provides leadingIconColor,
                                            LocalZGradientColor provides if (enabled) ZTheme.color.buttons.secondary.textActive
                                            else ZTheme.color.buttons.secondary.textDisable.asZGradient(),
                                            LocalZDecorationItemColor provides ZDecorationItemColor(
                                                textColor = leadingTextColor,
                                                iconColor = leadingIconColor,
                                                background = backgroundColor,
                                                border = borderColor,
                                                isError = isError,
                                                isDisable = enabled.not(),
                                            ),
                                        ) {
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
                                        colors.trailingIconColor(
                                            enabled,
                                            isError,
                                            interactionSource,
                                        ).value
                                    val trailingTextColor =
                                        colors.trailingTextColor(
                                            enabled,
                                            isError,
                                            interactionSource,
                                        ).value
                                    Row(
                                        modifier = Modifier.padding(start = ZInputFieldDefaults.IconPadding),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.Start,
                                        ),
                                    ) {
                                        val gradientColor = if (enabled)
                                            ZTheme.color.icon.singleToneActive
                                        else ZTheme.color.inputField.borderDisabled.asZGradient()
                                        CompositionLocalProvider(
                                            LocalContentColor provides trailingIconColor,
                                            LocalZGradientColor provides gradientColor,
                                            LocalZDecorationItemColor provides ZDecorationItemColor(
                                                textColor = textColor,
                                                iconColor = trailingIconColor,
                                                background = backgroundColor,
                                                border = borderColor,
                                                isError = isError,
                                                isDisable = enabled.not(),
                                            ),
                                        ) {
                                            ProvideTextStyle(value = textStyle.copy(color = trailingTextColor)) {
                                                trailing()
                                            }
                                        }
                                    }
                                }
                            }
                        },
                    )
                    AnimatedVisibility(supportView != null) {
                        val trailingIconColor =
                            colors.trailingIconColor(
                                enabled,
                                isError,
                                interactionSource,
                            ).value
                        val trailingTextColor =
                            colors.trailingTextColor(
                                enabled,
                                isError,
                                interactionSource,
                            ).value
                        val gradientColor = if (enabled)
                            ZTheme.color.icon.singleToneActive
                        else ZTheme.color.inputField.borderDisabled.asZGradient()
                        CompositionLocalProvider(
                            LocalContentColor provides trailingIconColor,
                            LocalZGradientColor provides gradientColor,
                            LocalZDecorationItemColor provides ZDecorationItemColor(
                                textColor = textColor,
                                iconColor = trailingIconColor,
                                background = backgroundColor,
                                border = borderColor,
                                isError = isError,
                                isDisable = enabled.not(),
                            ),
                        ) {
                            supportView?.invoke()
                        }
                    }
                }
            }
        }

        // --- Bottom Row: Text Bottom and CTA Text ---
        AnimatedVisibility(textBottom != null || cta != null) {
            Column {
                BlankHeight(ZInputFieldDefaults.TextBottomPadding)
                Row(
                    modifier = Modifier.fillMaxWidth(), // Align with input content
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // Provide default text style for textBottom
                    if (textBottom != null) {
                        CompositionLocalProvider(
                            LocalContentColor provides textBottomColor,
                            LocalZLabelColor provides textBottomColor,
                        ) {
                            ProvideTextStyle(value = supportTextStyle.copy(color = textBottomColor)) {
                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 16.dp),
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
