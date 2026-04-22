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
fun AccountRestrictionSheet(action: (BlockerAction) -> Unit) {
    val sheetController = LocalSheetCloseController.current
    ZBottomSheetContainer(
        title = stringResource(R.string.account_restricted),
        illustrationIcon = ZIcons.ic_glass_error.asZIcon,
        illustrationType = ZIllustrationType.RED,
        showDivider = false,
        footer = {
            ZPrimaryButton(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.get_assistance),
                onClick = {
                    sheetController.close()
                    action.invoke(BlockerAction.GetAssistance)
                },
                tagId = "primary_get_assistance",
            )

        },
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB2,
            LocalContentColor provides ZTheme.color.text.secondary,
        ) {
            Text(
                text = stringResource(R.string.account_restricted_desc),
                textAlign = TextAlign.Center,
                color = ZTheme.color.text.secondary,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewAccountRestrictionSheet() {
    ZSheetPreviewContainer {
        AccountRestrictionSheet {}
    }
}