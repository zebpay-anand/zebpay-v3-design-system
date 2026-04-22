package com.zebpay.catalog.toast

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ChipSelector
import com.zebpay.catalog.common.ShowcaseToggle
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.v3.components.molecules.toast.ZNote
import com.zebpay.ui.v3.components.molecules.toast.ZNoteType
import com.zebpay.ui.v3.components.molecules.toast.ZToast
import com.zebpay.ui.v3.components.molecules.toast.ZToastType


@Composable
fun ZToastScreen(
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
            ZNoteToastShowcase(modifier.padding(innerPadding))
        }
    }
}


@Composable
fun ZNoteToastShowcase(modifier: Modifier = Modifier) {
    val variants = listOf("Note", "Toast")
    var selectedVariant by remember { mutableStateOf(variants[0]) }

    val noteTypes = ZNoteType.entries
    val toastTypes = ZToastType.entries

    var selectedNoteType by remember { mutableStateOf(ZNoteType.Info) }
    var selectedToastType by remember { mutableStateOf(ZToastType.Info) }

    var title by remember { mutableStateOf("This is a title") }
    var message by remember { mutableStateOf("Lorem ipsum is a dummy or placeholder text commonly used in graphic design, publishing, and web development.") }
    var showShadow by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Spacer(Modifier.height(12.dp))

        when (selectedVariant) {
            "Note" -> {
                ZNote(
                    title = title,
                    message = message.takeIf { it.isNotBlank() },
                    type = selectedNoteType,
                    showShadow = showShadow,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {},
                )

                Spacer(Modifier.height(12.dp))
                ChipSelector(
                    label = "Note Type",
                    options = noteTypes,
                    selected = selectedNoteType,
                    onSelectedChange = { selectedNoteType = it },
                )
            }

            "Toast" -> {
                ZToast(
                    title = title,
                    message = message,
                    type = selectedToastType,
                    showShadow = showShadow,
                    onClose = {},
                )
                Spacer(Modifier.height(12.dp))
                ChipSelector(
                    label = "Toast Type",
                    options = toastTypes,
                    selected = selectedToastType,
                    onSelectedChange = { selectedToastType = it },
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        ChipSelector(
            label = "Select Component Variant",
            options = variants,
            selected = selectedVariant,
            onSelectedChange = { selectedVariant = it },
        )

        ShowcaseToggle(label = "Show Shadow", state = showShadow) {
            showShadow = it
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Message (optional)") },
            modifier = Modifier.fillMaxWidth(),
        )

    }
}
