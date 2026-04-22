package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.resource.ZIcons

/**
 * Defines the colors used by a [ZCountryCodeField] in different states.
 */
@Stable
interface ZCountryCodeFieldColors {

    /** Represents the color used for the border indicator. */
    @Composable
    fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
        isFilled: Boolean,
    ): State<Color>

    /** Represents the color used for the background. */
    @Composable
    fun backgroundColor(enabled: Boolean, isError: Boolean, focused: Boolean): State<Color>

    /** Represents the color used for the country code text. */
    @Composable
    fun textColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
        isFilled: Boolean,
    ): State<Color>

    /** Represents the color used for the label. */
    @Composable
    fun labelColor(enabled: Boolean, isError: Boolean, focused: Boolean): State<Color>

    /** Represents the color used for the dropdown icon. */
    @Composable
    fun dropdownIconColor(enabled: Boolean, isError: Boolean, focused: Boolean): State<Color>
}

/**
 * Default colors for [ZCountryCodeField]. Uses ZTheme colors as a base.
 */
object ZCountryCodeFieldDefaults {

    // Define default padding values
    val ContentPadding = PaddingValues(horizontal = 12.dp, vertical = 12.dp)
    val LabelBottomPadding = 4.dp
    val MinHeight = 46.dp // Minimum height for the input field area
    val width = 136.dp // Minimum Width for the input field area

    // Default shape
    val shape: Shape @Composable get() = ZTheme.shapes.medium // Or RoundedCornerShape(8.dp)

    @Composable
    fun colors(
        // active state colors
        activeTextColor: Color = ZTheme.color.text.primary,
        activeBorderColor: Color = ZTheme.color.inputField.borderActive,
        activeBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault,
        activeLabelColor: Color = ZTheme.color.text.primary,
        activeDropdownIconColor: Color = ZTheme.color.icon.singleTonePrimary,

        // Unfocused/Default state colors
        defaultTextColor: Color = ZTheme.color.text.secondary,
        defaultBorderColor: Color = ZTheme.color.inputField.borderDefault,
        defaultFilledBorderColor: Color = ZTheme.color.inputField.borderFilled,
        defaultBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault,
        defaultLabelColor: Color = ZTheme.color.text.secondary,
        defaultDropdownIconColor: Color = ZTheme.color.icon.singleTonePrimary,

        // Disabled state colors
        disabledTextColor: Color = ZTheme.color.text.disabled,
        disabledBorderColor: Color = ZTheme.color.inputField.borderDisabled,
        disabledBackgroundColor: Color = ZTheme.color.inputField.backgroundDisabled,
        disabledLabelColor: Color = ZTheme.color.text.disabled,
        disabledDropdownIconColor: Color = ZTheme.color.icon.singleToneDisable,

        // Error state colors
        errorTextColor: Color = ZTheme.color.text.primary, // Text color might not change for error
        errorBorderColor: Color = ZTheme.color.inputField.borderError,
        errorBackgroundColor: Color = ZTheme.color.inputField.backgroundDefault,
        errorLabelColor: Color = ZTheme.color.text.red,
        errorDropdownIconColor: Color = ZTheme.color.icon.singleTonePrimary, // Or error color?

    ): ZCountryCodeFieldColors = DefaultZCountryCodeFieldColors(
        activeTextColor = activeTextColor,
        activeBorderColor = activeBorderColor,
        activeBackgroundColor = activeBackgroundColor,
        activeLabelColor = activeLabelColor,
        activeDropdownIconColor = activeDropdownIconColor,
        defaultTextColor = defaultTextColor,
        defaultBorderColor = defaultBorderColor,
        defaultFilledBorderColor = defaultFilledBorderColor,
        defaultBackgroundColor = defaultBackgroundColor,
        defaultLabelColor = defaultLabelColor,
        defaultDropdownIconColor = defaultDropdownIconColor,
        disabledTextColor = disabledTextColor,
        disabledBorderColor = disabledBorderColor,
        disabledBackgroundColor = disabledBackgroundColor,
        disabledLabelColor = disabledLabelColor,
        disabledDropdownIconColor = disabledDropdownIconColor,
        errorTextColor = errorTextColor,
        errorBorderColor = errorBorderColor,
        errorBackgroundColor = errorBackgroundColor,
        errorLabelColor = errorLabelColor,
        errorDropdownIconColor = errorDropdownIconColor,
    )
}

