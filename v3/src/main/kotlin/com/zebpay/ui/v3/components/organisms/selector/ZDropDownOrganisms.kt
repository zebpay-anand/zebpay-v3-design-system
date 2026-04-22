package com.zebpay.ui.v3.components.organisms.selector

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.dropdown.ZDropdownItem
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.border

data class SelectorItem(
    val title: String,
    val subTitle: String? = null,
    val icon: ZIcon? = null,
)

/**
 * Represents the state of a dropdown list.
 *
 * @property items The list of items in the dropdown.
 * @property selectedIndex The index of the currently selected item. -1 if no item is selected.
 */
data class DropDownState(
    val items: List<SelectorItem>,
    val selectedIndex: Int = -1,
)

/**
 * Creates and remembers a [MutableState] of [DropDownState].
 *
 * This function is used to manage the state of a dropdown list in a composable function.
 * It ensures that the state is preserved across recompositions.
 *
 * @param items The list of items in the dropdown.
 * @param initialSelectedIndex The index of the initially selected item. Defaults to -1 (no selection).
 * @return A [MutableState] of [DropDownState] that can be used to manage the dropdown's state.
 */
@Composable
fun rememberDropDownState(
    items: List<SelectorItem>,
    initialSelectedIndex: Int = -1,
): MutableState<DropDownState> {
    return remember {
        mutableStateOf(
            DropDownState(
                items = items,
                selectedIndex = initialSelectedIndex,
            ),
        )
    }
}

/**
 * A dropdown selector component that allows users to select an item from a list.
 *
 * @param modifier The modifier to apply to the dropdown selector.
 * @param dropdownState The state object that manages the dropdown state.
 */
@Composable
fun ZDropdownSelector(
    modifier: Modifier = Modifier,
    dropdownState: MutableState<DropDownState>,
) {
    val state = dropdownState.value
    val selectedIndex = state.selectedIndex
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(ZTheme.shapes.large)
            .border(
                width = 1.dp,
                gradient = ZTheme.color.graphics.rrMeterPrimary,
                shape = ZTheme.shapes.large,
            ),
    ) {
        LazyColumn(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            itemsIndexed(state.items) { index, item ->
                ZDropdownItem(
                    title = item.title,
                    subTitle = item.subTitle,
                    icon = item.icon,
                    selected = selectedIndex == index,
                    onSelect = {
                        dropdownState.value = state.copy(selectedIndex = index)
                    },
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZDropDownSelector() {
    val items = arrayListOf(
        SelectorItem("Apple", "A juicy fruit", ZIcons.ic_dual_tone_cryptopacks.asZIcon),
        SelectorItem("Banana", "Yellow and sweet", ZIcons.ic_dual_tone_cryptopacks.asZIcon),
        SelectorItem("Cherry", "Small and red", ZIcons.ic_dual_tone_cryptopacks.asZIcon),
    )
    // Create a DropDownState with no initial selection
    val dropDownState = rememberDropDownState(items = items)
    ZBackgroundPreviewContainer {
        ZDropdownSelector(dropdownState = dropDownState)
    }
}


