package com.zebpay.catalog.keyboard

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ShowcaseCheckbox
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.keyword.CustomKeyboard
import com.zebpay.ui.v3.components.molecules.keyword.KeyboardKey

@Composable
fun ZKeyboardScreen(
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
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                var typedText by remember { mutableStateOf("") }

                var shuffle by remember { mutableStateOf(false) }
                var keyboardText1 by remember { mutableStateOf("Text1") }
                var showText1 by remember { mutableStateOf(false) }

                var keyboardText2 by remember { mutableStateOf("Text2") }
                var showText2 by remember { mutableStateOf(false) }

                var ctaText by remember { mutableStateOf("CTA") }
                var showCta by remember { mutableStateOf(false) }


                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth()
                        .verticalScroll(state = rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {

                    ShowcaseCheckbox(
                        "Shuffle Keys",
                        shuffle,
                        onToggle = {
                            shuffle = it
                        },
                    )

                    ShowcaseCheckbox(
                        "Show Text1",
                        showText1,
                        onToggle = {
                            showText1 = it
                        },
                    )

                    if (showText1) {
                        OutlinedTextField(
                            value = keyboardText1,
                            onValueChange = { keyboardText1 = it },
                            label = { Text("Enter Text1") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    ShowcaseCheckbox(
                        "Show Text2",
                        showText2,
                        onToggle = {
                            showText2 = it
                        },
                    )

                    if (showText2) {
                        OutlinedTextField(
                            value = keyboardText2,
                            onValueChange = { keyboardText2 = it },
                            label = { Text("Enter Text2") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }

                    ShowcaseCheckbox(
                        "Show CTA",
                        showCta,
                        onToggle = {
                            showCta = it
                        },
                    )

                    if (showCta) {
                        OutlinedTextField(
                            value = ctaText,
                            onValueChange = { ctaText = it },
                            label = { Text("Enter CTA Text") },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
                Text("Entered Text")
                BlankHeight(12.dp)
                Text(
                    text = typedText,
                    style = ZTheme.typography.headlineRegularH1.bold(),
                    textAlign = TextAlign.Center,
                )
                BlankHeight(12.dp)

                CustomKeyboard(
                    onKeyPress = {
                        when (it) {
                            is KeyboardKey.TextKey -> {
                                if (typedText.contains(".") && it.text == ".")
                                    return@CustomKeyboard
                                typedText += it.text
                            }

                            is KeyboardKey.DrawableKey -> {
                                typedText = typedText.dropLast(1)
                            }
                        }
                    },
                    shuffleKeys = shuffle,
                    text1 = if (showText1) keyboardText1 else null,
                    text2 = if (showText2) keyboardText2 else null,
                    textCtaLabel = if (showCta) ctaText else null,
                )
            }
        }
    }
}