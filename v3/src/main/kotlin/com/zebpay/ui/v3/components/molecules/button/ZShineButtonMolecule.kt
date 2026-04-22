package com.zebpay.ui.v3.components.molecules.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.loader.ZCircularLoader
import com.zebpay.ui.v3.components.atoms.shimmer.ShineHolder
import com.zebpay.ui.v3.components.utils.LocalZPrimaryButtonColor
import com.zebpay.ui.v3.components.utils.background
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ZShineButton(
    title: String,
    animate: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: ZButtonSize = ZButtonSize.Big,
    buttonColor: ZPrimaryColor = LocalZPrimaryButtonColor.current,
    enabled: Boolean = true,
    leadingIcon: ZIcon? = null,
    trailingIcon: ZIcon? = null,
    maxWidth: Boolean = false,
    isLoading: Boolean = false,
    delay: Int = 1000,
    duration: Int = 2000,
    maxLines: Int = Int.MAX_VALUE,
    paddingValues: PaddingValues = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
    hasOverFlowCall: (Boolean)->Unit = {}
) {


    val buttonColors = if (enabled) getPrimaryColors(buttonColor) else disableColor()

    val mainComponentAlpha by animateFloatAsState(
        if (isLoading)
            0f else 1f,
        label = "",
    )

    Box(
        modifier = modifier
            .clip(
                ZTheme.shapes.small,
            )
            .clickable(enabled && isLoading.not()) {
                onClick()
            },
    ) {
        ShineHolder(
            animate = if (enabled && isLoading.not()) {
                animate
            } else {
                false
            },
            delay = delay,
            duration = duration,
            modifier = Modifier
                .fillMaxWidth()
                .background(buttonColors.backgroundColor)
                .padding(paddingValues)
                .height(IntrinsicSize.Max),
        ) {
            Box {
                Row(
                    modifier = Modifier.alpha(mainComponentAlpha),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    leadingIcon?.let {
                        ZIcon(
                            icon = leadingIcon,
                            contentDescription = null,
                            tint = buttonColors.iconColor,
                            modifier = Modifier.size(buttonSize.iconSize),

                            )
                    }
                    Text(
                        text = title.uppercase(),
                        style = buttonSize.getTextStyle(),
                        color = buttonColors.textColor,
                        modifier = Modifier.conditional(maxWidth) {
                            weight(1f)
                        },
                        textAlign = TextAlign.Center,
                        maxLines = maxLines,
                        onTextLayout = { textLayoutResult ->
                            val hasOverflow = textLayoutResult.hasVisualOverflow
                            hasOverFlowCall.invoke(hasOverflow) // Notify external callback
                        }
                    )
                    trailingIcon?.let {
                        ZIcon(
                            icon = trailingIcon,
                            contentDescription = null,
                            tint = buttonColors.iconColor,
                            modifier = Modifier.size(buttonSize.iconSize),
                        )
                    }
                }
                AnimatedVisibility(
                    isLoading, modifier = Modifier.align(Alignment.Center),
                    enter = slideInVertically {
                        it / 2
                    },
                    exit = slideOutVertically(),
                ) {
                    ZCircularLoader(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(buttonSize.iconSize),
                        trackColor = buttonColors.trackColor,
                    )
                }
            }
        }
    }

}


@Preview
@Composable
private fun PreviewZShineButton() {
    var isLoading by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(2.seconds)
            isLoading = false
        }
    }
    ZBackgroundPreviewContainer {
        Column {
            ZShineButton(
                title = "Shinning Button",
                animate = true,
                onClick = {},
                isLoading = false,
                maxWidth = false,
            )
            ZShineButton(
                title = "Check Loading",
                animate = true,
                onClick = {
                    isLoading = true
                },
                isLoading = isLoading,
                maxWidth = false,
            )
        }
    }
}

@Composable
private fun disableColor() = ZPrimaryButtonColors(
    backgroundColor = ZTheme.color.buttons.primary.fillDisable.asZGradient(),
    iconColor = ZTheme.color.icon.singleToneWhite,
    trackColor = ZTheme.color.icon.singleToneWhite,
    foregroundColor = ZTheme.color.navigation.top.onGradientIconBg,
    textColor = ZTheme.color.buttons.primary.textDisable,
)