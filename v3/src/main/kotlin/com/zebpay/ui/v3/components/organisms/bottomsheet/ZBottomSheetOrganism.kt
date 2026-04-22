package com.zebpay.ui.v3.components.organisms.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetContainer
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.templates.ZScaffold
import com.zebpay.ui.v3.components.templates.ZScaffoldState
import com.zebpay.ui.v3.components.templates.rememberZScaffoldState
import com.zebpay.ui.v3.components.utils.LocalBlurController
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.ZBottomController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZBottomSheet(
    isVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    allowDismiss: Boolean = true,
    skipPartiallyExpanded: Boolean = true,
    scope: CoroutineScope = rememberCoroutineScope(),
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val blurEffect = LocalBlurController.current
    LaunchedEffect(isVisible.value) {
        blurEffect.toggle(isVisible.value)
    }
    if (isVisible.value) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = skipPartiallyExpanded,
            confirmValueChange = {
                if(allowDismiss.not()) {
                    it != SheetValue.Hidden
                }else{
                    true
                }
            },
        )
        CompositionLocalProvider(
            LocalSheetCloseController provides ZBottomController {
                scope.launch {
                    isVisible.value = false
                    sheetState.hide()
                }
                onDismissRequest?.invoke()
            },
        ) {
            ModalBottomSheet(
                properties = ModalBottomSheetProperties(shouldDismissOnBackPress = allowDismiss),
                onDismissRequest = {
                    if(allowDismiss) {
                        scope.launch {
                            isVisible.value = false
                            sheetState.hide()
                        }
                        onDismissRequest?.invoke()
                    }
                },
                sheetState = sheetState,
                dragHandle = {},
                containerColor = Color.Transparent,
                tonalElevation = 4.dp,
                scrimColor = ZTheme.color.bottomSheet.scrim,
                modifier = modifier.wrapContentHeight(),
                content = content,
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZBottomSheet(@PreviewParameter(LoremIpsum::class) message: String) {
    val state: ZScaffoldState = rememberZScaffoldState()
    ZebpayTheme {
        ZScaffold(
            state = state,
        ) {
            val showSheet = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .padding(it)
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
            }
            ZBottomSheet(
                isVisible = showSheet,
            ) {
                ZBottomSheetContainer(
                    title = "Headline",
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    footer = {
                        ZTextButton(
                            title = "KYC",
                            onClick = {},
                            tagId = "",
                        )
                    },
                ) {
                    Text(
                        text = message,
                        style = ZTheme.typography.bodyRegularB2,
                        color = ZTheme.color.text.secondary,
                    )
                }
            }
        }
    }
}

