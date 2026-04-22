package com.zebpay.ui.v3.components.templates.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zebpay.ui.v3.R
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.card.ZBlockerCard
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.templates.sheet.BankStatus
import com.zebpay.ui.v3.components.templates.sheet.BlockerAction
import com.zebpay.ui.v3.components.templates.sheet.ButtonAction
import com.zebpay.ui.v3.components.templates.sheet.KYCStatus
import com.zebpay.ui.v3.components.templates.sheet.UiBlockerState
import com.zebpay.ui.v3.components.utils.LocalBlockerController


private val KYCStatus.image: ZIcon
    get() = when (this) {
        KYCStatus.Pending -> ZIcons.ic_blocker_kyc_verify.asZIcon
        KYCStatus.Resume -> ZIcons.ic_blocker_kyc_verify.asZIcon
        is KYCStatus.Rejected -> ZIcons.ic_blocker_kyc_reject.asZIcon
        KYCStatus.UnderReview -> ZIcons.ic_blocker_kyc_review.asZIcon
        is KYCStatus.Exhausted -> ZIcons.ic_blocker_kyc_exhausted.asZIcon
        is KYCStatus.ReKycRequired.Pending -> ZIcons.ic_blocker_kyc_verify.asZIcon
        KYCStatus.ReKycRequired.Resume -> ZIcons.ic_blocker_kyc_verify.asZIcon
        is KYCStatus.ReKycRequired.UnderReview -> ZIcons.ic_blocker_kyc_review.asZIcon
        is KYCStatus.ReKycRequired.Rejected -> ZIcons.ic_blocker_kyc_reject.asZIcon
        is KYCStatus.ReKycRequired.Exhausted -> ZIcons.ic_blocker_kyc_exhausted.asZIcon
    }

private val KYCStatus.title: String
    @Composable @ReadOnlyComposable get() = when (this) {
        KYCStatus.Pending -> {
            val blockerController = LocalBlockerController.current
            val flyyTitle = blockerController.getBannerTitleAndDescription()?.first
            if (blockerController.isTraderCampaignEnabled && flyyTitle != null)
                flyyTitle
            else stringResource(id = R.string.blocker_complete_your_kyc)
        }

        KYCStatus.Resume -> {
            val blockerController = LocalBlockerController.current
            val flyyTitle = blockerController.getBannerTitleAndDescription()?.first
            if (blockerController.isTraderCampaignEnabled && flyyTitle != null)
                flyyTitle
            else stringResource(id = R.string.blocker_continue_your_kyc)
        }

        is KYCStatus.Rejected -> stringResource(id = R.string.blocker_rejected_your_kyc)
        KYCStatus.UnderReview -> stringResource(id = R.string.blocker_review_your_kyc)
        is KYCStatus.Exhausted -> stringResource(id = R.string.blocker_exhausted_your_kyc)
        is KYCStatus.ReKycRequired.Pending -> {
            this.reKycTitleAndBanner?.first
                ?: stringResource(id = R.string.re_kyc_pending)
        }

        KYCStatus.ReKycRequired.Resume -> stringResource(id = R.string.re_kyc_continue_verification)
        is KYCStatus.ReKycRequired.UnderReview -> stringResource(id = R.string.re_kyc_under_review)
        is KYCStatus.ReKycRequired.Rejected -> stringResource(id = R.string.re_kyc_rejected)
        is KYCStatus.ReKycRequired.Exhausted -> stringResource(id = R.string.re_kyc_attempts_exhausted)
    }

private val KYCStatus.subtext: String
    @Composable @ReadOnlyComposable get() = when (this) {
        KYCStatus.Pending,
        KYCStatus.Resume,
            -> {
            val blockerController = LocalBlockerController.current
            val flyyDesc = blockerController.getBannerTitleAndDescription()?.second
            if (blockerController.isTraderCampaignEnabled && flyyDesc != null)
                flyyDesc
            else stringResource(id = R.string.blocker_subtext_continue_your_kyc)
        }

        is KYCStatus.Rejected -> stringResource(id = R.string.blocker_subtext_rejected)
        KYCStatus.UnderReview -> stringResource(id = R.string.blocker_subtext_review)
        is KYCStatus.Exhausted -> stringResource(id = R.string.blocker_subtext_exhausted)
        is KYCStatus.ReKycRequired.Pending -> {
            this.reKycTitleAndBanner?.second
                ?: stringResource(id = R.string.re_kyc_verify_now_subtitle)
        }

        KYCStatus.ReKycRequired.Resume -> stringResource(id = R.string.re_kyc_verify_now_subtitle)
        is KYCStatus.ReKycRequired.UnderReview -> stringResource(id = R.string.re_kyc_verifying_details)
        is KYCStatus.ReKycRequired.Rejected -> stringResource(id = R.string.re_kyc_re_verify_subtitle)
        is KYCStatus.ReKycRequired.Exhausted -> stringResource(id = R.string.re_kyc_contact_support)
    }