/**
 * Default implementation of ZCountryCodeFieldColors.
 */
@Immutable
private class DefaultZCountryCodeFieldColors(
    // Focused state colors
    private val activeTextColor: Color,
    private val activeBorderColor: Color,
    private val activeBackgroundColor: Color,
    private val activeLabelColor: Color,
    private val activeDropdownIconColor: Color,
    // Unfocused/Default state colors
    private val defaultTextColor: Color,
    private val defaultBorderColor: Color,
    private val defaultFilledBorderColor: Color,
    private val defaultBackgroundColor: Color,
    private val defaultLabelColor: Color,
    private val defaultDropdownIconColor: Color,
    // Disabled state colors
    private val disabledTextColor: Color,
    private val disabledBorderColor: Color,
    private val disabledBackgroundColor: Color,
    private val disabledLabelColor: Color,
    private val disabledDropdownIconColor: Color,
    // Error state colors
    private val errorTextColor: Color,
    private val errorBorderColor: Color,
    private val errorBackgroundColor: Color,
    private val errorLabelColor: Color,
    private val errorDropdownIconColor: Color,
) : ZCountryCodeFieldColors {

    @Composable
    override fun borderColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
        isFilled: Boolean,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledBorderColor
            isError -> errorBorderColor
            focused -> activeBorderColor
            isFilled -> defaultFilledBorderColor
            else -> defaultBorderColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun backgroundColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledBackgroundColor
            isError -> errorBackgroundColor
            focused -> activeBackgroundColor
            else -> defaultBackgroundColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun textColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
        isFilled: Boolean,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledTextColor
            isError -> errorTextColor // Text color might change on error
            else -> {
                if (focused || isFilled) activeTextColor else defaultTextColor
            }
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun labelColor(enabled: Boolean, isError: Boolean, focused: Boolean): State<Color> {
        val targetColor = when {
            !enabled -> disabledLabelColor
            isError -> errorLabelColor
            focused -> activeLabelColor
            else -> defaultLabelColor
        }
        return rememberUpdatedState(targetColor)
    }

    @Composable
    override fun dropdownIconColor(
        enabled: Boolean,
        isError: Boolean,
        focused: Boolean,
    ): State<Color> {
        val targetColor = when {
            !enabled -> disabledDropdownIconColor
            isError -> errorDropdownIconColor // Icon might change color on error
            focused -> activeDropdownIconColor
            else -> defaultDropdownIconColor
        }
        return rememberUpdatedState(targetColor)
    }

}


/**
 * A custom composable that displays a selected country code and flag,
 * styled similarly to a TextField, and is clickable.
 *
 * @param selectedCountryCode The text representation of the country code (e.g., "+91").
 * @param selectedCountryFlag A composable lambda that renders the flag image.
 * It's recommended to use a fixed size for the flag within this lambda.
 * @param onClick Callback invoked when the field is clicked.
 * @param modifier Modifier for the outer Column layout.
 * @param enabled Controls the enabled state of the component.
 * @param label Optional composable lambda for the label displayed above the field.
 * @param isError Indicates if the current selection is in an error state.
 * @param interactionSource MutableInteractionSource for observing focus state.
 * @param shape Defines the shape of the field's border/background.
 * @param colors [ZCountryCodeFieldColors] resolving colors for different states.
 * @param textStyle The style for the country code text.
 * @param borderWidth The width of the border.
 */
