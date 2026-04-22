package com.zebpay.ui.v3.components.molecules.button

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Button
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZGradientIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.loader.ZCircularLoader
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.background
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


/**
 * Defines the possible states for the SwipeToConfirmButton.
 */
enum class SwipeState {
    /** The default, interactive state. */
    Active,

    /** The state during a background operation after a successful swipe. */
    InProgress,

    /** The state where the button is non-interactive. */
    Disabled
}

/**
 * Data class to hold the colors for different parts and states of the button.
 */
@Stable
data class SwipeButtonColors(
    val activeContainerColor: ZGradient,
    val activeTextColor: Color,
    val activeThumbColor: ZGradient,
    val activeThumbIconColor: ZGradient,
    val disabledContainerColor: ZGradient,
    val disabledTextColor: Color,
    val disabledThumbColor: ZGradient,
    val disabledThumbIconColor: ZGradient,
    val progressColor: Color,
    val progressTrackColor:Color
)

/**
 * Default color values for the SwipeToConfirmButton.
 */
object SwipeButtonDefaults {
    @Composable
    fun colors(
        activeContainerColor: ZGradient = ZTheme.color.buttons.secondary.borderGreen,
        activeTextColor: Color = ZTheme.color.buttons.primary.textActive,
        activeThumbColor: ZGradient = ZTheme.color.buttons.secondary.fillActive.asZGradient(),
        activeThumbIconColor: ZGradient = ZTheme.color.buttons.secondary.borderGreen,
        disabledContainerColor: ZGradient = ZTheme.color.buttons.primary.fillDisable.asZGradient(),
        disabledTextColor: Color = ZTheme.color.buttons.primary.textDisable,
        disabledThumbColor: ZGradient = ZTheme.color.buttons.secondary.fillActive.asZGradient(),
        disabledThumbIconColor: ZGradient = ZTheme.color.icon.singleToneDisable.asZGradient(),
        progressColor: Color = ZTheme.color.icon.singleToneWhite,
        progressTrackColor: Color =  ZTheme.color.navigation.top.onGradientIconBg,
    ): SwipeButtonColors = SwipeButtonColors(
        activeContainerColor = activeContainerColor,
        activeTextColor = activeTextColor,
        activeThumbColor = activeThumbColor,
        activeThumbIconColor = activeThumbIconColor,
        disabledContainerColor = disabledContainerColor,
        disabledTextColor = disabledTextColor,
        disabledThumbColor = disabledThumbColor,
        disabledThumbIconColor = disabledThumbIconColor,
        progressColor = progressColor,
        progressTrackColor = progressTrackColor
    )
}


@Stable
class SwipeToConfirmState(
    internal val scope: CoroutineScope,
    internal val completionThreshold: Float
) {
    // Single source of truth for the thumb's horizontal position.
    val offsetX = Animatable(0f)

    // Derived state for swipe progress (0.0 to 1.0).
    // This is efficient as it only recalculates when offsetX.value changes.
    val swipeFraction: Float by derivedStateOf {
        if (swipeableWidth > 0) (offsetX.value / swipeableWidth).coerceIn(0f, 1f) else 0f
    }

    // The maximum draggable width. Will be updated by the layout.
    internal var swipeableWidth by mutableFloatStateOf(0f)

    fun onDrag(delta: Float) {
        scope.launch {
            val newOffset = (offsetX.value + delta).coerceIn(0f, swipeableWidth)
            offsetX.snapTo(newOffset)
        }
    }

    fun onDragStopped(onSlideComplete: () -> Unit) {
        scope.launch {
            if (swipeFraction >= completionThreshold) {
                // Animate to the end with a "heavier" feel (lower stiffness).
                offsetX.animateTo(
                    targetValue = swipeableWidth,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessLow // Lower stiffness = more "weight"
                    )
                )
                onSlideComplete()
            } else {
                // Animate back to the start with a snappier spring.
                offsetX.animateTo(
                    targetValue = 0f,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            }
        }
    }

    fun reset() {
        scope.launch {
            offsetX.snapTo(
                targetValue = 0f
            )
        }
    }
}


// A remember function to create and manage the state holder.
@Composable
fun rememberSwipeToConfirmState(completionThreshold: Float = 0.7f): SwipeToConfirmState {
    val scope = rememberCoroutineScope()
    return remember {
        SwipeToConfirmState(scope = scope, completionThreshold = completionThreshold)
    }
}



