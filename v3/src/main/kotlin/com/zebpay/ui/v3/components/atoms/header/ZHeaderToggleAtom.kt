package com.zebpay.ui.v3.components.atoms.header

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZColors
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZHeaderGradientController
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.background
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun ZToolbarToggleAction(
    options: List<@Composable () -> Unit>,
    selectedIndex: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    onGradient: Boolean = LocalZHeaderGradientController.current.onGradient,
    height: Dp = 32.dp,
    contentWidth: Dp = 32.dp,
) {
    val toggleColor = if (onGradient) {
        ZToggleActionColor(
            border = ZTheme.color.navigation.top.onGradientIconBorder,
            background = ZTheme.color.navigation.top.onGradientIconBg,
            selectedIconColor = ZColors.Primary01,
            defaultIconColor = ZTheme.color.icon.singleToneWhite,
            activeColor = ZColors.White01.asZGradient(),
        )
    } else {
        ZToggleActionColor(
            border = ZTheme.color.navigation.top.onSolidIconBorder,
            background = ZTheme.color.background.default,
            selectedIconColor = ZTheme.color.icon.singleToneWhite,
            defaultIconColor = ZTheme.color.navigation.top.onSolidIcon,
            activeColor = ZGradientColors.Primary01,
        )
    }

    val scope = rememberCoroutineScope()
    val tabCount = options.size
    val density = LocalDensity.current
    val containerWidth = (contentWidth.value * options.size).dp
    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .width(containerWidth)
            .clip(ZTheme.shapes.full)
            .border(1.dp, color = toggleColor.border, ZTheme.shapes.full)
            .background(color = toggleColor.background)
            .pointerInput(tabCount) {
                detectTapGestures { offset ->
                    val widthPerTab = size.width / tabCount
                    val index = (offset.x / widthPerTab).toInt()
                    onSelect(index)
                }
            },
    ) {
        val boxWidth = constraints.maxWidth.toFloat()
        val perTab = boxWidth / tabCount

        val animOffset = remember { Animatable(selectedIndex * perTab) }
        LaunchedEffect(selectedIndex) {
            scope.launch {
                animOffset.animateTo(
                    targetValue = selectedIndex * perTab,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                )
            }
        }
        // Animated slider "blob"
        Box(
            modifier = Modifier
                .offset { IntOffset(animOffset.value.roundToInt(), 0) }
                .width(with(density) { perTab.toDp() })
                .fillMaxHeight()
                .padding(4.dp)
                .clip(ZTheme.shapes.full)
                .background(gradient = toggleColor.activeColor, ZTheme.shapes.full),
        )

        Row(modifier = Modifier.fillMaxSize()) {
            options.forEachIndexed { index, content ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        ) {
                            onSelect(index)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides
                                if (selectedIndex == index)
                                    toggleColor.selectedIconColor else toggleColor.defaultIconColor,
                        LocalZIconSize provides if (selectedIndex == index) 14.dp else 16.dp,
                    ) {
                        content()
                    }
                }
            }
        }
    }
}


@Immutable
data class ZToggleActionColor(
    val border: Color,
    val background: Color,
    val selectedIconColor: Color,
    val defaultIconColor: Color,
    val activeColor: ZGradient,
)


@ThemePreviews
@Composable
fun PreviewZToggleButton() {
    ZebpayTheme {
        ZBackgroundPreviewContainer {
            val selectedTab = remember { mutableIntStateOf(0) }
            ZToolbarToggleAction(
                options = listOf(
                    {
                        ZIcon(
                            icon = ZIcons.ic_currency_inr.asZIcon, contentDescription = "INR",
                            tint = LocalContentColor.current,
                        )
                    },
                    {
                        ZIcon(
                            icon = ZIcons.ic_currency_tether.asZIcon, contentDescription = "USDT",
                            tint = LocalContentColor.current,
                        )
                    },
                ),
                selectedIndex = selectedTab.intValue,
                onSelect = { selectedTab.intValue = it },
                modifier = Modifier,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZToggleButtonOnGradient() {
    ZebpayTheme {
        Column(modifier = Modifier.background(gradient = ZGradientColors.Primary01)) {
            val selectedTab = remember { mutableIntStateOf(0) }
            ZToolbarToggleAction(
                options = listOf(
                    {
                        ZIcon(
                            icon = ZIcons.ic_currency_inr.asZIcon, contentDescription = "INR",
                            tint = LocalContentColor.current,
                        )
                    },
                    {
                        ZIcon(
                            icon = ZIcons.ic_currency_tether.asZIcon, contentDescription = "USDT",
                            tint = LocalContentColor.current,
                        )
                    },
                ),
                selectedIndex = selectedTab.intValue,
                onSelect = { selectedTab.intValue = it },
                onGradient = true,
                modifier = Modifier,
            )
        }
    }
}