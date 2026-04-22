package com.zebpay.ui.v3.components.molecules.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.selections.ZRadioButton
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.resource.ZIcons

@Composable
fun ZDropdownItem(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String? = null,
    icon: ZIcon? = null,
    selected: Boolean = false,
    onSelect: () -> Unit,
) {
    val style = if (selected)
        ZTheme.typography.bodyRegularB2.semiBold()
    else
        ZTheme.typography.bodyRegularB2

    val background = if (selected)
        ZTheme.color.card.selected
    else
        ZTheme.color.background.default
    Column(
        modifier.background(color = background)
            .clickable {
                onSelect.invoke()
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ZRadioButton(
                selected = selected,
                onClick = onSelect,
                size = 24.dp,
            )
            if (icon != null) {
                ZIcon(icon = icon)
            }
            Text(
                text = title,
                style = style,
                color = ZTheme.color.text.primary,
            )
            if (!subTitle.isNullOrEmpty()) {
                Text(
                    text = subTitle,
                    style = ZTheme.typography.bodyRegularB2,
                    color = ZTheme.color.text.primary,
                )
            }
        }
        ZHorizontalDivider()
    }
}


@ThemePreviews
@Composable
fun PreviewZDropdownItem() {
    ZBackgroundPreviewContainer(
        innerPaddingValues = PaddingValues(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        ZDropdownItem(
            title = "Text 1",
            icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
            selected = false,
            onSelect = {},
        )
        ZDropdownItem(
            title = "Text 1",
            icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
            selected = false,
            onSelect = {},
        )
        ZDropdownItem(
            title = "Text 1",
            subTitle = "Subtext",
            icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
            selected = true,
            onSelect = {},
        )
        ZDropdownItem(
            title = "Text 1",
            subTitle = "Subtext",
            selected = false,
            onSelect = {},
        )
    }
}