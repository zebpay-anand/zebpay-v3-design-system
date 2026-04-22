package com.zebpay.catalog.buttons

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import android.net.Uri
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ChipSelector
import com.zebpay.catalog.common.ShowcaseToggle
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZButtonSize
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZOutlineColor
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryColor
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme

@Composable
fun ZButtonScreen(
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
            ZFilledButtonShowcase(Modifier.padding(innerPadding))
        }
    }
}


@Composable
fun ZFilledButtonShowcase(modifier: Modifier = Modifier) {
    val variantOptions = arrayListOf(
        ZButtonVariantOption.ZPrimaryButton,
        ZButtonVariantOption.ZSecondaryButton,
        ZButtonVariantOption.ZTextButton,
    )
    var variant by remember { mutableStateOf<ZButtonVariantOption>(ZButtonVariantOption.ZPrimaryButton) }

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        when (variant) {
            ZButtonVariantOption.ZPrimaryButton -> {
                ZPrimaryButtonShowcase {
                    // Variant Chips
                    ChipSelector(
                        label = "Variant",
                        options = variantOptions,
                        selected = variant,
                        onSelectedChange = { variant = it },
                    )
                }
            }

            ZButtonVariantOption.ZSecondaryButton -> {
                ZOutlineButtonShowcase {
                    // Variant Chips
                    ChipSelector(
                        label = "Variant",
                        options = variantOptions,
                        selected = variant,
                        onSelectedChange = { variant = it },
                    )
                }
            }

            ZButtonVariantOption.ZTextButton -> {
                ZTextButtonShowcase {
                    // Variant Chips
                    ChipSelector(
                        label = "Variant",
                        options = variantOptions,
                        selected = variant,
                        onSelectedChange = { variant = it },
                    )
                }
            }
        }

    }
}

@Composable
fun ZPrimaryButtonShowcase(
    variantSelection: @Composable () -> Unit,
) {
    Column {
        var helperText by remember { mutableStateOf("CTA") }
        var zFilledColor by remember { mutableStateOf(ZPrimaryColor.Blue) }
        var enabled by remember { mutableStateOf(true) }
        var iconPrefix by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        var hasLeadingIcon by remember { mutableStateOf(true) }
        var hasTrailingIcon by remember { mutableStateOf(true) }
        val leadingIcon = if (hasLeadingIcon)
            if (iconPrefix)
                Uri.parse("https://static.zebpay.com/multicoins/ic_coin_btc.png").asZIcon
            else Icons.AutoMirrored.Rounded.ArrowBack.asZIcon
        else null
        val trailingIcon = if (hasTrailingIcon) if (iconPrefix)
            Uri.parse("https://static.zebpay.com/multicoins/ic_coin_usdt.png").asZIcon
        else ZIcon.Vector(Icons.AutoMirrored.Rounded.ArrowForward) else null
        ZPrimaryButton(
            title = helperText,
            onClick = { Log.d("ZButton", "Clicked!") },
            buttonColor = zFilledColor,
            tagId = "",
            enabled = enabled,
            isLoading = isLoading,
            leading = if (hasLeadingIcon) {
                {
                    if (leadingIcon != null) {
                        ZIcon(
                            icon = leadingIcon,
                        )
                    }
                }
            } else {
                null
            },
            trailing = if (hasTrailingIcon) {
                {
                    if (trailingIcon != null) {
                        ZIcon(
                            icon = trailingIcon,
                        )
                    }
                }
            } else {
                null
            },
        )
        BlankHeight(16.dp)
        Text(
            "Customize Button",
            style = MaterialTheme.typography.titleMedium
                .copy(color = ZTheme.color.text.primary),
        )
        variantSelection()
        BlankHeight(8.dp)
        OutlinedTextField(
            value = helperText,
            onValueChange = { helperText = it },
            label = { Text("Button Text") },
            modifier = Modifier.fillMaxWidth(),
        )
        BlankHeight(8.dp)
        ChipSelector(
            label = "Color",
            options = ZPrimaryColor.entries,
            selected = zFilledColor,
            onSelectedChange = { zFilledColor = it },
        )
        ShowcaseToggle("Active/Disable", enabled) { enabled = it }
        ShowcaseToggle("Url Icon Prefix", iconPrefix) { iconPrefix = it }
        ShowcaseToggle("Icon Left", hasLeadingIcon) { hasLeadingIcon = it }
        ShowcaseToggle("Icon Right", hasTrailingIcon) { hasTrailingIcon = it }
        ShowcaseToggle("Loader", isLoading) { isLoading = it }
    }
}


