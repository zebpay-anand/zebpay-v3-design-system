package com.zebpay.ui.v3.components.molecules.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton

@Composable
fun RowScope.ZBottomSheetFooterMolecule(
    primaryTxtLabel:String = "",
    showPrimaryBtn : Boolean =true,
    primaryClick:()->Unit,
    primaryTagId:String,
    secondaryTxtLabel:String = "",
    showSecondaryBtn : Boolean = true,
    secondaryClick:()->Unit,
    secondaryTagId:String,
){
    var showVertical by remember { mutableStateOf(false) }

    if(showVertical){
        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            if(showSecondaryBtn) {
                ZOutlineButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = secondaryTxtLabel,
                    onClick = secondaryClick,
                    tagId = secondaryTagId,
                    hasOverFlowCall = {
                        if(it){
                            showVertical = true
                        }
                    }
                )
            }
            if(showPrimaryBtn) {
                ZPrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    title = primaryTxtLabel,
                    onClick = primaryClick,
                    tagId = primaryTagId,
                    hasOverFlowCall = {
                        if(it){
                            showVertical = true
                        }
                    }
                )
            }
        }
    }else{
        if(showSecondaryBtn) {
            ZOutlineButton(
                modifier = Modifier.weight(1f),
                title = secondaryTxtLabel,
                onClick = secondaryClick,
                tagId = secondaryTagId,
                hasOverFlowCall = {
                    if(it){
                        showVertical = true
                    }
                }
            )
        }
        if(showPrimaryBtn) {
            ZPrimaryButton(
                modifier = Modifier.weight(1f),
                title = primaryTxtLabel,
                onClick = primaryClick,
                tagId = primaryTagId,
                hasOverFlowCall = {
                    if(it){
                        showVertical = true
                    }
                }
            )
        }
    }
}


@ThemePreviews
@Composable
private fun ZBottomSheetFooterMoleculePreview(){
    ZBackgroundPreviewContainer {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically) {
            ZBottomSheetFooterMolecule(
                secondaryTxtLabel = "Buy More",
                secondaryClick = {

                },
                secondaryTagId = "cta_click_secondary",
                primaryTxtLabel = "Go To Order",
                primaryClick = {

                },
                primaryTagId = "cta_primary_click"
            )
        }
    }
}