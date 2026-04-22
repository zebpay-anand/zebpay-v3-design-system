package com.zebpay.ui.v3.components.molecules.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.atoms.navigation.MenuIcon
import com.zebpay.ui.v3.components.atoms.navigation.ZDefaultNavColors
import com.zebpay.ui.v3.components.atoms.navigation.ZNavColors
import com.zebpay.ui.v3.components.atoms.navigation.ZNavItem
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.BottomNavCutoutShape
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZNavigationBarColor
import com.zebpay.ui.v3.components.utils.background

/**
 * Scope for the items lambda of [ZNavigationBar]. Provides a function to add items.
 */
@LayoutScopeMarker
@Immutable
interface ZNavItemScope {
    val colors: ZNavColors
}

// Private implementation of the scope
private class ZNavItemScopeImpl(
    override val colors: ZNavColors,
) : ZNavItemScope


// --- Navigation Bar ---

private val FabDiameter = 60.dp // Standard FAB size
private val NavBarHeight = 76.dp // Typical height for bottom nav
private val NavBarShadowElevation = 8.dp // Elevation for the shadow

/**
 * A custom Bottom Navigation Bar with a central Floating Action Button (FAB) cutout.
 *
 * @param modifier Modifier applied to the main container Box.
 * @param fab Composable lambda for the Floating Action Button content (usually an Icon).
 * @param onFabClick Callback invoked when the FAB is clicked.
 * @param colors Color configuration using [ZNavColors].
 * @param shadowElevation Elevation for the shadow effect.
 * @param shadowColor Color for the shadow.
 * @param items A lambda block within [ZNavItemScope] where navigation items are defined using `Item(...)`.
 */
@Composable
fun ZNavigationBar(
    modifier: Modifier = Modifier,
    fab: @Composable () -> Unit,
    onFabClick: () -> Unit,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: ZNavColors = ZDefaultNavColors.colors(),
    shadowElevation: Dp = NavBarShadowElevation,
    shadowColor: Color = Color(0x33001869), // Default subtle shadow
    items: @Composable ZNavItemScope.() -> Unit,
) {
    val scope = remember(colors) { ZNavItemScopeImpl(colors) }

    val cutoutShape = remember { BottomNavCutoutShape(FabDiameter) }
    val shadowElevationPx = with(LocalDensity.current) { shadowElevation.toPx() }
    CompositionLocalProvider(
        LocalZNavigationBarColor provides colors,
        LocalTextStyle provides textStyle,
    ) {
        Box(modifier = modifier.fillMaxWidth()) { // Main container
            // Custom Layout for arranging items around the FAB space
            CompositionLocalProvider(
                LocalZIconSize provides 32.dp,
            ) {
                Layout(
                    modifier = Modifier
                        .height(NavBarHeight)
                        .clip(cutoutShape)
                        // Apply shadow and shape using graphicsLayer
                        .graphicsLayer {
                            this.shadowElevation = shadowElevationPx
                            this.shape = cutoutShape
                            // Set clip to false to allow shadow to draw outside the shape bounds
                            this.clip = false
                            // Optional: Define shadow colors for more control
                            this.ambientShadowColor = shadowColor
                            this.spotShadowColor = shadowColor
                        }
                        // Background color drawn within the final clipped shape
                        .background(colors.containerColor),
                    content = { scope.items() }, // Render all items defined in the scope
                ) { measures, constraints ->
                    val numItems = measures.size // Get count directly from measured children

                    require(numItems % 2 == 0) { "ZNavigationBar requires an even number of items (excluding FAB)" }
                    val itemsPerSide = numItems / 2

                    val itemWidth =
                        constraints.maxWidth / (numItems + 1) // Divide space including FAB gap

                    val placeable = measures.map {
                        it.measure(constraints.copy(minWidth = 0, maxWidth = itemWidth))
                    }
                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var xPosition = 0
                        // Place left items
                        placeable.take(itemsPerSide).forEach { placeable ->
                            placeable.placeRelative(xPosition, 0)
                            xPosition += itemWidth
                        }

                        // Skip the center space for FAB
                        xPosition += itemWidth

                        // Place right items
                        placeable.takeLast(itemsPerSide).forEach { placeable ->
                            placeable.placeRelative(xPosition, 0)
                            xPosition += itemWidth
                        }
                    }
                }
            }

            // FAB placed on top, aligned to bottom center and offset
            Box(
                modifier = Modifier
                    .size(FabDiameter)
                    .align(Alignment.BottomCenter)
                    // Adjust offset: half nav bar height up, then quarter FAB diameter down
                    .offset(y = -(NavBarHeight / 2) + (FabDiameter / 4))
                    .clip(CircleShape)
                    .background(colors.fabContainerColor)
                    .clickable(
                        onClick = onFabClick,
                        role = Role.Button,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                CompositionLocalProvider(LocalContentColor provides colors.fabColor) {
                    fab()
                }
            }
        }
    }
}


