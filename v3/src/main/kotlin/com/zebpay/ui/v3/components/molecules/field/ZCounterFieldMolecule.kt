package com.zebpay.ui.v3.components.molecules.field

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonGradientLabel
import com.zebpay.ui.v3.components.atoms.label.ZCommonLabel
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZDecorationItemColor
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZLabelColor
import com.zebpay.ui.v3.components.utils.ZDecorationItemColor


@Composable
fun ZToggleCounterField(
    modifier: Modifier = Modifier,
    tagID:String="",
    decorationItemColor: ZDecorationItemColor = LocalZDecorationItemColor.current,
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 6.dp, vertical = 6.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(12.dp),
    onAdd:()->Unit,
    onMinus:()->Unit
){
    Row (modifier = modifier.padding(innerPaddingValues),
        horizontalArrangement = horizontalArrangement){
        Box(modifier = Modifier
            .size(38.dp,28.dp)
            .safeClickable(
                enabled = (decorationItemColor.isDisable || decorationItemColor.isError).not(),
                tagId = tagID, debounceMillis = 1){
                onMinus.invoke()
            }
            .background(
                color = decorationItemColor.background,
                shape = ZTheme.shapes.small
            )
            .border(
                width = 1.dp,
                color = if(decorationItemColor.isDisable || decorationItemColor.isError)
                    ZTheme.color.inputField.borderDisabled
                else ZTheme.color.inputField.borderDefault,
                shape = ZTheme.shapes.small
            )) {
            ZCommonGradientLabel(
                modifier = Modifier.fillMaxSize(),
                label = "-1%",
                maxWidth = true,
                labelColor = if(decorationItemColor.isDisable || decorationItemColor.isError)
                    decorationItemColor.textColor.asZGradient() else ZTheme.color.buttons.primary.fillActive,
                textStyle = ZTheme.typography.bodyRegularB4.semiBold()
            )
        }

        Box(modifier = Modifier
            .size(38.dp,28.dp)
            .safeClickable(enabled = (decorationItemColor.isDisable || decorationItemColor.isError).not(),
                debounceMillis = 1){
                onAdd.invoke()
            }
            .background(
                color = decorationItemColor.background,
                shape = ZTheme.shapes.extraSmall
            )
            .border(
                width = 1.dp,
                color = if(decorationItemColor.isDisable || decorationItemColor.isError)
                    ZTheme.color.inputField.borderDisabled
                else ZTheme.color.inputField.borderDefault,
                shape = ZTheme.shapes.extraSmall
            )) {
            ZCommonGradientLabel(
                modifier = Modifier.fillMaxSize(),
                label = "+1%",
                maxWidth = true,
                labelColor = if(decorationItemColor.isDisable || decorationItemColor.isError)
                    decorationItemColor.textColor.asZGradient() else ZTheme.color.buttons.primary.fillActive,
                textStyle = ZTheme.typography.bodyRegularB4.semiBold()
            )
        }
    }
}

@Composable
fun ZCounterField(
    currentValue: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    width: Dp = 90.dp,
    decorationItemColor: ZDecorationItemColor = LocalZDecorationItemColor.current,
    iconSize: Dp = LocalZIconSize.current,
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 6.dp, vertical = 6.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    onAdd:()->Unit,
    onMinus:()->Unit
) {
    Box(modifier = modifier) {
        ZCommonLabel(
            modifier = Modifier.padding(innerPaddingValues)
                .width(width),
            label = currentValue,
            textStyle = textStyle,
            maxWidth = true,
            labelColor = decorationItemColor.textColor,
            horizontalArrangement = horizontalArrangement,
            leading = {
                Box(modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = decorationItemColor.background,
                        shape = ZTheme.shapes.extraSmall
                    )
                    .border(
                        width = 1.dp,
                        color = if(decorationItemColor.isDisable)
                            ZTheme.color.inputField.borderDisabled
                        else ZTheme.color.inputField.borderDefault,
                        shape = ZTheme.shapes.extraSmall
                    )){
                    Box(modifier = Modifier.padding(4.dp)) {
                        ZGradientIconButton(
                            onClick = {
                                onMinus.invoke()
                            },
                            icon = ZIcons.ic_subtract.asZIcon,
                            size = iconSize,
                        )
                    }
                }
            },
            trailing = {
                Box(modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = decorationItemColor.background,
                        shape = ZTheme.shapes.extraSmall
                    ).border(
                        width = 1.dp,
                        color =if(decorationItemColor.isDisable)
                            ZTheme.color.inputField.borderDisabled
                        else ZTheme.color.inputField.borderDefault,
                        shape = ZTheme.shapes.extraSmall
                    )) {
                    Box(modifier = Modifier.padding(4.dp)) {
                        ZGradientIconButton(
                            onClick = {
                                onAdd.invoke()
                            },
                            icon = ZIcons.ic_add.asZIcon,
                        )
                    }
                }
            },
        )
    }
}


@Composable
fun ZCounterOutlineField(
    currentValue: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    color: Color = LocalZLabelColor.current,
    iconSize: Dp = LocalZIconSize.current,
    shapes: CornerBasedShape = ZTheme.shapes.medium,
    innerPaddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 12.dp),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp),
    onAdd:()->Unit,
    onMinus:()->Unit
) {
    Box(modifier = modifier
        .background(color = ZTheme.color.card.fill, shape = shapes)
        .border(width = 1.dp, color = ZTheme.color.tab.secondary.borderDefault, shape = shapes)
    ) {
        ZCommonLabel(
            modifier = Modifier.padding(innerPaddingValues)
                .width(90.dp),
            label = currentValue,
            textStyle = textStyle,
            labelColor = color,
            maxWidth = true,
            horizontalArrangement = horizontalArrangement,
            leading = {
                ZGradientIconButton(
                    onClick = {
                        onAdd.invoke()
                    },
                    icon = ZIcons.ic_subtract.asZIcon,
                    size = iconSize,
                )
            },
            trailing = {
                ZGradientIconButton(
                    onClick = {
                        onMinus.invoke()
                    },
                    icon = ZIcons.ic_add.asZIcon,
                    size = iconSize,
                )
            },
        )
    }
}


@ThemePreviews
@Composable
private fun ZCounterFieldPreview(){
    ZBackgroundPreviewContainer{
        val current by remember { mutableIntStateOf(-9) }
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB3.semiBold(),
            LocalZLabelColor provides ZTheme.color.text.primary,
            LocalZIconSize provides 18.dp,
            LocalZDecorationItemColor provides ZDecorationItemColor(
                textColor = ZTheme.color.text.primary,
                iconColor = ZTheme.color.icon.singleTonePrimary,
                background = ZTheme.color.inputField.backgroundDefault,
                border = ZTheme.color.inputField.borderDefault,
            ),
        ) {

            ZToggleCounterField(
                onAdd = {},
                onMinus = {}
            )

            ZCounterOutlineField(
                currentValue = current.toString()+"%",
                onAdd = {},
                onMinus = {}
            )
            
            ZCounterField(
                currentValue = current.toString()+"%",
                onAdd = {},
                onMinus = {},
                iconSize = 20.dp
            )
        }
    }
}
