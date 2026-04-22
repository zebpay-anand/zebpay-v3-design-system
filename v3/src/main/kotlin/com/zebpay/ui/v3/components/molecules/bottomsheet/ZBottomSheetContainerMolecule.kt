package com.zebpay.ui.v3.components.molecules.bottomsheet

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.designsystem.v3.utils.conditional
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationSize
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZGradientColor
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer

@SuppressLint("ConfigurationScreenWidthHeight", "UnusedBoxWithConstraintsScope")
@Composable
private fun ZBottomSheetBaseContainer(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    gradientHeader: Boolean = false,
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    showConfetti: Boolean= false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    illustrationIcon: ZIcon = ZIcons.ic_coins.asZIcon,
    illustrationSize: ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
    customIllustration: (@Composable ()-> Unit)? = null,
    supportLazyColumn: Boolean = false,
    footerVisible: Boolean = false,
    verticalScrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.(Modifier) -> Unit,
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxAllowedContentHeight = (screenHeight * if (footerVisible) .6f else .7f)
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        val contentHeight = maxHeight
        val scrollableModifier = if (contentHeight > maxAllowedContentHeight) {
            Modifier
                .heightIn(max = maxAllowedContentHeight)
                .conditional(supportLazyColumn.not()) {
                    verticalScroll(verticalScrollState)
                }
        } else {
            Modifier.wrapContentHeight()
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            if(customIllustration!=null){
                if(gradientHeader){
                    ZBottomSheetGradientHeader(
                        title = title,
                        subTitle = subTitle,
                        allowDismiss = allowDismiss,
                        showConfetti = showConfetti,
                        allowBack = allowBack,
                        tagID = "back",
                        onBackPress = onBackPress,
                        maxTitleLines = maxTitleLines,
                    ) {
                        customIllustration()
                    }
                }else {
                    ZBottomSheetHeader(
                        title = title,
                        subTitle = subTitle,
                        allowDismiss = allowDismiss,
                        maxTitleLines = maxTitleLines,
                        allowBack = allowBack,
                        onBackPress = onBackPress,
                        illustrationIcon = {
                            customIllustration()
                        }
                    )
                }
            }else {
                if(gradientHeader){
                    ZBottomSheetGradientHeader(
                        title = title,
                        subTitle = subTitle,
                        illustrationIcon = illustrationIcon,
                        allowDismiss = allowDismiss,
                        allowBack = allowBack,
                        showConfetti = showConfetti,
                        onBackPress = onBackPress,
                        maxTitleLines = maxTitleLines,
                        illustrationSize = illustrationSize,
                        illustrationType = illustrationType,
                    )
                }else {
                    ZBottomSheetHeader(
                        title = title,
                        subTitle = subTitle,
                        illustrationIcon = illustrationIcon,
                        allowDismiss = allowDismiss,
                        allowBack = allowBack,
                        onBackPress = onBackPress,
                        maxTitleLines = maxTitleLines,
                        illustrationSize = illustrationSize,
                        illustrationType = illustrationType,
                    )
                }
            }
            Column(
                modifier = modifier
                    .offset(y = (-1).dp)
                    .background(color = ZTheme.color.background.default),
            ) {
                content(scrollableModifier)
            }
        }
    }
}



@Composable
fun ZBottomSheetContainer(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    gradientHeader: Boolean = false,
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    showConfetti: Boolean= false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    showDivider: Boolean = true,
    verticalScrollState: ScrollState = rememberScrollState(),
    headerIcon: @Composable () -> Unit,
    footer: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
){
    ZBottomSheetBaseContainer(
        title = title,
        modifier = modifier,
        subTitle = subTitle,
        gradientHeader = gradientHeader,
        allowDismiss = allowDismiss,
        showConfetti = showConfetti,
        allowBack = allowBack,
        onBackPress = onBackPress,
        maxTitleLines = maxTitleLines,
        customIllustration = headerIcon,
        verticalScrollState = verticalScrollState,
        supportLazyColumn = false,
        footerVisible = footer != null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .then(it),
            verticalArrangement = verticalArrangement,
            content = content,
        )
        if (footer != null) {
            if (showDivider) {
                ZHorizontalDivider()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                footer()
            }
        }
    }
}


