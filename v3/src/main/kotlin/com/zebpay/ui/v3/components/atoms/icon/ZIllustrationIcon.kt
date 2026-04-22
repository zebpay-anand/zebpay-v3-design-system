package com.zebpay.ui.v3.components.atoms.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.v3.components.resource.ZIcons


enum class ZIllustrationType {
    BLUE, RED, YELLOW, GREEN, PURPLE, ORANGE, Gray
}

enum class ZIllustrationSize {
    Small, Regular, Large
}

internal val ZIllustrationType.background: Int
    get() = when (this) {
        ZIllustrationType.BLUE -> ZIcons.ic_illustration_blue
        ZIllustrationType.RED -> ZIcons.ic_illustration_red
        ZIllustrationType.YELLOW -> ZIcons.ic_illustration_yellow
        ZIllustrationType.GREEN -> ZIcons.ic_illustration_green
        ZIllustrationType.PURPLE -> ZIcons.ic_illustration_purple
        ZIllustrationType.ORANGE -> ZIcons.ic_illustration_orange
        ZIllustrationType.Gray -> ZIcons.ic_illustration_gray
    }

internal val ZIllustrationSize.size : Pair<Dp,Dp>
    get() = when (this) {
        ZIllustrationSize.Small -> Pair(48.dp,20.dp)
        ZIllustrationSize.Regular -> Pair(68.dp,24.dp)
        ZIllustrationSize.Large -> Pair(100.dp,38.dp)
    }

@Composable
fun ZIllustrationIcon(
    icon: ZIcon,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    illustrationSize:ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
) {
    val sizePair = illustrationSize.size
    Box(modifier = modifier.size(sizePair.first)) {
        Image(
            painter = painterResource(id = illustrationType.background),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
        ZIcon(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = 0.dp, x = 1.dp),
            icon = icon,
            size = sizePair.second,
            contentDescription = iconDescription,
            tint = ZTheme.color.icon.singleToneWhite,
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZIllustrationIcon() {
    ZBackgroundPreviewContainer {
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_tick.asZIcon,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_error.asZIcon,
            illustrationType = ZIllustrationType.RED,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_info.asZIcon,
            illustrationType = ZIllustrationType.YELLOW,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.GREEN,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.PURPLE,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.ORANGE,
            illustrationSize = ZIllustrationSize.Small
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.Gray,
            illustrationSize = ZIllustrationSize.Small
        )
    }
}

@ThemePreviews
@Composable
fun PreviewZIllustrationIconRegular() {
    ZBackgroundPreviewContainer {
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_tick.asZIcon,
            illustrationSize = ZIllustrationSize.Regular
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_error.asZIcon,
            illustrationType = ZIllustrationType.RED,
            illustrationSize = ZIllustrationSize.Regular
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_info.asZIcon,
            illustrationType = ZIllustrationType.YELLOW,
            illustrationSize = ZIllustrationSize.Regular
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.GREEN,
            illustrationSize = ZIllustrationSize.Regular
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.PURPLE,
            illustrationSize = ZIllustrationSize.Regular
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.ORANGE,
            illustrationSize = ZIllustrationSize.Regular
        )
    }
}


@ThemePreviews
@Composable
fun PreviewZIllustrationIconLarge() {
    ZBackgroundPreviewContainer {
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_tick.asZIcon,
            illustrationSize = ZIllustrationSize.Large
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_error.asZIcon,
            illustrationType = ZIllustrationType.RED,
            illustrationSize = ZIllustrationSize.Large
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_info.asZIcon,
            illustrationType = ZIllustrationType.YELLOW,
            illustrationSize = ZIllustrationSize.Large
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.GREEN,
            illustrationSize = ZIllustrationSize.Large
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.PURPLE,
            illustrationSize = ZIllustrationSize.Large
        )
        ZIllustrationIcon(
            icon = ZIcons.ic_glass_summary.asZIcon,
            illustrationType = ZIllustrationType.ORANGE,
            illustrationSize = ZIllustrationSize.Large
        )
    }
}