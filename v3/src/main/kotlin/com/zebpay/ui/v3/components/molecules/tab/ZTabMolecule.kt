package com.zebpay.ui.v3.components.molecules.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.v3.components.atoms.tab.ZPrimaryTab
import com.zebpay.ui.v3.components.atoms.tab.ZSecondaryTab
import com.zebpay.ui.v3.components.atoms.tab.ZTertiaryTab
import com.zebpay.ui.v3.components.utils.setTestingTag


enum class ZTabBarStyle {
    PRIMARY, SECONDARY, TERTIARY
}


@Stable
data class ZTabState(
    val selectedIndex: MutableState<Int> = mutableIntStateOf(-1),
    val style: ZTabBarStyle = ZTabBarStyle.PRIMARY,
) {

    fun isSelect(index: Int): Boolean {
        return selectedIndex.value == index
    }

    fun select(index: Int) {
        selectedIndex.value = index
    }
}


@Composable
fun rememberZTabState(
    initialSelectedIndex: Int = -1,
    style: ZTabBarStyle = ZTabBarStyle.PRIMARY,
): ZTabState {
    return remember {
        ZTabState(
            selectedIndex = mutableIntStateOf(initialSelectedIndex),
            style = style,
        )
    }
}


@Composable
fun ZTabBar(
    tabItems: List<String>,
    tabState: ZTabState,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
) {
    val tabColors = ZTheme.color.tab
    val shape = ZTheme.shapes.full
    Box(modifier = modifier) {
        val arrangement = when (tabState.style) {
            ZTabBarStyle.PRIMARY -> Arrangement.SpaceEvenly
            ZTabBarStyle.SECONDARY -> Arrangement.spacedBy(8.dp)
            ZTabBarStyle.TERTIARY -> Arrangement.spacedBy(16.dp)
        }
        val paddingValues = when (tabState.style) {
            ZTabBarStyle.TERTIARY -> PaddingValues(horizontal = 16.dp)
            ZTabBarStyle.PRIMARY -> PaddingValues(4.dp)
            ZTabBarStyle.SECONDARY -> PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        }
        if (tabState.style == ZTabBarStyle.TERTIARY) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = ZTheme.color.separator.solidDefault,
            )
        }
        Row(
            modifier = Modifier
                .conditional(tabState.style == ZTabBarStyle.PRIMARY) {
                    fillMaxWidth()
                        .clip(shape = shape)
                        .background(color = tabColors.primary.fillDefault)
                        .border(1.dp, tabColors.primary.borderDefault, shape = shape)
                }
                .horizontalScroll(rememberScrollState())
                .padding(paddingValues),
            horizontalArrangement = arrangement,
        )
        {
            tabItems.forEachIndexed { index, label ->
                val isChecked = tabState.isSelect(index)
                when (tabState.style) {
                    ZTabBarStyle.PRIMARY -> ZPrimaryTab(
                        modifier = Modifier.weight(1f),
                        text = label,
                        tagId = "tab_tag_id_$index",
                        isChecked = isChecked,
                        onCheckedChange = {
                            tabState.select(index = index)
                            onTabSelected.invoke(index)
                        },
                    )

                    ZTabBarStyle.SECONDARY -> ZSecondaryTab(
                        modifier = Modifier,
                        text = label,
                        isChecked = isChecked,
                        onCheckedChange = {
                            tabState.select(index = index)
                            onTabSelected.invoke(index)
                        },
                    )

                    ZTabBarStyle.TERTIARY -> ZTertiaryTab(
                        modifier = Modifier,
                        text = label,
                        isChecked = isChecked,
                        onCheckedChange = {
                            tabState.select(index = index)
                            onTabSelected.invoke(index)
                        },
                    )
                }
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZBarVariant() {
    ZBackgroundPreviewContainer {
        val tabItems = arrayListOf("Selected", "Default", "Default")
        val tabTertiaryItems = arrayListOf(
            "Selected",
            "Default",
            "Default",
            "Default",
            "Default",
            "Default",
            "Default",
            "Default",
        )
        val zTabPrimaryState =
            rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.PRIMARY)
        val zTabSecondaryState =
            rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.SECONDARY)
        val zTabTertiaryState =
            rememberZTabState(initialSelectedIndex = 0, style = ZTabBarStyle.TERTIARY)

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            ZTabBar(
                tabItems = tabItems,
                tabState = zTabPrimaryState,
                onTabSelected = {
                },
            )
            ZTabBar(
                tabItems = tabTertiaryItems,
                tabState = zTabSecondaryState,
                onTabSelected = {
                },
            )
            ZTabBar(
                tabItems = tabTertiaryItems,
                tabState = zTabTertiaryState,
                onTabSelected = {
                },
            )
        }
    }
}