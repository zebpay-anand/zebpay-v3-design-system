package com.zebpay.catalog.bottomsheet

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.ZCatalogScaffold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetContainer
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetListContainer
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.molecules.list.ZListItemRow
import com.zebpay.ui.v3.components.molecules.list.ZListLabelItem
import com.zebpay.ui.v3.components.organisms.bottomsheet.ZBottomSheet
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.templates.ZScaffoldState
import com.zebpay.ui.v3.components.templates.rememberZScaffoldState
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews


@Composable
fun ZBottomSheetScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
    scaffoldState: ZScaffoldState = rememberZScaffoldState(),
) {
    ZCatalogScaffold(
        modifier = modifier,
        state = scaffoldState,
        title = stringResource(title),
        subTitle = stringResource(subTitle),
        onBackPressed = onNavBack,
    ) { innerPadding ->
        val showSheet = remember { mutableStateOf(false) }
        val listSheet = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                16.dp,
                alignment = Alignment.CenterVertically,
            ),
        ) {
            ZPrimaryButton(
                title = "Show BottomSheet",
                onClick = {
                    showSheet.value = true
                },
                tagId = "",
            )

            ZOutlineButton(
                title = "Show List Sheet",
                onClick = {
                    listSheet.value = true
                },
                tagId = "",
            )
        }
        ShowBottomSheet(showSheet)
        ShowBottomListSheet(listSheet)
    }
}


@Composable
fun ShowBottomListSheet(listSheet: MutableState<Boolean>) {
    ZBottomSheet(
        isVisible = listSheet,
        modifier = Modifier,
        allowDismiss = true,
    ) {
        CustomSheetListShow(listSheet)
    }
}


@Composable
fun ShowBottomSheet(showSheet: MutableState<Boolean>) {
    ZBottomSheet(
        isVisible = showSheet,
        modifier = Modifier,
        allowDismiss = true,
    ) {
        CustomSheetShow(showSheet)
    }
}


@Composable
fun CustomSheetListShow(showSheet: MutableState<Boolean>) {
    ZBottomSheetListContainer(
        title = "Headline",
        illustrationType = ZIllustrationType.YELLOW,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        footer = {
            ZOutlineButton(
                modifier = Modifier.weight(1f),
                title = "Secondary",
                onClick = {},
                tagId = "",
            )

            ZPrimaryButton(
                modifier = Modifier.weight(1f),
                title = "Primary",
                onClick = {},
                tagId = "",
            )
        },
    ) {
        repeat(50) {
            item {
                ZListItemRow(
                    modifier = Modifier.fillMaxWidth(),
                    isBoldLabel = true,
                    leftContent = {
                        ZListLabelItem(
                            label = "Left",
                            leading = {
                                ZIcon(
                                    icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                    tint = Color.Unspecified,
                                )
                            },
                            trailing = {
                                ZIconButton(
                                    icon = ZIcons.ic_info.asZIcon,
                                    onClick = {},
                                )
                            },
                        )
                    },
                    rightContent = {
                        ZIcon(
                            icon = ZIcons.ic_arrow_right.asZIcon,
                        )
                    },
                )
            }
        }
    }
}


@Composable
fun CustomSheetShow(showSheet: MutableState<Boolean>) {
    ZBottomSheetContainer(
        title = "Headline",
        illustrationType = ZIllustrationType.YELLOW,
        verticalArrangement = Arrangement.spacedBy(0.dp),
        footer = {
            ZOutlineButton(
                modifier = Modifier.weight(1f),
                title = "Secondary",
                onClick = {},
                tagId = "",
            )

            ZPrimaryButton(
                modifier = Modifier.weight(1f),
                title = "Primary",
                onClick = {},
                tagId = "",
            )
        },
    ) {
        repeat(5) {
            ZListItemRow(
                modifier = Modifier.fillMaxWidth(),
                isBoldLabel = true,
                leftContent = {
                    ZListLabelItem(
                        label = "Left",
                        leading = {
                            ZIcon(
                                icon = ZIcons.ic_dual_tone_cryptopacks.asZIcon,
                                tint = Color.Unspecified,
                            )
                        },
                        trailing = {
                            ZIconButton(
                                icon = ZIcons.ic_info.asZIcon,
                                onClick = {},
                            )
                        },
                    )
                },
                rightContent = {
                    ZIcon(
                        icon = ZIcons.ic_arrow_right.asZIcon,
                    )
                },
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewBottomSheet() {
    ZebpayTheme {
        val showSheet = remember { mutableStateOf(false) }
        CustomSheetShow(showSheet)
    }
}
