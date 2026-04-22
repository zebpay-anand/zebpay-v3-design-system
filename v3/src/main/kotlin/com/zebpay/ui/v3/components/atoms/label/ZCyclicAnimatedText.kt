package com.zebpay.ui.v3.components.atoms.label

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons

/**
 * A reusable Text composable that cycles through a list of strings with a
 * vertical slide animation on each tap.
 *
 * @param texts A list of strings to display and cycle through.
 * @param modifier The modifier to be applied to the component's container.
 * @param textStyle The style to be applied to the text.
 * @param animationDuration The duration of the slide in/out animation in milliseconds.
 */
@Composable
fun CyclicAnimatedText(
    texts: List<String>,
    modifier: Modifier = Modifier,
    selectedIndex:Int=0,
    textStyle: TextStyle = LocalTextStyle.current,
    animationDuration: Int = 400, // Duration for the animation
    onSelectIndex:(Int)->Unit = {},
) {
    // Return early if the list is empty to avoid errors.
    if (texts.isEmpty()) {
        return
    }
    val haptic = LocalHapticFeedback.current
    var currentIndex by remember { mutableIntStateOf(selectedIndex) }

    LaunchedEffect(selectedIndex) {
        currentIndex = selectedIndex
    }

    // The main container that handles clicks to cycle through the text.
    val containerModifier = modifier.safeClickable(tagId = "selected_index_${texts[currentIndex]}",
        interactionSource = remember { MutableInteractionSource() },
        indication = null // Disable ripple effect for a cleaner text-only interaction
    ) {
        haptic.performHapticFeedback(HapticFeedbackType.ContextClick)
        // Update index, looping back to the start if at the end of the list.
        currentIndex = (currentIndex + 1) % texts.size
        onSelectIndex.invoke(currentIndex)
    }

    Row(modifier = containerModifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp,Alignment.End),
        verticalAlignment = Alignment.CenterVertically) {
        AnimatedContent(
            targetState = currentIndex,
            modifier = Modifier,
            transitionSpec = {
                // Define the animation: new text slides in from top, old text slides out to bottom.
                val enterTransition = slideInVertically(
                    animationSpec = tween(durationMillis = animationDuration),
                    initialOffsetY = { fullHeight -> -fullHeight }, // Start from above
                ) + fadeIn(
                    animationSpec = tween(durationMillis = animationDuration),
                )

                val exitTransition = slideOutVertically(
                    animationSpec = tween(durationMillis = animationDuration),
                    targetOffsetY = { fullHeight -> fullHeight }, // Exit to below
                ) + fadeOut(
                    animationSpec = tween(durationMillis = animationDuration),
                )

                // Combine enter and exit transitions to run together.
                enterTransition.togetherWith(exitTransition)
            },
            // Center the text within the AnimatedContent container.
            contentAlignment = Alignment.Center,
            label = "CyclicAnimatedText",
        ) { targetIndex ->
            ZCommonGradientLabel(
                label = texts[targetIndex],
                textStyle = textStyle,
            )
        }
        ZGradientIcon(
            icon = ZIcons.ic_sorter_line.asZIcon,
            size = 14.dp
        )
    }
}


@ThemePreviews
@Composable
private fun CyclicAnimatedTextPreview() {
    ZBackgroundPreviewContainer {
        val headlineTexts = listOf(
            "QUICKTRADE",
            "FUTURES",
            "EXCHANGE"
        )
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.ctaC2
        ) {
            CyclicAnimatedText(
                texts = headlineTexts,
                animationDuration = 600 // Slower animation
            )
        }
    }
}
