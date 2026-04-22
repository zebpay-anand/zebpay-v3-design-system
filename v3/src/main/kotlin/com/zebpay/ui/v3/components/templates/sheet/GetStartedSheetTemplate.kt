package com.zebpay.ui.v3.components.templates.sheet

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.R
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetContainer
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer


@Composable
fun GetStartedSheet(action: (BlockerAction) -> Unit) {
    val sheetController = LocalSheetCloseController.current
    ZBottomSheetContainer(
        title = stringResource(R.string.explore_crypto_with_zebpay),
        illustrationIcon = ZIcons.ic_glass_logo.asZIcon,
        illustrationType = ZIllustrationType.BLUE,
        showDivider = false,
        footer = {
            ZPrimaryButton(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.get_started),
                onClick = {
                    sheetController.close()
                    action.invoke(BlockerAction.Login)
                },
                tagId = "primary_get_started",
            )

        },
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB2,
            LocalContentColor provides ZTheme.color.text.secondary,
        ) {
            Text(
                text = stringResource(R.string.c_begin_your_crypto_journey),
                textAlign = TextAlign.Center,
                color = ZTheme.color.text.secondary,
            )
        }
    }
}

@ThemePreviews
@Composable
fun PreviewGetStartedSheet() {
    ZSheetPreviewContainer {
        GetStartedSheet {}
    }
}