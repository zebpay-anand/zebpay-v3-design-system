package com.zebpay.catalog.tags

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ChipSelector
import com.zebpay.catalog.common.ShowcaseCheckbox
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.tags.PnLTag
import com.zebpay.ui.v3.components.molecules.tags.TagVariant
import com.zebpay.ui.v3.components.molecules.tags.ZStatusTag
import com.zebpay.ui.v3.components.molecules.tags.ZTagColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews


@Composable
fun ZTagScreen(
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
            ZTagShowcaseScreen(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ZTagShowcaseScreen(modifier: Modifier = Modifier) {
    var showcaseType by remember { mutableStateOf(ZShowcaseType.StatusTag) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        ChipSelector(
            label = "Select Tag Variant",
            options = ZShowcaseType.entries,
            selected = showcaseType,
            onSelectedChange = { showcaseType = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        when (showcaseType) {
            ZShowcaseType.StatusTag -> ZStatusTagShowcase()
            ZShowcaseType.PNLTag -> PnLTagShowcase()
        }
    }
}


@Composable
fun PnLTagShowcase(modifier: Modifier = Modifier) {
    var label by remember { mutableStateOf(ZPNLLabel.PROFIT) }
    var percentage by remember { mutableDoubleStateOf(15.99) }
    var helperText by remember { mutableStateOf("Profit") }
    var showHelper by remember { mutableStateOf(false) }
    var showIcon by remember { mutableStateOf(true) }

    Column(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(12.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            PnLTag(
                modifier = Modifier.align(Alignment.Center),
                percentage = percentage,
                helperText = if (showHelper) helperText else null,
                showIcon = showIcon,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // PNL Chips
        ChipSelector(
            label = "PNL Type",
            options = ZPNLLabel.entries,
            selected = label,
            onSelectedChange = {
                label = it
                percentage = when (it) {
                    ZPNLLabel.PROFIT -> 15.99
                    ZPNLLabel.LOSS -> -12.50
                    ZPNLLabel.NEUTRAL -> 0.0
                }
                helperText = when (it) {
                    ZPNLLabel.PROFIT -> "Profit"
                    ZPNLLabel.LOSS -> "Loss"
                    ZPNLLabel.NEUTRAL -> "N/A"
                }
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "Percentage: ${"%.2f".format(percentage)}%")

        ShowcaseCheckbox(
            "Show Helper Text",
            showHelper,
            onToggle = {
                showHelper = it
            },
        )

        if (showHelper) {
            OutlinedTextField(
                value = helperText,
                onValueChange = { helperText = it },
                label = { Text("Profit") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        ShowcaseCheckbox(
            "Show Icon",
            showIcon,
            onToggle = {
                showIcon = it
            },
        )
    }
}


@Composable
fun ZStatusTagShowcase(modifier: Modifier = Modifier) {
    var primaryText by remember { mutableStateOf("Text 1") }
    var helperText by remember { mutableStateOf("Text 2") }
    var showHelper by remember { mutableStateOf(true) }
    var showIcon by remember { mutableStateOf(true) }
    var showAnimation by remember { mutableStateOf(true) }
    var selectedVariant by remember { mutableStateOf(TagVariant.Regular) }
    var selectedColor by remember { mutableStateOf(ZTagColor.Green) }

    val allVariants = TagVariant.entries
    val allColors = ZTagColor.entries

    val icon = if (selectedColor == ZTagColor.Red) {
        ZIcons.ic_downwards.asZIcon
    } else {
        ZIcons.ic_upwards.asZIcon
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
    ) {

        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            ZStatusTag(
                modifier = Modifier.align(Alignment.Center),
                primaryText = primaryText,
                helperText = if (showHelper) helperText else null,
                animate = showAnimation,
                variant = selectedVariant,
                colorType = selectedColor,
                showIcon = showIcon,
                prefixZIcon = icon, // or expose this as user-selectable
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = primaryText,
            onValueChange = { primaryText = it },
            label = { Text("Primary Text") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(checked = showHelper, onCheckedChange = { showHelper = it })
            Text("Show Helper Text")
        }

        if (showHelper) {
            OutlinedTextField(
                value = helperText,
                onValueChange = { helperText = it },
                label = { Text("Helper Text") },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ShowcaseCheckbox(
            "Show Prefix Icon", showIcon,
            onToggle = {
                showIcon = it
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        ShowcaseCheckbox(
            "Show Animation", showAnimation,
            onToggle = {
                showAnimation = it
            },
        )

        Spacer(modifier = Modifier.height(16.dp))

        ChipSelector(
            label = "Tag Variant",
            options = allVariants,
            selected = selectedVariant,
            onSelectedChange = { selectedVariant = it },
        )
        Spacer(modifier = Modifier.height(16.dp))

        ChipSelector(
            label = "Tag Color",
            options = allColors,
            selected = selectedColor,
            onSelectedChange = { selectedColor = it },
        )
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@ThemePreviews
@Composable
fun PreviewZStatusTag() {
    ZebpayTheme {
        ZStatusTagShowcase(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = ZTheme.color.background.default,
                ),
        )
    }
}

@ThemePreviews
@Composable
fun PreviewPnLTag() {
    ZebpayTheme {
        PnLTagShowcase(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = ZTheme.color.background.default,
                ),
        )
    }
}


enum class ZShowcaseType {
    StatusTag, PNLTag
}

enum class ZPNLLabel {
    PROFIT, LOSS, NEUTRAL
}
