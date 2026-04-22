package com.zebpay.ui.v3.components.molecules.tooltip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZAmountLabel
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.molecules.field.ZInputField
import com.zebpay.ui.v3.components.molecules.list.ZColumnListContainer
import com.zebpay.ui.v3.components.resource.ZIcons


/**
 * tooltipContent - Content to display in tooltip.
 */
@Composable
fun ZCustomTooltipPopup(
    modifier: Modifier = Modifier,
    showTooltip: MutableState<Boolean>,
    enable: Boolean= false,
    tooltipPopup: @Composable (TooltipPopupPosition) -> Unit,
    requesterView: @Composable (Modifier) -> Unit,
) {
    var position by remember { mutableStateOf(TooltipPopupPosition(
        alignment = ZTooltipAlignment.TopCenter
    )) }

    val view = LocalView.current.rootView
    if (showTooltip.value && enable) {
        tooltipPopup(position)
    }
    requesterView(modifier
        .onGloballyPositioned { coordinates ->
                position = calculateTooltipPopupPosition(view, coordinates)
        }
    )
}

@Composable
fun ZTooltipPopup(
    position: TooltipPopupPosition,
    offsetPosition: Offset = Offset(0.0f,0.0f),
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var alignment = Alignment.TopCenter
    var offset = position.offset

    with(LocalDensity.current) {
        when (position.alignment) {
            ZTooltipAlignment.TopCenter -> {
                alignment = Alignment.TopCenter
                offset = offset.copy(
                    y = (position.offset.y + offsetPosition.y).toInt()
                )
            }
            ZTooltipAlignment.BottomCenter -> {
                alignment = Alignment.BottomCenter
                offset = offset.copy(
                    y = position.offset.y
                )
            }
        }
    }
    val popupPositionProvider = remember(alignment, offset) {
        TooltipAlignmentOffsetPositionProvider(
            alignment = alignment,
            offset = offset,
            centerPositionX = position.centerPositionX + offsetPosition.x,
            requestViewWidth = position.width
        )
    }
    Popup(
        popupPositionProvider = popupPositionProvider,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(),
        content = content
    )
}


@ThemePreviews
@Composable
private fun CustomToolTipPreview(){
    var price by remember { mutableStateOf("3,000") }
    val showToolTip = remember { mutableStateOf(false) }
    ZBackgroundPreviewContainer(modifier = Modifier.height(200.dp)) {
        ZInputField(
            value = price,
            onValueChange = { price = it },
            textStyle = ZTheme.typography.bodyRegularB3,
            label = { Text("Quantity/Amount") },
            placeholder = { Text("Enter Quantity") },
            textBottom = {
                if(false) {
                    Row {
                        ZLabel(
                            label = "Amount"
                        )
                        ZAmountLabel(
                            amount = "0.00"
                        )
                    }
                }else{
                    Text("Please enter quantity to proceed")
                }
            },
            trailing = {
                ZCustomTooltipPopup(
                    modifier = Modifier,
                    enable = true,
                    showTooltip = showToolTip,
                    tooltipPopup = { toolTipPosition ->
                        ZTooltipPopup(
                            position = toolTipPosition,
                            offsetPosition = Offset(45f,50f),
                            onDismissRequest = {
                                showToolTip.value = false
                            }
                        ){
                            ZColumnListContainer(innerPaddingValues = PaddingValues(16.dp)) {
                                 ZCommonLabel(
                                     label = "Your Custom View here"
                                 )
                            }
                        }
                    },
                ) {
                    Box(
                        modifier = it.safeClickable{
                            showToolTip.value = true
                        }
                    ) {
                        ZCommonLabel(
                            modifier = Modifier.padding(start = 8.dp),
                            label = "BTC",
                            trailing = {
                                ZIcon(
                                    icon = ZIcons.ic_arrow_down.asZIcon,
                                )
                            }
                        )
                    }
                }
            },
            shape = ZTheme.shapes.medium
        )
    }
}