private val BankStatus.title: String
    @Composable @ReadOnlyComposable get() = when (this) {
        BankStatus.NotStarted -> {
            val blockerController = LocalBlockerController.current
            val flyyTitle = blockerController.getBannerTitleAndDescription()?.first
            if (blockerController.isTraderCampaignEnabled && flyyTitle != null)
                flyyTitle
            else stringResource(id = R.string.blocker_bank_add_account)
        }

        BankStatus.UnderReview -> stringResource(id = R.string.blocker_bank_under_verification)
        is BankStatus.Rejected -> stringResource(id = R.string.blocker_bank_reject)
        BankStatus.Verified -> ""
    }

private val BankStatus.subtext: String
    @Composable @ReadOnlyComposable get() = when (this) {
        BankStatus.NotStarted -> {
            val blockerController = LocalBlockerController.current
            val flyyDesc = blockerController.getBannerTitleAndDescription()?.second
            if (blockerController.isTraderCampaignEnabled && flyyDesc != null)
                flyyDesc
            else stringResource(id = R.string.blocker_subtext_add_account)
        }

        BankStatus.UnderReview -> stringResource(id = R.string.blocker_subtext_under_verification)
        is BankStatus.Rejected -> stringResource(id = R.string.blocker_subtext_reject)
        BankStatus.Verified -> ""
    }

private val BankStatus.image: ZIcon
    get() = when (this) {
        BankStatus.NotStarted -> ZIcons.ic_blocker_bank_add.asZIcon
        BankStatus.UnderReview -> ZIcons.ic_blocker_bank_verify.asZIcon
        is BankStatus.Rejected -> ZIcons.ic_blocker_bank_reject.asZIcon
        BankStatus.Verified -> ZIcons.ic_blocker_bank_add.asZIcon
    }

@Composable
private fun BankStatus.primaryBankButtonAction(): ButtonAction? {
    return when (this) {
        BankStatus.UnderReview -> ButtonAction(
            label = stringResource(id = R.string.view_details),
            action = BlockerAction.AddBank,
        )

        is BankStatus.Rejected -> ButtonAction(
            label = stringResource(id = R.string.c_verify_bank),
            action = BlockerAction.AddBank, // Replace with appropriate action
        )

        BankStatus.NotStarted -> ButtonAction(
            label = stringResource(id = R.string.c_add_bank),
            action = BlockerAction.AddBank, // Replace with appropriate action
        )

        BankStatus.Verified -> null
    }
}

@Composable
private fun KYCStatus.primaryButtonAction(isScreenBlocker: Boolean): ButtonAction {
    return when (this) {
        KYCStatus.Pending -> ButtonAction(
            label = stringResource(R.string.start_kyc_verification),
            action = BlockerAction.VerifyKYC,
        )

        KYCStatus.Resume -> ButtonAction(
            label = stringResource(R.string.continue_kyc_verification),
            action = BlockerAction.VerifyKYC,
        )

        is KYCStatus.Rejected -> ButtonAction(
            label = stringResource(R.string.re_attempt_kyc),
            action = BlockerAction.VerifyKYC,
        )

        KYCStatus.UnderReview -> if (isScreenBlocker) {
            ButtonAction(
                label = stringResource(R.string.c_verify_bank),
                action = BlockerAction.VerifyBank,
            )
        } else {
            ButtonAction(
                label = stringResource(id = R.string.view_details),
                action = BlockerAction.ViewKYC,
            )
        }

        is KYCStatus.Exhausted -> ButtonAction(
            label = stringResource(R.string.raise_a_ticket),
            action = BlockerAction.RaiseTicket,
        )

        is KYCStatus.ReKycRequired.Pending -> ButtonAction(
            label = stringResource(R.string.re_kyc_complete_rekyc),
            action = BlockerAction.VerifyKYC,
        )

        KYCStatus.ReKycRequired.Resume -> ButtonAction(
            label = stringResource(R.string.re_kyc_continue_with_verification),
            action = BlockerAction.VerifyKYC,
        )

        is KYCStatus.ReKycRequired.UnderReview -> if (isScreenBlocker) {
            ButtonAction(
                label = stringResource(R.string.c_verify_bank),
                action = BlockerAction.VerifyBank,
            )
        } else {
            ButtonAction(
                label = stringResource(id = R.string.view_details),
                action = BlockerAction.ViewKYC,
            )
        }

        is KYCStatus.ReKycRequired.Rejected -> ButtonAction(
            label = stringResource(R.string.re_attempt_kyc),
            action = BlockerAction.VerifyKYC,
        )

        is KYCStatus.ReKycRequired.Exhausted -> ButtonAction(
            label = stringResource(R.string.raise_a_ticket),
            action = BlockerAction.RaiseTicket,
        )
    }
}


