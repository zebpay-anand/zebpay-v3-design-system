package com.zebpay.ui.v3.components.atoms.header

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZButtonSize
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZHeaderGradientController
import com.zebpay.ui.v3.components.utils.setTestingTag


@Composable
fun ZToolbarIconAction(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tagID:String="",
    onGradient: Boolean = LocalZHeaderGradientController.current.onGradient,
    size: Dp = 32.dp,
    tagId: String = "",
    content:@Composable BoxScope.()->Unit,
) {
    val topBarColor = ZTheme.color.navigation.top
    val actionColor = if (onGradient) ZActionColor(
        iconColor = topBarColor.onGradientIcon,
        background = topBarColor.onGradientIconBg,
        iconBorder = topBarColor.onGradientIconBorder,
    ) else ZActionColor(
        iconColor = topBarColor.onSolidIcon,
        background = topBarColor.onSolidIconBg,
        iconBorder = topBarColor.onSolidIconBorder,
    )
    CompositionLocalProvider(
        LocalContentColor provides actionColor.iconColor,
    ) {
        Box(
            modifier = modifier
                .size(size)
                .clip(ZTheme.shapes.full)
                .safeClickable(tagId = tagID, debounceMillis = 1000) {
                    onClick.invoke()
                }
                .background(actionColor.background)
                .border(1.dp, color = actionColor.iconBorder, ZTheme.shapes.full)
        ) {
            content()
        }
    }
}

@Composable
fun ZToolbarIconAction(
    icon: ZIcon,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tagID: String="",
    onGradient: Boolean = LocalZHeaderGradientController.current.onGradient,
    size: Dp = 32.dp,
    tagId: String = "",
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    ZToolbarIconAction(
        tagID = tagID,
        onClick = onClick,
        modifier = modifier,
        tagId = tagId,
        onGradient = onGradient,
        size = size,
    ){
        ZIcon(
            modifier = Modifier
                .padding(paddingValues)
                .align(Alignment.Center),
            icon = icon,
            contentDescription = "",
        )
    }
}

@Immutable
data class ZActionColor(
    val iconBorder: Color,
    val background: Color,
    val iconColor: Color,
)


@ThemePreviews
@Composable
fun PreviewZActionIcon() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_downwards.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_edit.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_download.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_user.asZIcon)
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZGradientActionIcon() {
    ZebpayTheme {
        Column(
            Modifier
                .padding(12.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_downwards.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_edit.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_download.asZIcon)
                ZToolbarIconAction(onClick = {}, icon = ZIcons.ic_user.asZIcon)
            }
        }
    }
}


@Composable
fun ZToolbarTextAction(
    ctaLabel: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onGradient: Boolean = LocalZHeaderGradientController.current.onGradient,
    enabled: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(4.dp),
) {
    if (onGradient) {
        ZTextButton(
            modifier = modifier.padding(paddingValues = paddingValues),
            tagId = "",
            title = ctaLabel,
            onClick = onClick,
            enabled = enabled,
            buttonSize = ZButtonSize.Big,
            textButtonColor = ZTextButtonColor.White,
        )
    } else {
        ZTextButton(
            modifier = modifier.padding(paddingValues = paddingValues),
            tagId = "",
            title = ctaLabel,
            onClick = onClick,
            enabled = enabled,
            buttonSize = ZButtonSize.Big,
            textButtonColor = ZTextButtonColor.Blue,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZTextAction() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZToolbarTextAction(ctaLabel = "CTA", onClick = {})
            }
        }
    }
}


@Composable
fun ZAppBarSearchBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onGradient: Boolean = LocalZHeaderGradientController.current.onGradient,
    placeholder: String = "Search",
) {
    val textColor = if (onGradient)
        ZTheme.color.text.white
    else
        ZTheme.color.text.primary

    val textStyle = ZTheme.typography.bodyRegularB2.semiBold()

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        cursorBrush = SolidColor(textColor),
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(2.dp),
            ) {
                if (value.isEmpty() && placeholder.isEmpty().not() && isFocused.not()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholder,
                        color = textColor,
                        style = textStyle,
                    )
                }
                innerTextField()
            }
        },
        textStyle = textStyle.copy(
            color = textColor,
        ),
        modifier = modifier.setTestingTag("_action_atom_search")
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (it.isFocused) {
                    keyboardController?.show()
                }
                isFocused = it.isFocused
            },
    )
}


@ThemePreviews
@Composable
fun PreviewSearchAction() {
    ZBackgroundPreviewContainer {
        ZAppBarSearchBox("", onValueChange = {})
        BlankHeight(16.dp)
        ZAppBarSearchBox("BTC USTD", onValueChange = {})
        BlankHeight(16.dp)
        ZAppBarSearchBox("BTC USTD", onValueChange = {}, onGradient = true)
    }
}


