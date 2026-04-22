package com.zebpay.ui.v3.components.molecules.header

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.medium
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.header.ZAppBarSearchBox
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.header.ZToolbarTextAction
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZAmountLabel
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.molecules.tags.PNLStatus
import com.zebpay.ui.v3.components.molecules.tags.ZPnlStatusTag
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZHeaderGradientController
import com.zebpay.ui.v3.components.utils.ZHeaderGradientController
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ZToolbar(
    modifier: Modifier = Modifier,
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    actionsItemSpacing: Dp = 12.dp,
    onNavClick: () -> Unit = { },
    onGradient: Boolean = false,
    isSearching: Boolean = false,
    showBackAction: Boolean = false,
    showNavAction: Boolean = true,
    searchQuery: String = "",
    searchPlaceHolder: String = "Search",
    onSearchTextChange: (String) -> Unit = {},
    onClearSearch: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    drawBelowToolbar: @Composable RowScope.() -> Unit = {},
    innerPadding: PaddingValues = PaddingValues(start = 16.dp, end = 16.dp),
    navIcon: ZIcon = ZIcons.ic_hamburger.asZIcon,
    showHorizontal: Boolean= true,
    isTransparent : Boolean = false,
) {
    ZToolbar(
        modifier = modifier,
        actions = actions,
        onNavClick = onNavClick,
        drawBelowToolbar = drawBelowToolbar,
        navIcon = navIcon,
        onGradient = onGradient,
        showBackAction = showBackAction,
        showNavAction = showNavAction,
        isSearching = isSearching,
        searchQuery = searchQuery,
        searchPlaceHolder = searchPlaceHolder,
        onSearchTextChange = onSearchTextChange,
        onClearSearch = onClearSearch,
        onBackPressed = onBackPressed,
        actionsItemSpacing = actionsItemSpacing,
        innerPadding = innerPadding,
        showHorizontal = showHorizontal,
        isTransparent = isTransparent,
    ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

/*
Gradient Conversions to Note:-
 -Text CTAs will turn from Gradient 04 to Solid White in dark theme
 -Top Navigation bar on Gradient will remain the same for both Light and Dark theme.
 */
@Composable
fun ZToolbar(
    modifier: Modifier = Modifier,
    actionsItemSpacing: Dp = 12.dp,
    actions: @Composable RowScope.() -> Unit = {},
    onNavClick: () -> Unit = { },
    navIcon: ZIcon = ZIcons.ic_hamburger.asZIcon,
    onGradient: Boolean = false,
    showBackAction: Boolean = false,
    showNavAction: Boolean = true,
    isSearching: Boolean = false,
    searchQuery: String = "",
    searchPlaceHolder: String = "Search",
    onSearchTextChange: (String) -> Unit = {},
    onClearSearch: () -> Unit = {},
    onBackPressed: () -> Unit = {},
    showHorizontal: Boolean= true,
    isTransparent : Boolean = false,
    showBelowTooBarBarContent: Boolean=false,
    innerPadding: PaddingValues = PaddingValues(start = 16.dp, end = 16.dp),
    drawBelowToolbar: @Composable RowScope.() -> Unit = {},
    title: @Composable RowScope.() -> Unit,
) {

    CompositionLocalProvider(
        LocalZHeaderGradientController provides ZHeaderGradientController(onGradient),
    ) {
        Column(modifier) {
            val themeColor = ZTheme.color
            val containerColor = if (onGradient || isTransparent)
                Color.Transparent
            else
                themeColor.background.default

            val titleContentColor = if (onGradient)
                themeColor.text.white
            else
                themeColor.text.primary
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    CompositionLocalProvider(
                        value = LocalTextStyle provides ZTheme.typography.bodyRegularB1.semiBold()
                            .copy(color = titleContentColor),
                    ) {
                        AnimatedVisibility(
                            visible = !isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally(),
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .padding(innerPadding),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically,
                                    content = title,
                                )
                            }
                        }
                        AnimatedVisibility(
                            visible = isSearching,
                            enter = fadeIn() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally(),
                        ) {
                            ZAppBarSearchBox(
                                modifier = Modifier
                                    .padding(innerPadding),
                                value = searchQuery,
                                onValueChange = onSearchTextChange,
                                placeholder = searchPlaceHolder,
                            )
                        }
                    }
                },
                actions = {
                    AnimatedVisibility(visible = (isSearching && searchQuery.isEmpty().not())) {
                        ZToolbarIconAction(
                            tagID = "clear_search",
                            icon = ZIcons.ic_cross.asZIcon,
                            onClick = onClearSearch,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                    }
                    AnimatedVisibility(visible = !isSearching) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(actionsItemSpacing),
                            verticalAlignment = Alignment.CenterVertically,
                            content = actions,
                            modifier = Modifier.padding(end = 12.dp),
                        )
                    }
                },
                navigationIcon = {
                    AnimatedVisibility(visible = isSearching || showBackAction) {
                        ZToolbarIconAction(
                            tagID = "back",
                            icon = ZIcons.ic_arrow_left.asZIcon,
                            onClick = onBackPressed,
                            modifier = Modifier.padding(start = 12.dp),
                        )
                    }
                    AnimatedVisibility(visible = !isSearching && !showBackAction && showNavAction) {
                        ZToolbarIconAction(
                            icon = navIcon,
                            onClick = onNavClick,
                            modifier = Modifier.padding(start = 12.dp),
                        )

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = containerColor,
                    titleContentColor = titleContentColor,
                ),
            )
            if(showBelowTooBarBarContent) {
                Row (modifier =
                    Modifier.windowInsetsPadding(TopAppBarDefaults.windowInsets)){
                    drawBelowToolbar()
                }
            }
            androidx.compose.animation.AnimatedVisibility(showHorizontal) {
                ZHorizontalDivider(onGradient = onGradient)
            }
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewZToolbar() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        Column {
            ZToolbar(
                title = "Headline",
                navIcon = ZIcons.ic_hamburger.asZIcon,
                actions = {
                    ZToolbarIconAction(
                        icon = ZIcons.ic_hide.asZIcon,
                        onClick = {},
                    )
                    ZToolbarIconAction(
                        icon = ZIcons.ic_edit.asZIcon,
                        onClick = {},
                    )
                    ZToolbarIconAction(
                        icon = ZIcons.ic_history.asZIcon,
                        onClick = {},
                    )
                },
            )
            BlankHeight(16.dp)
            ZToolbar(
                title = "Headline",
                navIcon = ZIcons.ic_hamburger.asZIcon,
                actions = {
                    ZToolbarIconAction(
                        icon = ZIcons.ic_add.asZIcon,
                        onClick = {},
                    )
                },
            )
            BlankHeight(16.dp)
            ZToolbar(
                title = "Headline",
                navIcon = ZIcons.ic_hamburger.asZIcon,
                actions = {
                    ZToolbarTextAction(ctaLabel = "CTA", onClick = {})
                },
            )
            BlankHeight(16.dp)
            ZToolbar(
                title = "Headline",
                navIcon = ZIcons.ic_hamburger.asZIcon,
                searchQuery = "Search",
                isSearching = true,
                actions = {
                    ZToolbarTextAction(ctaLabel = "CTA", onClick = {})
                },
            )
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewZToolbarOnGradient() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        Column(
            modifier = Modifier.background(
                ZTheme.color.tab.primary.fillActive,
            ),
        ) {
            ZToolbar(
                title = "Headline",
                onGradient = true,
                navIcon = ZIcons.ic_hamburger.asZIcon,
                actions = {
                    ZToolbarIconAction(
                        icon = ZIcons.ic_hide.asZIcon,
                        onClick = {},
                    )
                    ZToolbarIconAction(
                        icon = ZIcons.ic_edit.asZIcon,
                        onClick = {},
                    )
                    ZToolbarIconAction(
                        icon = ZIcons.ic_history.asZIcon,
                        onClick = {},
                    )
                },
            )
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewZToolbarTextActionOnGradient() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        Column(
            modifier = Modifier.background(
                ZTheme.color.tab.primary.fillActive,
            ),
        ) {
            ZToolbar(
                title = "Headline",
                onGradient = true,
                navIcon = ZIcons.ic_hamburger.asZIcon,
                actions = {
                    ZToolbarTextAction(ctaLabel = "CTA", onClick = {})
                },
            )
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewZToolbarSearchOnGradient() {
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(0.dp)) {
        Column(
            modifier = Modifier.background(
                ZTheme.color.tab.primary.fillActive,
            ),
        ) {
            ZToolbar(
                title = "Headline",
                onGradient = true,
                isSearching = true,
                searchQuery = "Search Query",
                navIcon = ZIcons.ic_hamburger.asZIcon,
            )
        }
    }
}