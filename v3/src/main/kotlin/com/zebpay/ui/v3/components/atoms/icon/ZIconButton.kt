package com.zebpay.ui.v3.components.atoms.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.setTestingTag


@Composable
fun ZIconButton(
    onClick: () -> Unit,
    icon: ZIcon,
    modifier: Modifier = Modifier,
    tagId: String = "",
    enabled: Boolean = true,
    contentDescription: String? = null,
    color: Color = LocalContentColor.current,
    interactionSource: MutableInteractionSource? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    size: Dp = LocalZIconSize.current,
) {
    IconButton(
        modifier = modifier.size(size).setTestingTag(tagId).semantics{testTagsAsResourceId=true},
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        ZIcon(
            modifier = Modifier.padding(innerPadding),
            icon = icon,
            size = size,
            contentDescription = contentDescription,
            tint = if(enabled) color else ZTheme.color.icon.singleToneDisable,
        )
    }
}

@Composable
fun ZGradientIconButton(
    onClick: () -> Unit,
    icon: ZIcon,
    modifier: Modifier = Modifier,
    tagId: String = "",
    enabled: Boolean = true,
    contentDescription: String? = null,
    size: Dp = LocalZIconSize.current,
    color: ZGradient = LocalZGradientColor.current,
    interactionSource: MutableInteractionSource? = null,
    innerPadding: PaddingValues = PaddingValues(0.dp),
) {
    IconButton(
        modifier = modifier.size(size).
                testTag(tagId).semantics{
            testTagsAsResourceId = true
        },
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        ZGradientIcon(
            modifier = Modifier.padding(innerPadding),
            icon = icon,
            size = size,
            contentDescription = contentDescription,
            gradient = color,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZButtonIcon() {
    ZebpayTheme {
        Column(
            Modifier
                .padding(12.dp)
                .background(color = ZTheme.color.background.default),
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ZIconButton(
                    onClick = {}, icon = ZIcons.ic_downwards.asZIcon,
                    color = ZTheme.color.icon.singleTonePrimary,
                )
                ZIconButton(
                    onClick = {}, icon = ZIcons.ic_edit.asZIcon,
                    color = ZTheme.color.icon.singleTonePrimary,
                )
                ZGradientIconButton(onClick = {}, icon = ZIcons.ic_download.asZIcon)
                ZGradientIconButton(onClick = {}, icon = ZIcons.ic_user.asZIcon)
            }
        }
    }
}