@Composable
private fun KYCStatus.secondaryButtonAction(
    showViewDetails: Boolean,
    isScreenBlocker: Boolean,
): ButtonAction? {
    return when (this) {
        KYCStatus.UnderReview -> if (isScreenBlocker) ButtonAction(
            label = stringResource(id = R.string.view_details),
            action = BlockerAction.ViewKYC,
        ) else null

        is KYCStatus.Rejected -> if (showViewDetails) {
            ButtonAction(
                label = stringResource(id = R.string.view_details),
                action = BlockerAction.ViewKYC,
            )
        } else null

        is KYCStatus.ReKycRequired.UnderReview -> if (isScreenBlocker) ButtonAction(
            label = stringResource(id = R.string.view_details),
            action = BlockerAction.ViewKYC,
        ) else null

        is KYCStatus.ReKycRequired.Rejected -> if (showViewDetails) {
            ButtonAction(
                label = stringResource(id = R.string.view_details),
                action = BlockerAction.ViewKYC,
            )
        } else null

        else -> null
    }
}


@Composable
fun ZUserBlockerCard(
    modifier: Modifier = Modifier,
    isScreenBlocker: Boolean = false,
    uiState: UiBlockerState,
    action: (BlockerAction) -> Unit,
) {
    when {
        uiState.isGuest -> {
            ZBlockerCard(
                modifier = modifier,
                headline = "Join 6M+ users on ZebPay",
                subText = "Signup and start trading today!",
                icon = ZIcons.ic_blocker_zebpay.asZIcon,
                blockerScreen = isScreenBlocker,
                primaryText = "Get Started",
            ) {
                action.invoke(BlockerAction.Login)
            }
        }

        uiState.isBlacklistUser -> {
            ZBlockerCard(
                modifier = modifier,
                headline = "Account Restricted",
                subText = "Visit help.zebpay.com for assistance",
                icon = ZIcons.ic_blocker_account_restrict.asZIcon,
                blockerScreen = isScreenBlocker,
                primaryText = "GET ASSISTANCE",
            ) {
                action.invoke(BlockerAction.GetAssistance)
            }
        }

        uiState.kycStatus != null -> {
            val kycStatus = uiState.kycStatus
            val primaryText = kycStatus.primaryButtonAction(isScreenBlocker = isScreenBlocker).label
            val primaryAction =
                kycStatus.primaryButtonAction(isScreenBlocker = isScreenBlocker).action
            val secondaryText = kycStatus.secondaryButtonAction(
                showViewDetails = false,
                isScreenBlocker = isScreenBlocker,
            )?.label
            val secondaryAction = kycStatus.secondaryButtonAction(
                showViewDetails = false,
                isScreenBlocker = isScreenBlocker,
            )?.action
            ZBlockerCard(
                modifier = modifier,
                headline = kycStatus.title,
                subText = kycStatus.subtext,
                icon = kycStatus.image,
                blockerScreen = isScreenBlocker,
                primaryText = primaryText,
                secondaryText = secondaryText,
                onSecondaryAction = {
                    secondaryAction?.let { action.invoke(it) }
                },
                buttonAction = {
                    action.invoke(primaryAction)
                },
            )
        }

        uiState.bankStatus != BankStatus.Verified -> {
            val bankStatus = uiState.bankStatus
            bankStatus.primaryBankButtonAction()?.let {
                val primaryText = it.label
                val primaryAction = it.action
                ZBlockerCard(
                    modifier = modifier,
                    headline = bankStatus.title,
                    subText = bankStatus.subtext,
                    icon = bankStatus.image,
                    blockerScreen = isScreenBlocker,
                    primaryText = primaryText,
                    buttonAction = {
                        action.invoke(primaryAction)
                    },
                )
            }
        }

        else -> {}
    }
}