@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ZSwipeButton(
    text: String,
    state: SwipeState,
    modifier: Modifier = Modifier,
    shape: CornerBasedShape = ZTheme.shapes.small,
    swipeToConfirmState: SwipeToConfirmState = rememberSwipeToConfirmState(),
    thumbIcon: ZIcon = ZIcons.ic_arrow_right.asZIcon,
    colors: SwipeButtonColors = SwipeButtonDefaults.colors(),
    onSlideComplete: () -> Unit,
){

    // Determine colors based on the current state.
    val (containerGradient, thumbGradient, textColor) = when (state) {
        SwipeState.Disabled -> Triple(colors.disabledContainerColor, colors.disabledThumbColor, colors.disabledTextColor)
        else -> Triple(colors.activeContainerColor, colors.activeThumbColor, colors.activeTextColor)
    }

    val hapticFeedback = LocalHapticFeedback.current

    // Reset the slider when the state changes away from InProgress.
    LaunchedEffect(state) {
        if (state != SwipeState.InProgress) {
            swipeToConfirmState.reset()
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .height(48.dp)
            .background(gradient = containerGradient),
        contentAlignment = Alignment.CenterStart
    ) {

        val thumbSize = 40.dp
        val thumbSizePx = with(LocalDensity.current) { thumbSize.toPx() }
        swipeToConfirmState.swipeableWidth = constraints.maxWidth.toFloat() - thumbSizePx - 30


        // --- Hint Text ---
        if(state != SwipeState.InProgress) {
            Hint(
                text = text,
                swipeFraction = swipeToConfirmState.swipeFraction,
                textColor = textColor,
                modifier = Modifier
                    .fillMaxWidth() // Ensure text doesn't go under the initial thumb position
            )
        }

        // --- InProgress Loader ---
        // AnimatedContent provides a nice crossfade between the loader and the thumb.
        AnimatedContent(
            targetState = state,
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
            transitionSpec = { fadeIn(animationSpec = tween(50)) togetherWith fadeOut(animationSpec = tween(50)) },
            label = "SwipeButtonStateAnimation"
        ) { targetState ->
            if (targetState == SwipeState.InProgress) {
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fill the space to catch drags anywhere
                        .padding(start = 4.dp, end = 8.dp) // Padding for thumb start/end pos
                ) {
                    ZCircularLoader(
                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.Center),
                        color = colors.progressColor,
                        trackColor = colors.progressTrackColor,
                    )
                }
            } else {
                // --- Draggable Thumb ---
                // We use a Box here to allow the draggable modifier to be placed correctly.
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fill the space to catch drags anywhere
                        .padding(start = 4.dp, end = 8.dp) // Padding for thumb start/end pos
                ) {
                    CompositionLocalProvider(
                        LocalZGradientColor provides containerGradient
                    ) {
                        Thumb(
                            thumbGradient = thumbGradient,
                            thumbIcon = thumbIcon,
                            thumbSize = thumbSize,
                            swipeFraction = swipeToConfirmState.swipeFraction, // Pass fraction for fade out
                            modifier = Modifier
                                .align(Alignment.CenterStart) // Align to the start of the padded box
                                .offset {
                                    IntOffset(
                                        x = swipeToConfirmState.offsetX.value.roundToInt(), y = 0
                                    )
                                }
                                .draggable(
                                    enabled = state == SwipeState.Active,
                                    orientation = Orientation.Horizontal,
                                    state = rememberDraggableState { delta ->
                                        swipeToConfirmState.onDrag(delta)
                                    },
                                    onDragStopped = {
                                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                                        swipeToConfirmState.onDragStopped(onSlideComplete)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun Thumb(
    thumbGradient: ZGradient,
    thumbIcon: ZIcon,
    thumbSize: Dp,
    swipeFraction: Float,
    modifier: Modifier = Modifier,
) {

    val startFadeFraction = 0.85f
    val fraction = 1f - ((swipeFraction - startFadeFraction) / (1f - startFadeFraction)).coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .size(thumbSize)
            .graphicsLayer {
                // Apply the fade out and a subtle scale down effect.
                alpha = fraction
            }
            .clip(ZTheme.shapes.small)
            .background(thumbGradient, shape = ZTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        ZGradientIcon(
            icon = thumbIcon,
            contentDescription = "Swipe handle",
        )
    }
}


@Composable
private fun Hint(
    text: String,
    swipeFraction: Float,
    textColor: Color = LocalContentColor.current,
    modifier: Modifier = Modifier,
) {
    // Simplified alpha calculation for the hint text fade out.
    val hintAlpha = (1f - swipeFraction).coerceIn(0f, 1f)

    Text(
        text = text,
        style = ZTheme.typography.ctaC1,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier.alpha(hintAlpha),
        maxLines = 1,
    )
}


@ThemePreviews
@Composable
fun ZSwipeButtonPreview(){
    ZBackgroundPreviewContainer {
        var state by remember { mutableStateOf(SwipeState.Active) }
        ZSwipeButton(
            text = "SWIPE TO CONFIRM",
            state = state,
        ){
        }
        var state1 by remember { mutableStateOf(SwipeState.Disabled) }
        ZSwipeButton(
            text = "SWIPE TO CONFIRM",
            state = state1,
        ){

        }

        var state2 by remember { mutableStateOf(SwipeState.InProgress) }
        ZSwipeButton(
            text = "SWIPE TO CONFIRM",
            state = state2,
        ){

        }
    }
}