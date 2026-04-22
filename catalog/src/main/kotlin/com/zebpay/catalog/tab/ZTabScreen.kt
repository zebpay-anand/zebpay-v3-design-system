package com.zebpay.catalog.tab

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.v3.components.molecules.tab.ZTabBar
import com.zebpay.ui.v3.components.molecules.tab.ZTabBarStyle
import com.zebpay.ui.v3.components.molecules.tab.rememberZTabState


@Composable
fun ZTabBarScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
) {
    ZebpayTheme {
        CatalogScaffold(
            modifier = modifier,
            title = stringResource(title),
            subTitle = stringResource(subTitle),
            onBackPressed = onNavBack,
        ) { innerPadding ->
            ZTabBarShowcaseScreen(Modifier.padding(innerPadding))
        }
    }
}


@Composable
fun ZTabBarShowcaseScreen(modifier: Modifier = Modifier) {
    var tabItemCountText by remember { mutableStateOf("5") }
    val tabItemCount = tabItemCountText.toIntOrNull()?.coerceIn(0, 100) ?: 0
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedStyle by remember { mutableStateOf(ZTabBarStyle.PRIMARY) }

    val zTabPrimaryState = rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.PRIMARY)
    val zTabSecondaryState =
        rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.SECONDARY)
    val zTabTertiaryState =
        rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.TERTIARY)

    val tabItems = listOf("Home", "Profile", "Settings")
    val variantTabItem = remember(tabItemCount) {
        List(tabItemCount) { index -> "Tab-$index" }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        ZTabBar(
            modifier = Modifier.wrapContentWidth(),
            tabItems = when (selectedStyle) {
                ZTabBarStyle.PRIMARY -> tabItems
                ZTabBarStyle.SECONDARY -> variantTabItem
                ZTabBarStyle.TERTIARY -> variantTabItem
            },
            tabState = when (selectedStyle) {
                ZTabBarStyle.PRIMARY -> zTabPrimaryState
                ZTabBarStyle.SECONDARY -> zTabSecondaryState
                ZTabBarStyle.TERTIARY -> zTabTertiaryState
            },
            onTabSelected = { selectedIndex = it },
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedStyle != ZTabBarStyle.PRIMARY) {
            OutlinedTextField(
                value = tabItemCountText,
                onValueChange = { tabItemCountText = it },
                label = { Text("Number of Tab Items") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Text(
            text = "Tab Bar Variant",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp).fillMaxWidth(),
        )

        ZTabBarStyle.entries.forEach { style ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = selectedStyle == style,
                        onClick = { selectedStyle = style },
                        role = Role.RadioButton,
                    )
                    .padding(vertical = 4.dp),
            ) {
                RadioButton(
                    selected = selectedStyle == style,
                    onClick = null,
                )
                Text(
                    text = style.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val tabText = if (selectedStyle != ZTabBarStyle.PRIMARY) {
            variantTabItem[selectedIndex]
        } else {
            tabItems[selectedIndex]
        }
        Text(
            text = "Selected Tab: $tabText",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewZFilledButtonShowcase() {
    ZebpayTheme {
        ZTabBarShowcaseScreen()
    }
}