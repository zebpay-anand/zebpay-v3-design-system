package com.zebpay.catalog.empty

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.ChipSelector
import com.zebpay.catalog.common.ShowcaseCheckbox
import com.zebpay.catalog.common.ShowcaseToggle
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.molecules.empty.EmptyStateView
import com.zebpay.ui.v3.components.molecules.empty.ZEmptyState

@Composable
fun ZEmptyStateScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
) {
    CatalogScaffold(
        modifier = modifier,
        title = stringResource(title),
        subTitle = stringResource(subTitle),
        onBackPressed = onNavBack,
    ) { innerPadding ->
        ZEmptyStateShowcase(modifier.padding(innerPadding))
    }
}

@Composable
fun ZEmptyStateShowcase(modifier: Modifier = Modifier) {
    var emptyState by remember { mutableStateOf(ZEmptyState.BIG) }
    var illustrateType by remember { mutableStateOf(ZIllustrationType.BLUE) }
    var showTitle by remember { mutableStateOf(true) }
    var showSubTitle by remember { mutableStateOf(true) }
    var isCTAInRow by remember { mutableStateOf(true) }
    var showPrimaryCTA by remember { mutableStateOf(true) }
    var showSecondaryCTA by remember { mutableStateOf(true) }

    val icon = ZIcons.ic_glass_tick.asZIcon // Just an example icon
    val title = "No Data Available"
    val subTitle = "Looks like there's nothing here yet. Try refreshing or check back later."

    Column(
        modifier = modifier
            .fillMaxSize().verticalScroll(rememberScrollState()),
    ) {
        // The Empty State View
        EmptyStateView(
            modifier = Modifier.fillMaxWidth(),
            icon = icon,
            emptyStateType = emptyState,
            illustrationType = illustrateType,
            title = if (showTitle) title else "",
            subTitle = if (showSubTitle) subTitle else "",
            primaryCTA = if (showPrimaryCTA) "Retry" else "",
            secondaryCTA = if (showSecondaryCTA) "Learn More" else "",
            primaryClick = { println("Primary Clicked") },
            secondaryClick = { println("Secondary Clicked") },
            isCTAInRow = isCTAInRow,
        )
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Interactive controls
            Text("Controls", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            ChipSelector(
                label = "Illustrate Color Type",
                options = ZIllustrationType.entries,
                selected = illustrateType,
                onSelectedChange = { illustrateType = it },
            )

            ChipSelector(
                label = "Empty State Type",
                options = ZEmptyState.entries,
                selected = emptyState,
                onSelectedChange = { emptyState = it },
            )

            ShowcaseToggle(
                label = "CTA in Row",
                state = isCTAInRow,
                onToggle = { isCTAInRow = it },
            )

            ShowcaseCheckbox(
                label = "Show Title ",
                state = showTitle,
                onToggle = { showTitle = it },
            )

            ShowcaseCheckbox(
                label = "Show Sub-title",
                state = showSubTitle,
                onToggle = { showSubTitle = it },
            )

            ShowcaseCheckbox(
                label = "Show Primary CTA",
                state = showPrimaryCTA,
                onToggle = { showPrimaryCTA = it },
            )

            ShowcaseCheckbox(
                label = "Show Secondary CTA",
                state = showSecondaryCTA,
                onToggle = { showSecondaryCTA = it },
            )

            Spacer(modifier = Modifier.height(24.dp))

        }

    }
}

@Preview
@Composable
fun PreviewZEmptyStateShowcase() {
    MaterialTheme {
        Column(Modifier.background(color = ZTheme.color.background.default)) {
            ZEmptyStateShowcase()
        }
    }
}



