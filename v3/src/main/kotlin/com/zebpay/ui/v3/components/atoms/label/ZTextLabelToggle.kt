package com.zebpay.ui.v3.components.atoms.label
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth


private fun String.isSelected(current: String): Boolean{
    return this.equals(current, ignoreCase = true)
}

@Composable
fun ZTextLabelToggle(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String,
    selectedText: String,
    style: TextStyle = ZTheme.typography.bodyRegularB4,
    selectedColor: Color = ZTheme.color.text.primary,
    defaultColor: Color = ZTheme.color.text.grey,
    onSelected:(String)->Unit,
    trailIcon: @Composable (() -> Unit)? = null,
){

    val selectedStyle = style.semiBold()
    CompositionLocalProvider(
        LocalTextStyle provides style
    ) {
        Row(
            modifier = modifier.safeClickable{
                if(selectedText.equals(text1,ignoreCase = true)){
                    onSelected(text2)
                }else{
                    onSelected(text1)
                }
            },
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ZCommonLabel(
                label = text1,
                textStyle = if(text1.isSelected(current = selectedText)) selectedStyle else style,
                labelColor = if(text1.isSelected(current = selectedText)) selectedColor else defaultColor ,
            )
            ZLabel(
                label = "/",
                labelColor = ZTheme.color.text.grey
            )
            ZCommonLabel(
                label = text2,
                textStyle = if(text2.isSelected(current = selectedText)) selectedStyle else style,
                labelColor = if(text2.isSelected(current = selectedText)) selectedColor else defaultColor ,
            )
            if(trailIcon!=null){
                BlankWidth(4.dp)
            }
            AnimatedVisibility(trailIcon!=null){
                trailIcon?.invoke()
            }
        }
    }
}

@ThemePreviews
@Composable
private fun ZTextLabelTogglePreview(){
    ZBackgroundPreviewContainer {

        var selectedText by remember { mutableStateOf("Qty") }

        ZTextLabelToggle(
            text1 = "Qty",
            text2 = "Amt",
            selectedText = selectedText,
            onSelected = {
                selectedText = it
            }
        )
    }
}