@Composable
fun ZCountryCodeField(
    modifier: Modifier = Modifier,
    selectedCountryCode: String = "",
    selectedCountryFlag: ZIcon,
    onClick: () -> Unit,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    isFocused: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ZCountryCodeFieldDefaults.shape,
    colors: ZCountryCodeFieldColors = ZCountryCodeFieldDefaults.colors(),
    textStyle: TextStyle = LocalTextStyle.current,
    borderWidth: Dp = 1.dp,
) {
    // Resolve colors
    val textColor =
        colors.textColor(enabled, isError, isFocused, selectedCountryCode.isNotEmpty()).value
    var mergedTextStyle = textStyle.merge(TextStyle(color = textColor))
    if (enabled && selectedCountryCode.isNotEmpty()) {
        mergedTextStyle = mergedTextStyle.semiBold()
    }
    val borderColor =
        colors.borderColor(enabled, isError, isFocused, selectedCountryCode.isNotEmpty()).value
    val backgroundColor = colors.backgroundColor(enabled, isError, isFocused).value
    val dropdownIconColor = colors.dropdownIconColor(enabled, isError, isFocused).value

    Column(modifier = modifier) {
        if (label != null) {
            val labelColor = colors.labelColor(enabled, isError, isFocused).value
            val requiredSemiBold = enabled && selectedCountryCode.isNotEmpty()
            CompositionLocalProvider(LocalContentColor provides dropdownIconColor) {
                ProvideTextStyle(
                    value = textStyle.copy(
                        color = labelColor,
                        fontWeight =
                            if (requiredSemiBold) FontWeight.SemiBold else FontWeight.Normal,
                    ),
                ) {
                    label()
                }
            }
            Spacer(modifier = Modifier.height(ZCountryCodeFieldDefaults.LabelBottomPadding))
        }

        Box(
            modifier = Modifier
                .width(ZCountryCodeFieldDefaults.width)
                .defaultMinSize(minHeight = ZCountryCodeFieldDefaults.MinHeight)
                .background(backgroundColor, shape)
                .border(BorderStroke(borderWidth, borderColor), shape)
                .clip(shape)
                .clickable(
                    enabled = enabled,
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = null,
                )
                .padding(ZCountryCodeFieldDefaults.ContentPadding), // Apply padding *inside* the clickable area
            contentAlignment = Alignment.CenterStart, // Align content to the start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End, // Push dropdown icon to the end
                modifier = Modifier.fillMaxWidth(),
            ) {
                // --- Country Code Text ---
                Text(
                    text = selectedCountryCode,
                    style = mergedTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                )
                BlankWidth(8.dp)
                ZIcon(
                    modifier = Modifier,
                    icon = selectedCountryFlag,
                    tint = Color.Unspecified,
                )
                BlankWidth(4.dp)
                ZIcon(
                    icon = ZIcons.ic_arrow_down.asZIcon,
                    contentDescription = "Select Country", // Accessibility
                    tint = dropdownIconColor,
                )
            }
        }
    }
}


@ThemePreviews
@Composable
private fun ZCountryCodeFieldPreview() {
    val selectedCode by remember { mutableStateOf("+8888") }
    val countryIcon = ZIcons.ic_flag_in.asZIcon
    ZBackgroundPreviewContainer {

        ZCountryCodeField(
            selectedCountryFlag = countryIcon,
            onClick = { },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Country Code (Default)") },
        )

        ZCountryCodeField(
            selectedCountryCode = selectedCode,
            selectedCountryFlag = countryIcon,
            onClick = { },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Country Code (Filled)") },
        )

        ZCountryCodeField(
            selectedCountryCode = selectedCode,
            selectedCountryFlag = countryIcon,
            onClick = { },
            textStyle = ZTheme.typography.bodyRegularB3,
            isFocused = true,
            label = { Text("Country Code (Focused)") },
        )

        // Example 3: Error State
        ZCountryCodeField(
            selectedCountryCode = selectedCode,
            selectedCountryFlag = countryIcon,
            textStyle = ZTheme.typography.bodyRegularB3,
            onClick = { },
            label = { Text("Country Code") },
            isError = true,
        )

        // Example 4: Disabled State
        ZCountryCodeField(
            selectedCountryCode = selectedCode,
            selectedCountryFlag = countryIcon,
            textStyle = ZTheme.typography.bodyRegularB3,
            onClick = { },
            label = { Text("Country Code") },
            enabled = false,
        )
    }
}
