package com.zebpay.ui.v3.components.molecules.refresh


import androidx.annotation.FloatRange
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.v3.R
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * A pull to refresh container with a configurable indicator.
 *
 * @param onRefresh Callback to trigger a refresh.
 * @param modifier Modifier to be applied to the container.
 * @param loadingDuration Duration for the loading indicator to appear.
 * @param containScrollInside Whether to contain scroll inside the container.
 * @param state The pull to refresh state.
 * @param content The content to be displayed inside the container.
 */

@Composable
fun ZPullToRefreshContainer(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    loadingDuration: Duration = 2.seconds,
    containScrollInside: Boolean = false,
    state: ZPullToRefreshState = rememberZPullToRefreshState(),
    content: @Composable BoxScope.() -> Unit,
) {
    LaunchedEffect(key1 = state.isRefreshing) {
        if (state.isRefreshing) {
            onRefresh()
            state.startRefresh()
            delay(loadingDuration)
            state.endRefresh()
        }
    }

    Box(
        modifier = modifier.conditional(containScrollInside) {
            nestedScroll(state.nestedScrollConnection)
        },
    ) {
        this.content()
        ZPullToRefresh(state = state, modifier = Modifier.align(Alignment.TopCenter))
    }
}

/**
 * A pull to refresh container with a configurable indicator.
 *
 * @param state The pull to refresh state.
 * @param modifier Modifier to be applied to the container.
 * @param containScrollInside Whether to contain scroll inside the container.
 * @param content The content to be displayed inside the container.
 */