@Composable
fun ZNavigationBar(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: ZNavColors = ZDefaultNavColors.colors(),
    shadowElevation: Dp = NavBarShadowElevation,
    shadowColor: Color = Color(0x33001869), // Default subtle shadow
    items: @Composable ZNavItemScope.() -> Unit,
) {
    val scope = remember(colors) { ZNavItemScopeImpl(colors) }

    CompositionLocalProvider(
        LocalZNavigationBarColor provides colors,
        LocalTextStyle provides textStyle,
        LocalZIconSize provides 32.dp,
    ) {
        Layout(
            modifier = modifier
                .height(NavBarHeight)
                // Apply shadow and shape using graphicsLayer
                .graphicsLayer {
                    this.clip = false
                    this.ambientShadowColor = shadowColor
                    this.spotShadowColor = shadowColor
                    this.shadowElevation = shadowElevation.toPx()
                }
                // Background color drawn within the final clipped shape
                .background(colors.containerColor),
            content = {
                scope.items()
            }, // Render all items defined in the scope
        ) { measures, constraints ->
            val numItems = measures.size // Get count directly from measured children
            val itemWidth = constraints.maxWidth / numItems // Divide space including FAB gap

            val placeable = measures.map {
                it.measure(constraints.copy(minWidth = 0, maxWidth = itemWidth))
            }
            layout(constraints.maxWidth, constraints.maxHeight) {
                var xPosition = 0
                // Place left items
                placeable.take(numItems).forEach { placeable ->
                    placeable.placeRelative(xPosition, 0)
                    xPosition += itemWidth
                }
            }
        }
    }
}


// --- Preview ---

@ThemePreviews
@Composable
fun ZNavigationBarPreview() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "QuickTrade", "CryptoPacks", "Exchange")
    val icons = listOf(
        MenuIcon(
            defaultIcon = ZIcons.ic_home_default.asZIcon,
            selectedIcon = ZIcons.ic_home_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_quicktrade_default.asZIcon,
            selectedIcon = ZIcons.ic_quicktrade_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_cryptopacks_default.asZIcon,
            selectedIcon = ZIcons.ic_cryptopacks_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_exchange_default.asZIcon,
            selectedIcon = ZIcons.ic_exchange_active.asZIcon,
        ),
    )
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(0.dp),
    ) {
        BlankHeight(30.dp)
        ZNavigationBar(
            fab = { ZIcon(ZIcons.ic_add.asZIcon, contentDescription = "Add") },
            onFabClick = { },
            textStyle = ZTheme.typography.bodyRegularB4,
            colors = ZDefaultNavColors.colors(
                activeIcon = Color.Unspecified,
            ),
        ) {
            items.forEachIndexed { index, label ->
                ZNavItem(
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    icon = {
                        val icon = icons[index]
                        ZIcon(
                            icon = if (selectedItem == index) icon.selectedIcon else icon.defaultIcon,
                            contentDescription = label,
                        )
                    },
                    label = {
                        Text(label)
                    },
                )
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZNavigationBarWithoutFab() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("QuickTrade", "Exchange", "CryptoPacks", "Portfolio", "Futures")
    val icons = listOf(
        MenuIcon(
            defaultIcon = ZIcons.ic_quicktrade_default.asZIcon,
            selectedIcon = ZIcons.ic_quicktrade_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_exchange_default.asZIcon,
            selectedIcon = ZIcons.ic_exchange_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_cryptopacks_default.asZIcon,
            selectedIcon = ZIcons.ic_cryptopacks_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_portfolio_default.asZIcon,
            selectedIcon = ZIcons.ic_portfolio_active.asZIcon,
        ),
        MenuIcon(
            defaultIcon = ZIcons.ic_future_default.asZIcon,
            selectedIcon = ZIcons.ic_future_active.asZIcon,
        ),
    )
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(0.dp),
    ) {
        BlankHeight(30.dp)
        ZNavigationBar(
            textStyle = ZTheme.typography.bodyRegularB4,
            colors = ZDefaultNavColors.colors(
                activeIcon = Color.Unspecified,
            ),
        ) {
            items.forEachIndexed { index, label ->
                ZNavItem(
                    alwaysShowLabel = true,
                    selected = selectedItem == index,
                    onClick = { selectedItem = index },
                    icon = {
                        val icon = icons[index]
                        ZIcon(
                            icon = if (selectedItem == index) icon.selectedIcon else icon.defaultIcon,
                            contentDescription = label,
                        )
                    },
                    label = {
                        Text(label)
                    },
                )
            }
        }
    }
}
