package com.zebpay.catalog.dropdown

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import android.net.Uri
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ChipSelector
import com.zebpay.catalog.common.ShowcaseToggle
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.organisms.selector.DropDownState
import com.zebpay.ui.v3.components.organisms.selector.SelectorItem
import com.zebpay.ui.v3.components.organisms.selector.ZDropdownSelector
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme


@Composable
fun ZDropdownScreen(
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
            ZDropdownSelectorShowcase(modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ZDropdownSelectorShowcase(modifier: Modifier = Modifier) {
    var defaultIcon by remember { mutableStateOf(ZIcons.ic_dual_tone_cryptopacks.asZIcon) }
    var moreItems by remember { mutableStateOf(true) }

    var text1 by remember { mutableStateOf("Text 1") }
    var subtext by remember { mutableStateOf("SubText") }
    var addIcon by remember { mutableStateOf(true) }

    val availableIcons = listOf(
        ZIcons.ic_dual_tone_cryptopacks.asZIcon,
        ZIcons.ic_dual_tone_gst.asZIcon,
        ZIcons.ic_dual_tone_fees.asZIcon,
        ZIcons.ic_dual_tone_coins.asZIcon,
        ZIcons.ic_dual_tone_fiat_deposit.asZIcon,
        Uri.parse("https://static.zebpay.com/multicoins/ic_coin_btc.png").asZIcon,
        Uri.parse("https://static.zebpay.com/multicoins/ic_coin_usdt.png").asZIcon,
    )
    val dropdownState = remember {
        mutableStateOf(
            DropDownState(
                items = arrayListOf(
                    SelectorItem(
                        "Option A",
                        "Primary option",
                        ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                    ),
                    SelectorItem(
                        "Option B",
                        "Secondary option",
                        ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                    ),
                    SelectorItem(
                        "Option C",
                        "Another option",
                        ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                    ),
                ),
                selectedIndex = -1,
            ),
        )
    }

    val items = dropdownState.value.items
    val selectedIndex = dropdownState.value.selectedIndex

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {

        ZDropdownSelector(
            dropdownState = dropdownState,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(Modifier.height(8.dp))
        ShowcaseToggle("Add New Items", moreItems) { moreItems = it }
        if (moreItems) {
            ChipSelector(
                label = "Default Icon",
                options = availableIcons,
                selected = defaultIcon,
                isIcon = true,
                onSelectedChange = { selected ->
                    defaultIcon = selected
                },
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = text1,
                onValueChange = { text1 = it },
                label = { Text("Text Value") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = subtext,
                onValueChange = { subtext = it },
                label = { Text("SubText value") },
                modifier = Modifier.fillMaxWidth(),
            )
            ShowcaseToggle("Show Icon", addIcon) { addIcon = it }
            Button(
                onClick = {
                    dropdownState.value = dropdownState.value.copy(
                        items = items + SelectorItem(
                            text1,
                            subtext,
                            if (addIcon) defaultIcon else null,
                        ),
                    )
                },
            ) {
                Text("Add Item")
            }
            Spacer(Modifier.height(16.dp))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    if (items.isNotEmpty()) {
                        dropdownState.value = dropdownState.value.copy(
                            items = items.dropLast(1),
                            selectedIndex = dropdownState.value.selectedIndex
                                .takeIf { it < items.size - 1 } ?: -1,
                        )
                    }
                },
                enabled = items.isNotEmpty(),
            ) {
                Text("Remove Last")
            }

            Button(
                onClick = {
                    dropdownState.value = dropdownState.value.copy(selectedIndex = -1)
                },
            ) {
                Text("Reset")
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = if (selectedIndex >= 0)
                "Selected: ${items[selectedIndex].title}"
            else
                "No item selected",
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(Modifier.height(12.dp))
    }
}
