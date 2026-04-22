package com.zebpay.ui.v3.components.molecules.card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZImage
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.label.ZCommonGradientLabel
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.atoms.misc.BlankWidth
import com.zebpay.ui.v3.components.molecules.button.ZTextButton
import com.zebpay.ui.v3.components.molecules.button.ZTextButtonColor
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.border

@Composable
fun ZBlockerCard(
    modifier: Modifier = Modifier,
    headline: String,
    subText: String,
    icon: ZIcon,
    primaryText: String,
    secondaryText: String?=null,
    blockerScreen: Boolean = false,
    innerPadding:PaddingValues= PaddingValues(16.dp,12.dp),
    onSecondaryAction:(()->Unit)?=null,
    buttonAction:()->Unit
){
    if(blockerScreen){
        ZCompleteBlockerCard(
            modifier = modifier,
            headline = headline,
            subText = subText,
            icon = icon,
            primaryText = primaryText,
            secondaryText = secondaryText,
            onSecondaryAction = onSecondaryAction,
            innerPadding = innerPadding,
            buttonAction = buttonAction,
        )
    }else{
        ZToastBlockerCard(
            modifier = modifier,
            headline = headline,
            subText = subText,
            icon = icon,
            primaryText = primaryText,
            innerPadding = innerPadding,
            buttonAction = buttonAction)
    }
}

@Composable
fun ZCompleteBlockerCard(
    modifier: Modifier = Modifier,
    headline: String,
    subText: String,
    icon: ZIcon,
    primaryText: String,
    secondaryText: String?=null,
    innerPadding:PaddingValues= PaddingValues(16.dp,12.dp),
    onSecondaryAction:(()->Unit)?=null,
    buttonAction:()->Unit
){
    Box(modifier = modifier.background(
         gradient = ZGradientColors.Blue01,
    )){
        Row(modifier = Modifier.padding(innerPadding)){
            Column(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center){
                ZLabel(
                    label = headline.uppercase(),
                    textStyle = ZTheme.typography.headlineRegularH3.bold(),
                    labelColor = ZTheme.color.text.white
                )
                ZLabel(
                    label = subText,
                    textStyle = ZTheme.typography.bodyRegularB4,
                    labelColor = ZTheme.color.text.white
                )
                BlankHeight(12.dp)

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    AnimatedVisibility (secondaryText.isNullOrEmpty().not()){
                        Box(modifier = Modifier.border(
                            gradient = ZTheme.color.icon.singleToneWhite.asZGradient(),
                            shape = ZTheme.shapes.medium)
                            .safeClickable(tagId =  "blocker_btn_action_$secondaryText"){
                                onSecondaryAction?.invoke()
                            })
                        {
                            ZCommonGradientLabel(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                label = secondaryText?.uppercase()?:"",
                                textStyle = ZTheme.typography.bodyRegularB4.semiBold(),
                                labelColor = ZTheme.color.buttons.primary.textActive.asZGradient()
                            )
                        }
                    }
                    Box(modifier = Modifier.background(
                        color = ZTheme.color.buttons.secondary.fillActive,
                        shape = ZTheme.shapes.medium)
                        .safeClickable(tagId =  "blocker_btn_action_$primaryText"){
                            buttonAction.invoke()
                        })
                    {
                        ZCommonGradientLabel(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            label = primaryText.uppercase(),
                            textStyle = ZTheme.typography.bodyRegularB4.semiBold(),
                            labelColor = ZTheme.color.buttons.secondary.textActive
                        )
                    }
                }
            }
            BlankWidth(12.dp)
            ZImage(
                modifier = Modifier,
                icon = icon,
                size = 80.dp,
                contentDescription = "illustration_icon"
            )
        }
    }
}



@Composable
fun ZToastBlockerCard(
    modifier: Modifier = Modifier,
    headline: String,
    subText: String,
    icon: ZIcon,
    primaryText: String,
    tagID: String = "",
    innerPadding:PaddingValues= PaddingValues(16.dp,12.dp),
    buttonAction:()->Unit
){
    Box(modifier = modifier.background(
        gradient = ZGradientColors.Blue01,
        shape = ZTheme.shapes.large
        )){
        Row(modifier = Modifier.padding(innerPadding)){
            Column(modifier = Modifier.fillMaxWidth().weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center){
                ZLabel(
                    label = headline.uppercase(),
                    textStyle = ZTheme.typography.headlineRegularH4.bold(),
                    labelColor = ZTheme.color.text.white
                )
                ZLabel(
                    label = subText,
                    textStyle = ZTheme.typography.bodyRegularB4,
                    labelColor = ZTheme.color.text.white
                )
                BlankHeight(12.dp)

                ZTextButton(
                    modifier = Modifier.offset(x=(-4).dp),
                    title = primaryText,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    textButtonColor = ZTextButtonColor.White,
                    onClick = {
                        buttonAction.invoke()
                    },
                    trailing = {
                        ZIcon(
                            icon = ZIcons.ic_arrow_right.asZIcon,
                            tint = ZTheme.color.icon.singleToneWhite
                        )
                    },
                    tagId = tagID.ifEmpty {"blocker_btn_action"},
                )
            }
            BlankWidth(12.dp)
            ZImage(
                modifier = Modifier,
                icon = icon,
                size = 76.dp,
                contentDescription = "illustration_icon"
            )
        }
    }
}

@ThemePreviews
@Composable
private fun ZCompleteBlockerCardPreview(){
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(10.dp)) {
        ZCompleteBlockerCard(
            icon = ZIcons.ic_blocker_kyc_verify.asZIcon,
            headline = "KYC: COMPLETE VERIFICATION",
            subText = "Verify to trade without any restrictions",
            primaryText = "START KYC VERIFICATION"
        ){

        }

        ZCompleteBlockerCard(
            icon = ZIcons.ic_blocker_kyc_verify.asZIcon,
            headline = "KYC: Under Review",
            subText = "We're verifying your details",
            primaryText = "VERIFY BANK",
            secondaryText = "VIEW DETAILS"
        ){

        }
    }
}

@ThemePreviews
@Composable
private fun ZToastBlockerCardPreview(){
    ZBackgroundPreviewContainer(innerPaddingValues = PaddingValues(8.dp)) {
        ZToastBlockerCard(
            icon = ZIcons.ic_blocker_kyc_verify.asZIcon,
            headline = "KYC: COMPLETE VERIFICATION",
            subText = "Verify to trade without any restrictions",
            primaryText = "START KYC VERIFICATION"
        ){

        }
    }
}