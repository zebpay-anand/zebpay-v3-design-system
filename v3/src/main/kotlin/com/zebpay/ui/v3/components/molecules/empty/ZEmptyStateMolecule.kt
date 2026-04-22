package com.zebpay.ui.v3.components.molecules.empty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.ZBackgroundPreviewContainer
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.semiBold
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationSize
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.misc.BlankHeight
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.resource.ZIcons


enum class ZEmptyState {
    BIG, SMALL
}

internal val ZEmptyState.illustrationSize: ZIllustrationSize
    get() = when (this) {
        ZEmptyState.BIG -> ZIllustrationSize.Large
        ZEmptyState.SMALL -> ZIllustrationSize.Large
    }

@Composable
fun EmptyStateView(
    icon: ZIcon,
    modifier: Modifier = Modifier,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
    emptyStateType: ZEmptyState = ZEmptyState.BIG,
    title: String?,
    subTitle: String?,
    primaryCTA: String = "",
    primaryTagId: String = "",
    secondaryCTA: String = "",
    secondaryTagId: String = "",
    primaryClick: (() -> Unit)? = null,
    secondaryClick: (() -> Unit)? = null,
    maxSubtitleLines: Int = 5,
    maxTitleLines: Int = 2,
    contentSpacing: Dp = 20.dp,
    isCTAInRow: Boolean = true,
) {
    EmptyStateView(
        emptyStateType = emptyStateType,
        title = title,
        subTitle = subTitle,
        primaryCTA = primaryCTA,
        primaryTagId = primaryTagId,
        secondaryCTA = secondaryCTA,
        secondaryTagId = secondaryTagId,
        primaryClick = primaryClick,
        secondaryClick = secondaryClick,
        maxSubtitleLines = maxSubtitleLines,
        maxTitleLines = maxTitleLines,
        isCTAInRow = isCTAInRow,
        contentSpacing = contentSpacing,
        modifier = modifier,
    ) {
        ZIllustrationIcon(
            icon = icon,
            illustrationType = illustrationType,
            illustrationSize = emptyStateType.illustrationSize,
            modifier = Modifier,
        )
    }
}

@Composable
fun EmptyStateView(
    modifier: Modifier = Modifier,
    emptyStateType: ZEmptyState = ZEmptyState.BIG,
    title: String?,
    subTitle: String?,
    primaryCTA: String = "",
    primaryTagId: String = "",
    secondaryCTA: String = "",
    secondaryTagId: String = "",
    primaryClick: (() -> Unit)? = null,
    secondaryClick: (() -> Unit)? = null,
    maxSubtitleLines: Int = 5,
    maxTitleLines: Int = 2,
    isCTAInRow: Boolean = true,
    contentSpacing: Dp = 20.dp,
    imagePlaceHolder: @Composable () -> Unit,
) {

    val titleTextStyle = if (emptyStateType == ZEmptyState.BIG)
        ZTheme.typography.displayRegularD3.bold()
    else ZTheme.typography.bodyRegularB1.semiBold()

    val subTitleTextStyle = if (emptyStateType == ZEmptyState.BIG)
        ZTheme.typography.bodyRegularB1
    else ZTheme.typography.bodyRegularB2

    val paddingValues = if (isCTAInRow) {
        PaddingValues(horizontal = 4.dp, vertical = 4.dp)
    } else {
        PaddingValues(horizontal = 32.dp, vertical = 4.dp)
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(paddingValues),
    ) {
        imagePlaceHolder()
        BlankHeight(height = contentSpacing)
        if (title.isNullOrBlank().not())
            Text(
                text = title.orEmpty(),
                color = ZTheme.color.text.primary,
                style = titleTextStyle,
                maxLines = maxTitleLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        if (subTitle.isNullOrBlank().not()) {
            Text(
                text = subTitle.orEmpty(),
                color = ZTheme.color.text.secondary,
                style = subTitleTextStyle,
                maxLines = maxSubtitleLines,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
            )
        }
        BlankHeight(height = contentSpacing)
        if (isCTAInRow) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (primaryCTA.isBlank().not() && primaryClick != null) {
                    ZPrimaryButton(
                        title = primaryCTA,
                        onClick = primaryClick,
                        tagId = primaryTagId,
                        modifier = Modifier.weight(1f),
                        paddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    )
                }
                if (secondaryCTA.isBlank().not() && secondaryClick != null) {
                    ZOutlineButton(
                        title = secondaryCTA,
                        onClick = secondaryClick,
                        tagId = secondaryTagId,
                        modifier = Modifier.weight(1f),
                        paddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    )

                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                if (primaryCTA.isBlank().not() && primaryClick != null) {
                    ZPrimaryButton(
                        title = primaryCTA,
                        onClick = primaryClick,
                        tagId = primaryTagId,
                        paddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    )
                }
                if (secondaryCTA.isBlank().not() && secondaryClick != null) {
                    ZOutlineButton(
                        title = secondaryCTA,
                        onClick = secondaryClick,
                        tagId = secondaryTagId,
                        paddingValues = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewEmptyState() {
    ZBackgroundPreviewContainer {
        CompositionLocalProvider(
            LocalContentColor provides ZTheme.color.icon.singleToneWhite,
        ) {
            EmptyStateView(
                title = "Heading",
                subTitle = "Subtext",
                primaryCTA = "CTA",
                secondaryCTA = "CTA",
                primaryClick = {},
                secondaryClick = {},
                isCTAInRow = false,
                icon = ZIcons.ic_glass_tick.asZIcon,
            )
            BlankHeight(40.dp)
            EmptyStateView(
                title = "Heading",
                subTitle = "Subtext",
                primaryCTA = "CTA",
                secondaryCTA = "CTA",
                primaryClick = {},
                secondaryClick = {},
                emptyStateType = ZEmptyState.SMALL,
                icon = ZIcons.ic_glass_tick.asZIcon,
            )
        }
    }
}