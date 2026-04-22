package com.zebpay.ui.v3.components.templates.sheet

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.R
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetContainer
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalBlockerController
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer


sealed interface BankStatus {

    data object NotStarted : BankStatus

    data object Verified : BankStatus

    data object UnderReview : BankStatus

    data class Rejected(val rejectReasons: List<String>?, val isAttemptsExhausted: Boolean) :
        BankStatus
}


internal val BankStatus.illustration: Pair<ZIcon, ZIllustrationType>
    get() = when (this) {
        BankStatus.NotStarted -> Pair(ZIcons.ic_glass_bank.asZIcon, ZIllustrationType.YELLOW)
        BankStatus.Verified -> Pair(ZIcons.ic_glass_info.asZIcon, ZIllustrationType.YELLOW)
        is BankStatus.Rejected -> Pair(ZIcons.ic_glass_bank.asZIcon, ZIllustrationType.RED)
        BankStatus.UnderReview -> Pair(ZIcons.ic_glass_search.asZIcon, ZIllustrationType.PURPLE)
    }


internal val BankStatus.title: String
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        BankStatus.NotStarted ->{
            val blockerController = LocalBlockerController.current
            val flyyTitle = blockerController.getBlockerTitleAndDescription()?.first
            if (blockerController.isTraderCampaignEnabled && flyyTitle != null)
                flyyTitle
            else stringResource(id = R.string.no_bank_account)
        }
        is BankStatus.Rejected -> stringResource(id = R.string.bank_account_rejected)
        else -> stringResource(id = R.string.bank_under_verification)
    }

internal val BankStatus.description: String
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        BankStatus.NotStarted -> {
            val blockerController = LocalBlockerController.current
            val flyyDesc = blockerController.getBlockerTitleAndDescription()?.second
            if (blockerController.isTraderCampaignEnabled && flyyDesc != null)
                flyyDesc
            else  stringResource(id = R.string.no_bank_account_desc)}
        is BankStatus.Rejected -> rejectReasons?.firstOrNull()
            ?: stringResource(id = R.string.bank_account_rejected_desc)

        else -> stringResource(id = R.string.bank_under_verification_desc)
    }


@Composable
fun BankStatus.primaryButtonAction(): ButtonAction? {
    return when (this) {
        BankStatus.Verified -> null
        BankStatus.UnderReview -> ButtonAction(
            label = stringResource(id = R.string.c_okay),
            action = BlockerAction.None,
        )

        else -> ButtonAction(
            label = stringResource(id = R.string.c_add_bank),
            action = BlockerAction.AddBank, // Replace with appropriate action
        )
    }
}

@Composable
fun UserBankStatusSheet(
    bankStatus: BankStatus,
    action: (BlockerAction) -> Unit,
) {
    val sheetController = LocalSheetCloseController.current
    ZBottomSheetContainer(
        title = bankStatus.title,
        illustrationIcon = bankStatus.illustration.first,
        illustrationType = bankStatus.illustration.second,
        showDivider = false,
        footer = {
            bankStatus.primaryButtonAction()?.let {
                ZPrimaryButton(
                    modifier = Modifier.weight(1f),
                    title = it.label,
                    onClick = {
                        sheetController.close()
                        it.action.let { userAction -> action.invoke(userAction) }
                    },
                    tagId = "bank_status_primary_${it.label}",
                )
            }
        },
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB2,
            LocalContentColor provides ZTheme.color.text.secondary,
        ) {
            Text(
                text = bankStatus.description,
                textAlign = TextAlign.Center,
                color = ZTheme.color.text.secondary,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewNoBankStatusSheet() {
    ZSheetPreviewContainer {
        UserBankStatusSheet(BankStatus.NotStarted) {}
    }
}


@ThemePreviews
@Composable
fun PreviewUnderVerificationBankStatusSheet() {
    ZSheetPreviewContainer {
        UserBankStatusSheet(BankStatus.UnderReview) {}
    }
}

@ThemePreviews
@Composable
fun PreviewRejectedBankStatusSheet() {
    ZSheetPreviewContainer {
        UserBankStatusSheet(BankStatus.Rejected(null, isAttemptsExhausted = false)) {}
    }
}