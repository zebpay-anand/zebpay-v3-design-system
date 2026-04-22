package com.zebpay.ui.v3.components.molecules.pills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.pills.ZPill
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Data class to hold the state of the pills.
 *
 * @property selectedIndex The index of the selected pill, or -1 if none is selected.
 * @property disabledIndexes A list of indexes that are disabled.
 */
@Stable
data class PillState(
    val selectedIndex: MutableState<Int> = mutableIntStateOf(-1),
    val disabledIndexes: SnapshotStateList<Int> = mutableStateListOf(),
    val rowState: LazyListState,
    val scope: CoroutineScope
) {

    /**
     * check if the pill at the given index is selected
     *
     * @param index The index of the pill to check.
     */
    fun isSelect(index: Int): Boolean {
        return selectedIndex.value == index
    }


    /**
     * Selects the pill at the given index.
     *
     * @param index The index of the pill to select.
     */
    fun select(index: Int) {
        selectedIndex.value = index
    }

    /**
     * Disables the pill at the given index.
     *
     * @param index The index of the pill to disable.
     */
    fun disable(index: Int) {
        if (!disabledIndexes.contains(index)) {
            disabledIndexes.add(index)
        }
    }

    /**
     * Enables the pill at the given index.
     *
     * @param index The index of the pill to enable.
     */
    fun enable(index: Int) {
        disabledIndexes.remove(index)
    }

    /**
     * Toggles the disabled state of the pill at the given index.
     *
     * @param index The index of the pill to toggle.
     */
    fun toggleDisable(index: Int) {
        if (disabledIndexes.contains(index)) {
            disabledIndexes.remove(index)
        } else {
            disabledIndexes.add(index)
        }
    }
}

/**
 * Remembers and creates a [PillState] instance.
 *
 * @param initialSelectedIndex The initial index of the selected pill.
 * @param initialDisabledIndexes The initial list of disabled indexes.
 * @return A [PillState] instance.
 */
@Composable
fun rememberPillState(
    initialSelectedIndex: Int = -1,
    initialDisabledIndexes: List<Int> = emptyList(),
    rowState: LazyListState = rememberLazyListState(),
    scope: CoroutineScope = rememberCoroutineScope(),
): PillState {
    return remember {
        PillState(
            selectedIndex = mutableIntStateOf(initialSelectedIndex),
            disabledIndexes = mutableStateListOf<Int>().apply { addAll(initialDisabledIndexes) },
            rowState = rowState,
            scope = scope
        )
    }
}

/**
 * A composable function that displays a list of horizontal pills.
 * @param pillState The [PillState] to manage the state of the pills.
 * @param items The list of items to display as pills.
 * @param modifier The modifier to apply to the row.
 * @param itemView The Composable to show Items in Row
 */
@Composable
fun <T> ZPillRow(
    pillState: PillState,
    items: List<T>,
    modifier: Modifier = Modifier,
    contentPadding:PaddingValues = PaddingValues(horizontal = 8.dp),
    itemView: @Composable (Int,T)->Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = contentPadding,
        state = pillState.rowState,
    ) {
        itemsIndexed(items){index,item->
            itemView(index,item)
        }
    }
}

@ThemePreviews
@Composable
fun PillShowcasePreview() {
    ZBackgroundPreviewContainer {
        val disableIndex = remember { mutableStateListOf(2) }
        val pillState =
            rememberPillState(initialSelectedIndex = 0, initialDisabledIndexes = disableIndex)
        ZPillRow(
            modifier = Modifier.padding(8.dp),
            pillState = pillState,
            items = listOf("Pill 1", "Pill 2", "Pill 3", "Pill 4", "Pill 5")
        ){index,item->
            ZPill(
                text = item,
                isSelected = pillState.isSelect(index),
                enable = pillState.disabledIndexes.contains(index).not(),
                onCheckedChange = {
                    pillState.select(index)
                },
            )
        }
    }
}