package com.zebpay.catalog.header

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ShowcaseCheckbox
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.header.ZToolbarTextAction
import com.zebpay.ui.v3.components.atoms.header.ZToolbarToggleAction
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.header.ZToolbar
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.background

@Composable
fun ZHeaderScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
) {
    var isSearching by remember { mutableStateOf(false) }
    var toggleVisible by remember { mutableStateOf(false) }
    var textButton by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(true) }
    var showBackNav by remember { mutableStateOf(false) }
    var titleText by remember { mutableStateOf("Headline") }
    var ctaText by remember { mutableStateOf("CTA") }
    var searchQuery by remember { mutableStateOf("") }
    var onGradient by remember { mutableStateOf(false) }

    CatalogScaffold(
        modifier = modifier,
        topBar = {
            ZToolbar(
                modifier = Modifier.background(gradient = ZGradientColors.Primary01),
                onGradient = onGradient,
                title = if (showTitle) titleText else "",
                isSearching = isSearching,
                searchQuery = searchQuery,
                onSearchTextChange = {
                    searchQuery = it
                },
                onClearSearch = {
                    searchQuery = ""
                },
                onBackPressed = {
                    if (isSearching) {
                        isSearching = false
                    } else {
                        onNavBack.invoke()
                    }
                },
                navIcon = ZIcons.ic_hamburger.asZIcon,
                showBackAction = showBackNav,
                actions = {
                    if (toggleVisible) {
                        val selectedTab = remember { mutableIntStateOf(0) }
                        ZToolbarToggleAction(
                            options = listOf(
                                {
                                    ZIcon(
                                        size = 12.dp,
                                        icon = ZIcons.ic_currency_inr.asZIcon,
                                        contentDescription = "INR",
                                        tint = LocalContentColor.current,
                                    )
                                },
                                {
                                    ZIcon(
                                        size = 12.dp,
                                        icon = ZIcons.ic_currency_tether.asZIcon,
                                        contentDescription = "USDT",
                                        tint = LocalContentColor.current,
                                    )
                                },
                            ),
                            selectedIndex = selectedTab.intValue,
                            onSelect = { selectedTab.intValue = it },
                        )
                    }

                    if (textButton) {
                        ZToolbarTextAction(
                            ctaLabel = ctaText, onClick = {},
                            onGradient = onGradient,
                        )
                    } else {
                        ZToolbarIconAction(
                            icon = ZIcons.ic_search.asZIcon,
                            onClick = {
                                isSearching = true
                            },
                            onGradient = onGradient,
                        )
                        ZToolbarIconAction(
                            icon = ZIcons.ic_edit.asZIcon,
                            onClick = {},
                            onGradient = onGradient,
                        )
                    }
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.size(16.dp))

            ShowcaseCheckbox(
                "Show Toggle Button",
                toggleVisible,
                onToggle = { toggle ->
                    toggleVisible = toggle
                },
            )

            ShowcaseCheckbox(
                "Show Text Button",
                textButton,
                onToggle = { toggle ->
                    textButton = toggle
                },
            )
            if (textButton) {
                OutlinedTextField(
                    value = ctaText,
                    onValueChange = { ctaText = it },
                    label = { Text("Text Button Label") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
            ShowcaseCheckbox(
                "Show Search Bar",
                isSearching,
                onToggle = { toggle ->
                    isSearching = toggle
                },
            )
            ShowcaseCheckbox(
                "Show On Gradient",
                onGradient,
                onToggle = { toggle ->
                    onGradient = toggle
                },
            )
            ShowcaseCheckbox(
                "Show Back Nav",
                showBackNav,
                onToggle = { toggle ->
                    showBackNav = toggle
                },
            )
            ShowcaseCheckbox(
                "Show Title",
                showTitle,
                onToggle = { toggle ->
                    showTitle = toggle
                },
            )

            if (showTitle) {
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { titleText = it },
                    label = { Text("Top Header Title") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                )
            }
        }
    }
}