@Composable
fun ZBottomSheetContainer(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    gradientHeader: Boolean = false,
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    showConfetti: Boolean= false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    illustrationIcon: ZIcon = ZIcons.ic_glass_coins.asZIcon,
    illustrationSize: ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    showDivider: Boolean = true,
    verticalScrollState: ScrollState = rememberScrollState(),
    footer: (@Composable RowScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {

    ZBottomSheetBaseContainer(
        title = title,
        modifier = modifier,
        subTitle = subTitle,
        gradientHeader = gradientHeader,
        allowDismiss = allowDismiss,
        allowBack = allowBack,
        showConfetti = showConfetti,
        onBackPress = onBackPress,
        maxTitleLines = maxTitleLines,
        illustrationIcon = illustrationIcon,
        illustrationSize = illustrationSize,
        illustrationType = illustrationType,
        verticalScrollState = verticalScrollState,
        supportLazyColumn = false,
        footerVisible = footer != null,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding)
                .then(it),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
        )
        if (footer != null) {
            if (showDivider) {
                ZHorizontalDivider()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                footer()
            }
        }
    }
}


@Composable
fun ZBottomSheetListContainer(
    title: String,
    modifier: Modifier = Modifier,
    subTitle: String = "",
    gradientHeader: Boolean = false,
    allowDismiss: Boolean = true,
    allowBack: Boolean = false,
    showConfetti: Boolean= false,
    onBackPress:(()->Unit)?=null,
    maxTitleLines: Int = 1,
    illustrationIcon: ZIcon = ZIcons.ic_coins.asZIcon,
    illustrationSize: ZIllustrationSize = ZIllustrationSize.Regular,
    illustrationType: ZIllustrationType = ZIllustrationType.BLUE,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(16.dp),
    showDivider: Boolean = true,
    state: LazyListState = rememberLazyListState(),
    footer: (@Composable RowScope.() -> Unit)? = null,
    content: LazyListScope.() -> Unit,
) {

    ZBottomSheetBaseContainer(
        title = title,
        modifier = modifier,
        subTitle = subTitle,
        gradientHeader = gradientHeader,
        allowDismiss = allowDismiss,
        allowBack = allowBack,
        showConfetti = showConfetti,
        onBackPress = onBackPress,
        maxTitleLines = maxTitleLines,
        illustrationIcon = illustrationIcon,
        illustrationSize = illustrationSize,
        illustrationType = illustrationType,
        supportLazyColumn = true,
        footerVisible = footer != null,
    ) {
        LazyColumn(
            modifier = modifier.then(it),
            state =  state,
            verticalArrangement = verticalArrangement,
            contentPadding = contentPadding,
        ) {
            content()
        }
        if (footer != null) {
            if (showDivider) {
                ZHorizontalDivider()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                footer()
            }
        }
    }
}


@ThemePreviews
@Composable
fun PreviewContainer(@PreviewParameter(LoremIpsum::class) message: String) {
    ZSheetPreviewContainer {
        ZBottomSheetContainer(
            title = "Heading",
            illustrationIcon = ZIcons.ic_coins.asZIcon,
            footer = {
                ZOutlineButton(
                    modifier = Modifier.weight(1f),
                    title = "Secondary",
                    onClick = {},
                    tagId = "",
                )

                ZPrimaryButton(
                    modifier = Modifier.weight(1f),
                    title = "Primary",
                    onClick = {},
                    tagId = "",
                )
            },
        ) {
            Text(
                text = message,
                style = ZTheme.typography.bodyRegularB2,
                color = ZTheme.color.text.secondary,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewGradientContainer(@PreviewParameter(LoremIpsum::class) message: String) {
    ZSheetPreviewContainer {
        CompositionLocalProvider(
            LocalZGradientColor provides ZTheme.color.bottomSheet.green
        ) {
            ZBottomSheetContainer(
                title = "Heading",
                subTitle = "Sub Heading",
                gradientHeader = true,
                allowDismiss = true,
                showConfetti = true,
                illustrationIcon = ZIcons.ic_coins.asZIcon,
                footer = {
                    ZOutlineButton(
                        modifier = Modifier.weight(1f),
                        title = "Secondary",
                        onClick = {},
                        tagId = "",
                    )

                    ZPrimaryButton(
                        modifier = Modifier.weight(1f),
                        title = "Primary",
                        onClick = {},
                        tagId = "",
                    )
                },
            ) {
                Text(
                    text = message,
                    style = ZTheme.typography.bodyRegularB2,
                    color = ZTheme.color.text.secondary,
                )
            }
        }
    }
}