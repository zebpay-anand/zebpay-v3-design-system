package com.zebpay.ui.v3.components.atoms.navigation

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.utils.LocalZNavigationBarColor


/**
 * Data class holding the colors for the ZNavigationBar.
 */
@Immutable
data class ZNavColors(
    val activeIconColor: Color,
    val defaultIconColor: Color,
    val activeTextColor: ZGradient,
    val defaultTextColor: Color,
    val fabColor: Color, // Color for the FAB icon itself
    val fabContainerColor: ZGradient, // Background color of the FAB
    val containerColor: Color, // Background color of the navigation bar
)

/**
 * Default color values and factory function.
 */
object ZDefaultNavColors {
    @Composable
    fun colors(
        activeIcon: Color = ZTheme.color.icon.singleTonePrimary,
        defaultIcon: Color = ZTheme.color.navigation.bottom.default,
        activeText: ZGradient = ZTheme.color.navigation.bottom.active,
        defaultText: Color = ZTheme.color.text.secondary,
        fabIcon: Color = ZTheme.color.icon.singleToneWhite,
        fabContainer: ZGradient = ZTheme.color.navigation.bottom.active,
        container: Color = ZTheme.color.navigation.bottom.background,
    ): ZNavColors = ZNavColors(
        activeIconColor = activeIcon,
        defaultIconColor = defaultIcon,
        activeTextColor = activeText,
        defaultTextColor = defaultText,
        fabColor = fabIcon,
        fabContainerColor = fabContainer,
        containerColor = container,
    )
}

/**
 * Represents a single item within the ZNavigationBar. Typically an icon and a label.
 * This is the composable placed by ZNavItemScope.Item().
 *
 * @param selected Whether this item is selected.
 * @param onClick Callback invoked when the item is clicked.
 * @param icon The composable for the item's icon.
 * @param modifier Modifier applied to the item's clickable area.
 * @param enabled Controls the enabled state.
 * @param label Optional composable for the item's label.
 * @param alwaysShowLabel Whether to show the label even when not selected.
 * @param colors The color configuration for the navigation bar.
 * @param interactionSource Interaction source for customizing appearance/behavior.
 */
@Composable
fun ZNavItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true, // Default to always show as per image
    textStyle: TextStyle = LocalTextStyle.current,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val colors = LocalZNavigationBarColor.current
    val currentIconColor = if (selected) colors.activeIconColor else colors.defaultIconColor
    val currentTextColor =
        if (selected) colors.activeTextColor else colors.defaultTextColor.asZGradient()

    val clickableModifier = modifier.fillMaxSize().clickable(
        enabled = enabled,
        onClick = onClick,
        role = Role.Tab,
        interactionSource = interactionSource,
        indication = LocalIndication.current,
    )

    // Layout: Column containing Icon and optional Label
    Column(
        modifier = clickableModifier.padding(vertical = 4.dp), // Padding for touch target
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, // Center content vertically
    ) {
        // Provide the calculated color to the icon
        CompositionLocalProvider(LocalContentColor provides currentIconColor) {
            icon()
        }

        // Show label if provided and selected or alwaysShowLabel is true
        if (label != null && (selected || alwaysShowLabel)) {
            Spacer(modifier = Modifier.height(4.dp)) // Space between icon and label
            // Provide calculated color and default style to the label
            ProvideTextStyle(
                value = textStyle.copy(
                    brush = currentTextColor.toTextBrush(),
                    textAlign = TextAlign.Center,
                    fontSize = adaptiveTextSize(),
                    fontWeight = if (selected) {
                        FontWeight.Medium
                    } else {
                        FontWeight.Normal
                    },
                ),
            ) {
                label()
            }
        }
    }
}


@Composable
fun adaptiveTextSize(): TextUnit {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp

    return when {
        screenWidthDp <= 320 -> 10.sp
        screenWidthDp <= 365 -> 11.sp
        else -> 12.sp
    }
}

data class MenuIcon(
    val selectedIcon: ZIcon,
    val defaultIcon: ZIcon,
)