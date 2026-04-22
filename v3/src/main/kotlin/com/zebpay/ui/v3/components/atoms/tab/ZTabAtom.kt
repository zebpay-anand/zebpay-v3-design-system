package com.zebpay.ui.v3.components.atoms.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.designsystem.v3.utils.safeClickable
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.utils.background
import com.zebpay.ui.v3.components.utils.border


@Composable
fun ZPrimaryTab(
    text: String,
    tagId: String="",
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabPrimaryColors = ZTheme.color.tab.primary
    val bgColor: ZGradient = if (isChecked)
        tabPrimaryColors.fillActive
    else
        tabPrimaryColors.fillDefault.asZGradient()
    val textColor by animateColorAsState(
        targetValue = if (isChecked)
            tabPrimaryColors.textActive
        else
            tabPrimaryColors.textDefault,
        label = "text_color",
    )
    val shapes = ZTheme.shapes
    val style = if (isChecked) ZTheme.typography.bodyRegularB2.semiBold() else
        ZTheme.typography.bodyRegularB2
    Box(
        modifier = modifier
            .clip(ZTheme.shapes.full)
            .background(gradient = bgColor, shape = shapes.full)
            .safeClickable(tagId = tagId) {
                onCheckedChange(isChecked.not())
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = style,
            color = textColor,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}


@Composable
fun ZSecondaryTab(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabSecondaryColors = ZTheme.color.tab.secondary
    val borderWidth by animateDpAsState(
        targetValue = if (isChecked)
            0.dp
        else
            1.dp,
        label = "stroke",
    )

    val bgColor: ZGradient = if (isChecked)
        tabSecondaryColors.fillActive
    else
        tabSecondaryColors.fillDefault.asZGradient()

    val borderColor: ZGradient = if (isChecked)
        tabSecondaryColors.borderActive
    else
        tabSecondaryColors.borderDefault.asZGradient()

    val textColor by animateColorAsState(
        targetValue = if (isChecked)
            tabSecondaryColors.textActive
        else
            tabSecondaryColors.textDefault,
        label = "text_color",
    )
    val shapes = ZTheme.shapes
    val style = if (isChecked) ZTheme.typography.bodyRegularB3.semiBold() else
        ZTheme.typography.bodyRegularB3
    Box(
        modifier
            .clip(ZTheme.shapes.full)
            .background(gradient = bgColor, shape = shapes.full)
            .conditional(isChecked.not()) {
                border(borderWidth, borderColor, shapes.full)
            }
            .clickable {
                onCheckedChange(isChecked.not())
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = style,
            color = textColor,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}


@Composable
fun ZTertiaryTab(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabColors = ZTheme.color.tab.tertiary

    val textColor = if (isChecked)
        tabColors.textActive
    else
        tabColors.textDefault.asZGradient()

    val textStyle = if (isChecked)
        ZTheme.typography.bodyRegularB2.semiBold()
    else
        ZTheme.typography.bodyRegularB2

    Box(
        modifier = modifier
            .safeClickable(tagId = "tab_click_$text") {
                onCheckedChange(isChecked.not())
            }
            .width(IntrinsicSize.Max),
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center),
        ) {
            Text(
                text = text,
                style = textStyle.copy(brush = textColor.toTextBrush()),
                modifier = Modifier.padding(bottom = 6.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        AnimatedVisibility(
            isChecked,
            modifier = Modifier
                .align(Alignment.BottomCenter),
            enter = scaleIn(tween(500)),
            exit = scaleOut(tween(500)),
        ) {
            Spacer(
                modifier = Modifier
                    .size(20.dp, 2.dp)
                    .clip(RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp))
                    .background(
                        gradient = ZGradientColors.Primary01,
                        RoundedCornerShape(12.dp, 12.dp, 0.dp, 0.dp),
                    ),
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZPrimaryTab() {
    ZebpayTheme {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ZPrimaryTab(
                "Selected",
                isChecked = true,
                onCheckedChange = {},
                modifier = Modifier.width(180.dp),
            )
            ZPrimaryTab(
                "Default",
                isChecked = false,
                onCheckedChange = {},
                modifier = Modifier.width(180.dp),
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZSecondaryTab() {
    ZebpayTheme {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ZSecondaryTab(
                "Selected",
                isChecked = true,
                onCheckedChange = {},
                modifier = Modifier,
            )
            ZSecondaryTab(
                "Default",
                isChecked = false,
                onCheckedChange = {},
                modifier = Modifier,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewZTertiaryTab() {
    ZebpayTheme {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            ZTertiaryTab(
                "Selected",
                isChecked = true,
                onCheckedChange = {},
                modifier = Modifier,
            )
            ZTertiaryTab(
                "Default",
                isChecked = false,
                onCheckedChange = {},
                modifier = Modifier,
            )
            ZTertiaryTab(
                "Default",
                isChecked = false,
                onCheckedChange = {},
                modifier = Modifier,
            )
        }
    }
}