@Composable
fun ZPullToRefreshContainer(
    state: ZPullToRefreshState,
    modifier: Modifier = Modifier,
    containScrollInside: Boolean = false,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier.conditional(containScrollInside) {
            nestedScroll(state.nestedScrollConnection)
        },
    ) {
        this.content()
        ZPullToRefresh(state = state, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@ThemePreviews
@Composable
private fun PreviewZPullToRefresh() {
    val state = rememberZPullToRefreshState()

    LaunchedEffect(key1 = state.isRefreshing) {
        if (state.isRefreshing) {
            state.startRefresh()
            delay(3.seconds)
            state.endRefresh()
        }
    }

    ZBackgroundPreviewContainer {
        ZPullToRefreshContainer(
            state = state,
            modifier = Modifier.fillMaxSize(),
            containScrollInside = true,
        ) {
            Text(text = "Try Pull To Refresh", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
private fun ZPullToRefresh(
    state: ZPullToRefreshState,
    modifier: Modifier = Modifier,
    shape: Shape = ZTheme.shapes.medium,
) {

    val showElevation = remember {
        derivedStateOf { state.verticalOffset > 1f || state.isRefreshing }
    }
    Box(
        modifier = modifier
            .offset(y = -(2).dp)
            .graphicsLayer {
                translationY = state.verticalOffset - size.height
            }
            .shadow(
                // Avoid shadow when indicator is hidden
                elevation = if (showElevation.value) 5.dp else 0.dp,
                shape = shape,
                clip = true,
            )
            .background(
                color = ZTheme.color.background.default,
                shape = shape,
            )
            .border(1.dp, ZGradientColors.Primary01.toTextBrush(), shape),
    ) {
        ZIndicator(state)
    }

}

@Composable
private fun ZIndicator(state: ZPullToRefreshState) {

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Crossfade(
            targetState = state.isRefreshing,
            animationSpec = tween(durationMillis = 100),
            label = "indicator",
        ) {
            if (it) {
                CircularProgressIndicator(
                    color = ZTheme.color.text.primary,
                    strokeWidth = 2.dp,
                    trackColor = ZTheme.color.text.secondary,
                    modifier = Modifier.size(18.dp),
                )
            } else {
                CircularProgressIndicator(
                    {
                        state.progress
                    },
                    color = ZTheme.color.text.primary,
                    strokeWidth = 2.dp,
                    trackColor = ZTheme.color.text.secondary,
                    modifier = Modifier.size(18.dp),
                )
            }
        }
        Text(
            text = stringResource(
                id = if (state.isRefreshing)
                    R.string.refreshing else
                    R.string.pull_down_to_refresh,
            ),
            color = ZTheme.color.text.primary,
            style = ZTheme.typography.bodyRegularB3,
        )
    }

}

@ThemePreviews
@Composable
private fun PreviewZIndicatorIdle() {
    ZebpayTheme {
        ZIndicator(state = rememberZPullToRefreshState())
    }
}

@ThemePreviews
@Composable
private fun PreviewZIndicatorRefreshing() {
    ZebpayTheme {
        ZIndicator(
            state = rememberZPullToRefreshState().apply {
                this.startRefresh()
            },
        )
    }
}

@Stable
@ExperimentalMaterial3Api
interface ZPullToRefreshState {
    /** The threshold (in pixels), above which if a release occurs, a refresh will be called */
    val positionalThreshold: Float

    /**
     * PullRefresh progress towards [positionalThreshold]. 0.0 indicates no progress, 1.0 indicates
     * complete progress, > 1.0 indicates overshoot beyond the provided threshold
     */
    @get:FloatRange(from = 0.0)
    val progress: Float

    /**
     * Indicates whether a refresh is occurring.
     */
    val isRefreshing: Boolean

    /**
     * Sets [isRefreshing] to true.
     */
    fun startRefresh()

    /**
     * Sets [isRefreshing] to false.
     */
    fun endRefresh()

    /**
     * The vertical offset (in pixels) for the [PullToRefreshContainer] to consume
     */
    @get:FloatRange(from = 0.0)
    val verticalOffset: Float

    /**
     * A [NestedScrollConnection] that should be attached to a [Modifier.nestedScroll] in order to
     * keep track of the scroll events.
     */
    var nestedScrollConnection: NestedScrollConnection
}

@ExperimentalMaterial3Api
internal class ZPullToRefreshStateImpl(
    initialRefreshing: Boolean,
    override val positionalThreshold: Float,
    enabled: () -> Boolean,
) : ZPullToRefreshState {

    override val progress get() = adjustedDistancePulled / positionalThreshold
    override val verticalOffset get() = _verticalOffset

    override val isRefreshing get() = _refreshing

    override fun startRefresh() {
        _refreshing = true
        _verticalOffset = positionalThreshold
    }

    override fun endRefresh() {
        _verticalOffset = 0f
        _refreshing = false
    }

    override var nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(
            available: Offset,
            source: NestedScrollSource,
        ): Offset = when {
            !enabled() -> Offset.Zero
            // Swiping up
            source == NestedScrollSource.Drag && available.y < 0 -> {
                consumeAvailableOffset(available)
            }

            else -> Offset.Zero
        }

        override fun onPostScroll(
            consumed: Offset,
            available: Offset,
            source: NestedScrollSource,
        ): Offset = when {
            !enabled() -> Offset.Zero
            // Swiping down
            source == NestedScrollSource.Drag && available.y > 0 -> {
                consumeAvailableOffset(available)
            }

            else -> Offset.Zero
        }

        override suspend fun onPreFling(available: Velocity): Velocity {
            return Velocity(0f, onRelease(available.y))
        }
    }

    /** Helper method for nested scroll connection */
    fun consumeAvailableOffset(available: Offset): Offset {
        val y = if (isRefreshing) 0f else {
            val newOffset = (distancePulled + available.y).coerceAtLeast(0f)
            val dragConsumed = newOffset - distancePulled
            distancePulled = newOffset
            _verticalOffset = calculateVerticalOffset()
            dragConsumed
        }
        return Offset(0f, y)
    }

    /** Helper method for nested scroll connection. Calls onRefresh callback when triggered */
    suspend fun onRelease(velocity: Float): Float {
        if (isRefreshing) return 0f // Already refreshing, do nothing
        // Trigger refresh
        if (adjustedDistancePulled > positionalThreshold) {
            startRefresh()
        } else {
            animateTo(0f)
        }

        val consumed = when {
            // We are flinging without having dragged the pull refresh (for example a fling inside
            // a list) - don't consume
            distancePulled == 0f -> 0f
            // If the velocity is negative, the fling is upwards, and we don't want to prevent the
            // the list from scrolling
            velocity < 0f -> 0f
            // We are showing the indicator, and the fling is downwards - consume everything
            else -> velocity
        }
        distancePulled = 0f
        return consumed
    }

    suspend fun animateTo(offset: Float) {
        animate(initialValue = _verticalOffset, targetValue = offset) { value, _ ->
            _verticalOffset = value
        }
    }

    /** Provides custom vertical offset behavior for [PullToRefreshContainer] */
    fun calculateVerticalOffset(): Float = when {
        // If drag hasn't gone past the threshold, the position is the adjustedDistancePulled.
        adjustedDistancePulled <= positionalThreshold -> adjustedDistancePulled
        else -> {
            // How far beyond the threshold pull has gone, as a percentage of the threshold.
            val overshootPercent = abs(progress) - 1.0f
            // Limit the overshoot to 200%. Linear between 0 and 200.
            val linearTension = overshootPercent.coerceIn(0f, 2f)
            // Non-linear tension. Increases with linearTension, but at a decreasing rate.
            val tensionPercent = linearTension - linearTension.pow(2) / 4
            // The additional offset beyond the threshold.
            val extraOffset = positionalThreshold * tensionPercent
            positionalThreshold + extraOffset
        }
    }

    companion object {
        /** The default [Saver] for [PullToRefreshStateImpl]. */
        fun Saver(
            positionalThreshold: Float,
            enabled: () -> Boolean,
        ) = Saver<ZPullToRefreshState, Boolean>(
            save = { it.isRefreshing },
            restore = { isRefreshing ->
                ZPullToRefreshStateImpl(isRefreshing, positionalThreshold, enabled)
            },
        )
    }

    internal var distancePulled by mutableFloatStateOf(0f)
    private val adjustedDistancePulled: Float get() = distancePulled * 0.5f
    private var _verticalOffset by mutableFloatStateOf(0f)
    private var _refreshing by mutableStateOf(initialRefreshing)
}


/**
 * Create and remember the default [PullToRefreshState].
 *
 * @param positionalThreshold The positional threshold when a refresh would be triggered
 * @param enabled a callback used to determine whether scroll events are to be handled by this
 * [PullToRefreshState]
 */
@Composable
@ExperimentalMaterial3Api
fun rememberZPullToRefreshState(
    positionalThreshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    enabled: () -> Boolean = { true },
): ZPullToRefreshState {
    val density = LocalDensity.current
    val positionalThresholdPx = with(density) { positionalThreshold.toPx() }
    return rememberSaveable(
        positionalThresholdPx, enabled,
        saver = ZPullToRefreshStateImpl.Saver(
            positionalThreshold = positionalThresholdPx,
            enabled = enabled,
        ),
    ) {
        ZPullToRefreshStateImpl(
            initialRefreshing = false,
            positionalThreshold = positionalThresholdPx,
            enabled = enabled,
        )
    }
}