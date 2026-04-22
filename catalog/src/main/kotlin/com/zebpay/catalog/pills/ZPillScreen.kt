package com.zebpay.catalog.pills

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.ui.v3.components.atoms.pills.ZPill
import com.zebpay.ui.v3.components.molecules.pills.ZPillRow
import com.zebpay.ui.v3.components.molecules.pills.rememberPillState
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer

@Composable
fun ZPillScreen(
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
            ZPillShowcase(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ZPillShowcase(modifier: Modifier) {
    var pillCountText by remember { mutableStateOf("5") }
    val pillCount = pillCountText.toIntOrNull()?.coerceIn(0, 100) ?: 0

    val pillState = rememberPillState(
        initialSelectedIndex = 0,
        initialDisabledIndexes = arrayListOf(2),
    )
    val items = remember(pillCount) {
        List(pillCount) { index -> "Pill-$index" }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        ZPillRow(
            pillState = pillState,
            items = items,
            modifier = Modifier.fillMaxWidth(),
        ){ index,item->
            ZPill(
                text = item,
                isSelected = pillState.isSelect(index),
                enable = pillState.disabledIndexes.contains(index).not(),
                onCheckedChange = {
                    pillState.select(index)
                },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = pillCountText,
            onValueChange = { pillCountText = it },
            label = { Text("Number of Pills") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (pillState.selectedIndex.value != -1)
                "Selected: Pill Index ${pillState.selectedIndex.value}"
            else
                "No Pill Selected",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}


@ThemePreviews
@Composable
fun ZPillShowcasePreview(){
    ZBackgroundPreviewContainer {
        ZPillShowcase(modifier = Modifier)
    }
}