@Composable
fun ZOutlineButtonShowcase(
    variantSelection: @Composable () -> Unit,
) {
    Column {
        var helperText by remember { mutableStateOf("CTA") }
        var zFilledColor by remember { mutableStateOf(ZOutlineColor.Blue) }
        var enabled by remember { mutableStateOf(true) }
        var iconPrefix by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        var hasLeadingIcon by remember { mutableStateOf(true) }
        var hasTrailingIcon by remember { mutableStateOf(true) }
        val leadingIcon = if (hasLeadingIcon)
            if (iconPrefix)
                Uri.parse("https://static.zebpay.com/multicoins/ic_coin_btc.png").asZIcon
            else Icons.AutoMirrored.Rounded.ArrowBack.asZIcon
        else null
        val trailingIcon = if (hasTrailingIcon) if (iconPrefix)
            Uri.parse("https://static.zebpay.com/multicoins/ic_coin_usdt.png").asZIcon
        else ZIcon.Vector(Icons.AutoMirrored.Rounded.ArrowForward) else null
        ZOutlineButton(
            title = helperText,
            onClick = { Log.d("ZButton", "Clicked!") },
            buttonColor = zFilledColor,
            tagId = "",
            enabled = enabled,
            isLoading = isLoading,
            leading = if (hasLeadingIcon) {
                {
                    if (leadingIcon != null) {
                        ZGradientIcon(
                            icon = leadingIcon,
                        )
                    }
                }
            } else {
                null
            },
            trailing = if (hasTrailingIcon) {
                {
                    if (trailingIcon != null) {
                        ZGradientIcon(
                            icon = trailingIcon,
                        )
                    }
                }
            } else {
                null
            },
        )
        BlankHeight(16.dp)
        Text(
            "Customize Button",
            style = MaterialTheme.typography.titleMedium
                .copy(color = ZTheme.color.text.primary),
        )
        variantSelection()
        BlankHeight(8.dp)
        OutlinedTextField(
            value = helperText,
            onValueChange = { helperText = it },
            label = { Text("Button Text") },
            modifier = Modifier.fillMaxWidth(),
        )
        BlankHeight(8.dp)
        ChipSelector(
            label = "Color",
            options = ZOutlineColor.entries,
            selected = zFilledColor,
            onSelectedChange = { zFilledColor = it },
        )
        ShowcaseToggle("Active/Disable", enabled) { enabled = it }
        ShowcaseToggle("Url Icon Prefix", iconPrefix) { iconPrefix = it }
        ShowcaseToggle("Icon Left", hasLeadingIcon) { hasLeadingIcon = it }
        ShowcaseToggle("Icon Right", hasTrailingIcon) { hasTrailingIcon = it }
        ShowcaseToggle("Loader", isLoading) { isLoading = it }
    }
}


@Composable
fun ZTextButtonShowcase(
    variantSelection: @Composable () -> Unit,
) {
    Column {
        var helperText by remember { mutableStateOf("CTA") }
        var zFilledColor by remember { mutableStateOf(ZTextButtonColor.Blue) }
        var size by remember { mutableStateOf(ZButtonSize.Big) }
        var enabled by remember { mutableStateOf(true) }
        var iconPrefix by remember { mutableStateOf(false) }
        var hasLeadingIcon by remember { mutableStateOf(true) }
        var hasTrailingIcon by remember { mutableStateOf(true) }
        val leadingIcon = if (hasLeadingIcon)
            if (iconPrefix)
                Uri.parse("https://static.zebpay.com/multicoins/ic_coin_btc.png").asZIcon
            else Icons.AutoMirrored.Rounded.ArrowBack.asZIcon
        else null
        val trailingIcon = if (hasTrailingIcon) if (iconPrefix)
            Uri.parse("https://static.zebpay.com/multicoins/ic_coin_usdt.png").asZIcon
        else ZIcon.Vector(Icons.AutoMirrored.Rounded.ArrowForward) else null
        ZTextButton(
            title = helperText,
            onClick = { Log.d("ZButton", "Clicked!") },
            textButtonColor = zFilledColor,
            buttonSize = size,
            tagId = "",
            enabled = enabled,
            leading = if (hasLeadingIcon) {
                {
                    if (leadingIcon != null) {
                        ZGradientIcon(
                            icon = leadingIcon,
                        )
                    }
                }
            } else {
                null
            },
            trailing = if (hasTrailingIcon) {
                {
                    if (trailingIcon != null) {
                        ZGradientIcon(
                            icon = trailingIcon,
                        )
                    }
                }
            } else {
                null
            },
        )
        BlankHeight(16.dp)
        Text(
            "Customize Button",
            style = MaterialTheme.typography.titleMedium
                .copy(color = ZTheme.color.text.primary),
        )
        variantSelection()
        BlankHeight(8.dp)
        OutlinedTextField(
            value = helperText,
            onValueChange = { helperText = it },
            label = { Text("Button Text") },
            modifier = Modifier.fillMaxWidth(),
        )
        BlankHeight(8.dp)
        ChipSelector(
            label = "Color",
            options = ZTextButtonColor.entries,
            selected = zFilledColor,
            onSelectedChange = { zFilledColor = it },
        )
        ChipSelector(
            label = "Size",
            options = ZButtonSize.entries,
            selected = size,
            onSelectedChange = { size = it },
        )
        ShowcaseToggle("Active/Disable", enabled) { enabled = it }
        ShowcaseToggle("Url Icon Prefix", iconPrefix) { iconPrefix = it }
        ShowcaseToggle("Icon Left", hasLeadingIcon) { hasLeadingIcon = it }
        ShowcaseToggle("Icon Right", hasTrailingIcon) { hasTrailingIcon = it }
    }
}

sealed class ZButtonVariantOption(val label: String) {
    data object ZPrimaryButton : ZButtonVariantOption("Primary") {
        override fun toString(): String = label
    }

    data object ZSecondaryButton : ZButtonVariantOption("Secondary") {
        override fun toString(): String = label
    }

    data object ZTextButton : ZButtonVariantOption("Text") {
        override fun toString(): String = label
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewZFilledButtonShowcase() {
    MaterialTheme {
        ZFilledButtonShowcase()
    }
}


