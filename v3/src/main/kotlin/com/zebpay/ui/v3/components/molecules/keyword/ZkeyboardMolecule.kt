package com.zebpay.ui.v3.components.molecules.keyword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.resource.ZIcons

enum class ActionKeys {
    Delete,
    Default
}

sealed interface KeyboardKey {
    data class TextKey(val text: String) : KeyboardKey
    data class DrawableKey(internal val drawable: Int) : KeyboardKey {
        val actionKey: ActionKeys
            get() = when (drawable) {
                ZIcons.ic_remove -> ActionKeys.Delete
                else -> ActionKeys.Default
            }
    }
}

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    text1: String? = null,
    text2: String? = null,
    onCtaClick: () -> Unit = {},
    textCtaLabel: String? = null,
    onKeyPress: (KeyboardKey) -> Unit,
    shuffleKeys: Boolean = false,
) {
    val numericKeys = (1..9).map { KeyboardKey.TextKey(it.toString()) } + KeyboardKey.TextKey("0")
    val specialKeys = listOf(
        KeyboardKey.TextKey(""),
        KeyboardKey.DrawableKey(ZIcons.ic_remove),
    )

    val shuffledKeys = if (shuffleKeys) numericKeys.shuffled() else numericKeys

    val keyRows = listOf(
        shuffledKeys.take(3),
        shuffledKeys.drop(3).take(3),
        shuffledKeys.drop(6).take(3),
        listOf(specialKeys.first(), shuffledKeys.last(), specialKeys.last()),
    )

    val zThemeColor = ZTheme.color

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .background(zThemeColor.background.default),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BlankHeight(6.dp)
        Row(
            modifier = Modifier.wrapContentWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (!text1.isNullOrEmpty()) {
                Text(
                    text = text1,
                    color = ZTheme.color.text.primary,
                    style = ZTheme.typography.bodyRegularB3.semiBold(),
                    textAlign = TextAlign.Center,
                )
            }
            if (!text2.isNullOrEmpty()) {
                Text(
                    text = text2,
                    color = ZTheme.color.text.primary,
                    style = ZTheme.typography.bodyRegularB4,
                    textAlign = TextAlign.Center,
                )
            }
            if (!textCtaLabel.isNullOrEmpty()) {
                ZTextButton(
                    title = textCtaLabel,
                    innerPaddingContent = PaddingValues(0.dp),
                    onClick = onCtaClick,
                    tagId = "",
                )
            }
        }
        BlankHeight(12.dp)
        keyRows.forEachIndexed { outIndex, row ->
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(0.5.dp)
                    .background(zThemeColor.separator.solidDefault),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                row.forEachIndexed { index, key ->
                    KeyButton(key = key, onKeyPress = onKeyPress, modifier = Modifier.weight(1f))
                    if (index != row.lastIndex)
                        Spacer(
                            modifier = Modifier
                                .width(0.5.dp)
                                .fillMaxHeight()
                                .background(zThemeColor.separator.solidDefault),
                        )
                }
            }

        }
    }
}

@Composable
private fun KeyButton(
    key: KeyboardKey,
    onKeyPress: (KeyboardKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clickable { onKeyPress(key) }
            .padding(vertical = 12.dp),
    ) {
        when (key) {
            is KeyboardKey.TextKey -> {
                Text(
                    text = key.text,
                    style = ZTheme.typography.headlineRegularH1.bold(),
                    color = ZTheme.color.text.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                )
            }

            is KeyboardKey.DrawableKey -> {
                ZIcon(
                    icon = key.drawable.asZIcon,
                    tint = ZTheme.color.icon.singleTonePrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .align(Alignment.Center),
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun CustomKeyboardPreview() {
    ZBackgroundPreviewContainer {
        var typedText by remember { mutableStateOf("") }
        Text(text = typedText)
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
            shuffleKeys = false,
            text1 = "Never share your OTP/PIN",
            textCtaLabel = "Biometric",
        )
    }
}