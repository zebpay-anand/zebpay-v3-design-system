package com.zebpay.catalog.common

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.colors.utils.camelCaseToText
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.v3.components.atoms.icon.ZIcon


@Composable
fun <T> ChipSelector(
    modifier: Modifier = Modifier,
    label: String,
    options: List<T>,
    selected: T,
    isIcon: Boolean = false,
    onSelectedChange: (T) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            label, style = MaterialTheme.typography.bodyLarge,
            color = ZTheme.color.text.primary,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState()),
        ) {
            options.forEach { item ->
                FilterChip(
                    selected = item == selected,
                    onClick = { onSelectedChange(item) },
                    label = {
                        if (isIcon && item is ZIcon) {
                            ZIcon(
                                item,
                            )
                        } else {
                            Text(
                                item.toString().camelCaseToText(),
                                color = ZTheme.color.text.primary,
                            )
                        }
                    },
                    modifier = Modifier.padding(end = 4.dp),
                )
            }
        }
    }
}


@Composable
fun ShowcaseToggle(label: String, state: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            label, style = MaterialTheme.typography.bodyLarge,
            color = ZTheme.color.text.primary,
        )
        Switch(checked = state, onCheckedChange = onToggle)
    }
}


@Composable
fun ShowcaseCheckbox(
    label: String, state: Boolean, onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth(),
    ) {
        Checkbox(checked = state, onCheckedChange = onToggle)
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            color = ZTheme.color.text.primary,
        )
    }
}
