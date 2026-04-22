package com.zebpay.ui.v3.components.atoms.container

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.list.ZColumnListContainer


/**
 * A reusable container that switches between two content slots with a horizontal
 * slide animation.
 *
 * @param showFirstContent A boolean state that determines which content to display.
 * `true` shows [content01], `false` shows [content02].
 * @param modifier The modifier to be applied to the AnimatedContent container.
 * @param animationDurationMillis The duration of the slide animation in milliseconds.
 * @param content01 The composable content to display when [showFirstContent] is `true`.
 * @param content02 The composable content to display when [showFirstContent] is `false`.
 */
@Composable
fun ZSlideContainer(
    showFirstContent: Boolean,
    modifier: Modifier = Modifier,
    animationDurationMillis: Int = 400,
    content01: @Composable () -> Unit,
    content02: @Composable () -> Unit
){
    AnimatedContent(
        targetState = showFirstContent,
        modifier = modifier,
        transitionSpec = {
            // Determine the direction of the slide based on the state change.
            if (targetState) {
                // Transitioning from content02 to content01 (false -> true)
                // content01 slides in from the left.
                val enter = slideInHorizontally(
                    animationSpec = tween(animationDurationMillis),
                    initialOffsetX = { fullWidth -> -fullWidth }
                )
                // content02 slides out to the right.
                val exit = slideOutHorizontally(
                    animationSpec = tween(animationDurationMillis),
                    targetOffsetX = { fullWidth -> fullWidth }
                )
                enter togetherWith exit
            } else {
                // Transitioning from content01 to content02 (true -> false)
                // content02 slides in from the right.
                val enter = slideInHorizontally(
                    animationSpec = tween(animationDurationMillis),
                    initialOffsetX = { fullWidth -> fullWidth }
                )
                // content01 slides out to the left.
                val exit = slideOutHorizontally(
                    animationSpec = tween(animationDurationMillis),
                    targetOffsetX = { fullWidth -> -fullWidth }
                )
                enter togetherWith exit
            }
        },
        label = "SlideContainerAnimation"
    ) { isFirstContentVisible ->
        // Based on the target state, display the appropriate content.
        if (isFirstContentVisible) {
            content01()
        } else {
            content02()
        }
    }
}

@ThemePreviews
@Composable
private fun ZSlideContainerPreview() {
    ZBackgroundPreviewContainer {
        var showFirst by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
           // The SlideContainer component
            ZSlideContainer(
                showFirstContent = showFirst,
                modifier = Modifier
                    .fillMaxWidth(),
                content01 = {
                    ZColumnListContainer(
                        modifier = Modifier.size(300.dp),
                        background = ZTheme.color.graphics.glowGreen,
                        innerPaddingValues = PaddingValues(30.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Container Box 1",
                            textAlign = TextAlign.Center
                        )
                        BlankHeight(20.dp)
                        ZOutlineButton(
                            title = "Show Next",
                            onClick = {
                                showFirst = false
                            },
                            tagId = ""
                        )
                    }
                },
                content02 = {
                    ZColumnListContainer(
                        modifier = Modifier.size(300.dp),
                        background = ZTheme.color.graphics.glowRed,
                        innerPaddingValues = PaddingValues(30.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Container Box 2",
                            textAlign = TextAlign.Center
                        )
                        BlankHeight(20.dp)
                        ZOutlineButton(
                            title = "Back",
                            onClick = {
                                showFirst = true
                            },
                            tagId = ""
                        )
                    }
                }
            )
        }
    }
}