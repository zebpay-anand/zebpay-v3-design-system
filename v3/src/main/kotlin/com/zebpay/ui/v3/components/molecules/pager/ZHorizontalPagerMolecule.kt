package com.zebpay.ui.v3.components.molecules.pager

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.atoms.misc.ZIndicator
import com.zebpay.ui.v3.components.molecules.card.ZCardRow
import com.zebpay.ui.v3.components.resource.ZIcons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> ZCircularCardPager(
    items: List<T>,
    modifier: Modifier = Modifier,
    autoRun: Boolean = true,
    autoRunDelayMillis: Long = 5000L,
    pagerState: PagerState = rememberPagerState(
        initialPage = items.size * 1000, // Start in the middle for "infinite" feel
        pageCount = { if (items.isNotEmpty()) Int.MAX_VALUE else 0 }, // Effectively infinite pages
    ),
    indicatorModifier: Modifier = Modifier.padding(vertical = 16.dp),
    itemContent: @Composable (item: T, pageIndex: Int) -> Unit,
) {
    if (items.isEmpty()) {
        // Optionally display a placeholder if the list is empty
        return
    }

    val actualPageCount = items.size
    val coroutineScope = rememberCoroutineScope()

    // Calculate the current page index within the bounds of the actual item list
    val currentPage = remember {
        derivedStateOf { pagerState.currentPage % actualPageCount }
    }.value

    // Auto-scroll effect
    LaunchedEffect(key1 = autoRun, key2 = pagerState.isScrollInProgress) {
        if (autoRun && !pagerState.isScrollInProgress) {
            while (true) {
                delay(autoRunDelayMillis)
                val nextPage = pagerState.currentPage + 1
                coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(durationMillis = 700), // Animation duration for scroll
                    )
                }
            }
        }
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            // contentPadding = PaddingValues(horizontal = 32.dp), // Add padding to show parts of next/prev items
            // pageSpacing = 8.dp // Space between pages
        ) { pageIndex ->
            val actualIndex = pageIndex % actualPageCount
            val item = items[actualIndex]
            itemContent(item, actualIndex)
        }
        // Indicator
        ZIndicator(
            totalDots = actualPageCount,
            selectedIndex = currentPage,
            modifier = indicatorModifier,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZCircularCardPager() {
    ZBackgroundPreviewContainer {
        val sampleItems = remember { List(5) { index -> "Page $index" } }
        var autoRun by remember { mutableStateOf(true) }
        ZCircularCardPager(
            items = sampleItems,
            autoRun = autoRun,
        ) { itemData, _ ->
            ZCardRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                heading = "Receive Crypto",
                subtext = "Securely receive crypto & grow your portfolio effortlessly.",
                onClick = {},
                prefixIcon = ZIcons.ic_download.asZIcon,
            )
        }
        BlankHeight(16.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Auto-Run")
            Spacer(Modifier.width(8.dp))
            Switch(checked = autoRun, onCheckedChange = { autoRun = it })
        }
    }
}


