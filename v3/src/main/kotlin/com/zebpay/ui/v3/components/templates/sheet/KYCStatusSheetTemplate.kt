package com.zebpay.ui.v3.components.templates.sheet

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.R
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.ZIllustrationType
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.bottomsheet.ZBottomSheetContainer
import com.zebpay.ui.v3.components.molecules.button.ZOutlineButton
import com.zebpay.ui.v3.components.molecules.button.ZPrimaryButton
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalBlockerController
import com.zebpay.ui.v3.components.utils.LocalSheetCloseController
import com.zebpay.ui.v3.components.utils.ZSheetPreviewContainer

/**
 * Represents the different KYC (Know Your Customer) statuses.
 *
 * This sealed interface defines the possible KYC statuses that a user can have.
 * Each status is represented by a data object or data class that implements this interface.
 *
 * The possible statuses are:
 * - [Pending]: KYC is pending review.
 * - [Resume]: KYC is in a resume state, indicating that the user needs to continue the process.
 * - [Rejected]: KYC is rejected, with an optional reason provided.
 * - [UnderReview]: KYC is currently under review.
 * - [Exhausted]: KYC has reached the maximum limit of attempts.
 */
sealed interface KYCStatus {

    /**
     * KYC is pending review.
     *
     * This status indicates that the user's KYC information has been submitted
     * and is waiting to be reviewed.
     */
    data object Pending : KYCStatus

    /**
     * KYC is in resume state.
     *
     * This status indicates that the user has started the KYC process but has not
     * completed it and needs to resume.
     */
    data object Resume : KYCStatus

    /**
     * KYC is rejected with an optional reason.
     *
     * This status indicates that the user's KYC information has been rejected.
     *
     * @property reason The reason for rejection, if any. This can be null if no specific
     * reason is provided.
     */
    data class Rejected(val reason: String?) : KYCStatus

    /**
     * KYC is under review.
     *
     * This status indicates that the user's KYC information is currently being reviewed.
     */
    data object UnderReview : KYCStatus

    /**
     * KYC has reached the maximum limit.
     *
     * This status indicates that the user has exceeded the maximum number of attempts
     * allowed for the KYC process.
     *
     * @property maxLimit The maximum limit for KYC attempts. This is a string representation
     * of the limit.
     */
    data class Exhausted(val maxLimit: String) : KYCStatus

    /**
     * ReKYC is required with different variants based on current KYC status.
     *
     * This sealed interface represents the different states a user can be in when ReKYC is required.
     * Each variant maps to a specific UI state with appropriate title, description, and actions.
     *
     * Mapping from [UserKycStatus] to ReKYC variants:
     * - NotStarted/InProgress → [Pending] (user can start/continue verification)
     * - UnderReview → [UnderReview] (verification is being processed)
     * - Rejected (attempts NOT exhausted) → [Rejected] (user can re-attempt)
     * - Rejected (attempts exhausted) → [Exhausted] (user must contact support)
     * - Verified → [Exhausted] (edge case: verified but reKYC still required)
     *
     * Note: The distinction between [Rejected] and [Exhausted] is critical:
     * - [Rejected]: User has attempts remaining, can re-attempt KYC
     * - [Exhausted]: User has exhausted all attempts, must contact support
     * This is determined by checking [UserKycStatus.Rejected.isAttemptsExhausted] in the mapping logic.
     */
    sealed interface ReKycRequired : KYCStatus {
        /**
         * ReKYC is pending/in-progress - user needs to start/continue verification.
         *
         * This state is used when the user hasn't started ReKYC or has started but not completed it.
         * The server may provide custom title and banner messages via [reKycTitleAndBanner].
         * If not provided, default strings are used.
         *
         * @property reKycTitleAndBanner Optional Pair(title, bannerMessage) from server.
         *                                First element is the title, second is the banner/subtitle message.
         */
        data class Pending(
            val reKycTitleAndBanner: Pair<String, String>?,
        ) : ReKycRequired

        data object Resume : ReKycRequired

        /**
         * ReKYC is under review.
         *
         * This state indicates that the user's ReKYC submission is currently being reviewed.
         * User can view details but cannot take further action until review is complete.
         */
        data object UnderReview : ReKycRequired

        /**
         * ReKYC was rejected - user needs to re-attempt.
         *
         * This state is used when ReKYC was rejected but the user still has attempts remaining.
         * The user can re-attempt the verification process.
         * The server may provide custom title and banner messages via [reKycTitleAndBanner].
         *
         * @property reKycTitleAndBanner Optional Pair(title, bannerMessage) from server.
         *                                First element is the title, second is the banner/subtitle message.
         */
        data class Rejected(
            val reKycTitleAndBanner: Pair<String, String>?,
        ) : ReKycRequired

        /**
         * ReKYC attempts exhausted - user needs to contact support.
         *
         * This state is used when the user has exhausted all ReKYC attempts.
         * The user cannot re-attempt and must contact customer support for assistance.
         * This occurs when [UserKycStatus.Rejected.isAttemptsExhausted] is true.
         */
        data object Exhausted : ReKycRequired
    }
}


/**
 * Provides an illustration (icon and color) associated with each KYC status.
 * @receiver The [KYCStatus] instance for which to get the illustration.
 * @return A [Pair] containing the [ZIcon] and [ZIllustrationType] for the given KYC status.
 */
internal val KYCStatus.illustration: Pair<ZIcon, ZIllustrationType>
    get() = when (this) {
        KYCStatus.Pending -> Pair(ZIcons.ic_glass_summary.asZIcon, ZIllustrationType.BLUE)
        KYCStatus.Resume -> Pair(ZIcons.ic_glass_info.asZIcon, ZIllustrationType.YELLOW)
        is KYCStatus.Rejected -> Pair(ZIcons.ic_glass_error.asZIcon, ZIllustrationType.RED)
        KYCStatus.UnderReview -> Pair(ZIcons.ic_glass_search.asZIcon, ZIllustrationType.YELLOW)
        is KYCStatus.Exhausted -> Pair(ZIcons.ic_glass_info.asZIcon, ZIllustrationType.PURPLE)
        is KYCStatus.ReKycRequired.Pending -> Pair(ZIcons.ic_glass_summary.asZIcon, ZIllustrationType.BLUE)
        KYCStatus.ReKycRequired.Resume -> Pair(ZIcons.ic_glass_info.asZIcon, ZIllustrationType.YELLOW)
        is KYCStatus.ReKycRequired.UnderReview -> Pair(ZIcons.ic_glass_search.asZIcon, ZIllustrationType.YELLOW)
        is KYCStatus.ReKycRequired.Rejected -> Pair(ZIcons.ic_glass_error.asZIcon, ZIllustrationType.RED)
        is KYCStatus.ReKycRequired.Exhausted -> Pair(ZIcons.ic_glass_info.asZIcon, ZIllustrationType.PURPLE)
    }

/**
 * Gets the title text for the KYC status.
 *
 * For ReKYC states:
 * - [ReKycRequired.Pending]: Uses server-provided title from [reKycTitleAndBanner] if available,
 *   otherwise falls back to default "RE-KYC: Continue Verification"
 * - [ReKycRequired.UnderReview]: "RE-KYC: Under Review"
 * - [ReKycRequired.Rejected]: "RE-KYC: Rejected"
 * - [ReKycRequired.Exhausted]: "RE-KYC: Attempts Exhausted"
 */
internal val KYCStatus.title: String
    @Composable @ReadOnlyComposable get() = when (this) {
        KYCStatus.Pending,
        KYCStatus.Resume,
            -> {
            val blockerController = LocalBlockerController.current
            val flyyTitle = blockerController.getBlockerTitleAndDescription()?.first
            if (blockerController.isTraderCampaignEnabled && flyyTitle != null)
                flyyTitle
            else
                stringResource(id = R.string.complete_your_kyc)
        }

        is KYCStatus.Rejected -> stringResource(id = R.string.kyc_rejected)
        KYCStatus.UnderReview -> stringResource(id = R.string.kyc_under_review)
        is KYCStatus.Exhausted -> stringResource(id = R.string.kyc_attempt_exhausted)
        // ReKYC Pending: Use server-provided title if available, otherwise use default
        is KYCStatus.ReKycRequired.Pending -> {
            this.reKycTitleAndBanner?.first
                ?: stringResource(id = R.string.re_kyc_pending)
        }

        KYCStatus.ReKycRequired.Resume -> stringResource(id = R.string.re_kyc_continue_verification)
        is KYCStatus.ReKycRequired.UnderReview -> stringResource(id = R.string.re_kyc_under_review)
        is KYCStatus.ReKycRequired.Rejected -> stringResource(id = R.string.re_kyc_rejected)
        is KYCStatus.ReKycRequired.Exhausted -> stringResource(id = R.string.re_kyc_attempts_exhausted)
    }

/**
 * Gets the description/subtitle text for the KYC status.
 *
 * For ReKYC states:
 * - [ReKycRequired.Pending]: Uses server-provided bannerMessage from [reKycTitleAndBanner] if available,
 *   otherwise falls back to default "Verify now to maintain seamless access"
 * - [ReKycRequired.UnderReview]: "We're verifying your details"
 * - [ReKycRequired.Rejected]: "Please re-verify to get full access"
 * - [ReKycRequired.Exhausted]: "Please contact customer support"
 */
internal val KYCStatus.description: String
    @Composable @ReadOnlyComposable get() = when (this) {
        KYCStatus.Pending, KYCStatus.Resume ->{
            val blockerController = LocalBlockerController.current
            val flyyDesc = blockerController.getBlockerTitleAndDescription()?.second
            if (blockerController.isTraderCampaignEnabled && flyyDesc != null)
                flyyDesc
            else
                stringResource(id = R.string.complete_kyc_desc)
        }
        is KYCStatus.Rejected -> reason ?: stringResource(id = R.string.kyc_reject_desc)
        KYCStatus.UnderReview -> stringResource(id = R.string.kyc_under_review_desc)
        is KYCStatus.Exhausted -> stringResource(
            id = R.string.kyc_attempt_exhausted_desc,
            this.maxLimit,
        )
        // ReKYC Pending: Use server-provided bannerMessage if available, otherwise use default
        is KYCStatus.ReKycRequired.Pending -> {
            this.reKycTitleAndBanner?.second
                ?: stringResource(id = R.string.re_kyc_verify_now_subtitle)
        }

        KYCStatus.ReKycRequired.Resume -> stringResource(id = R.string.re_kyc_verify_now_subtitle)
        is KYCStatus.ReKycRequired.UnderReview -> stringResource(id = R.string.re_kyc_verifying_details)
        is KYCStatus.ReKycRequired.Rejected -> stringResource(id = R.string.re_kyc_re_verify_subtitle)
        is KYCStatus.ReKycRequired.Exhausted -> stringResource(id = R.string.re_kyc_contact_support)
    }

internal val KYCStatus.primaryButtonAction: ButtonAction
    @Composable @ReadOnlyComposable get() = when (this) {
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

        KYCStatus.UnderReview -> ButtonAction(
            label = stringResource(R.string.c_verify_bank),
            action = BlockerAction.VerifyBank,
        )

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

        is KYCStatus.ReKycRequired.UnderReview -> ButtonAction(
            label = stringResource(R.string.view_details),
            action = BlockerAction.ViewKYC,
        )

        is KYCStatus.ReKycRequired.Rejected -> ButtonAction(
            label = stringResource(R.string.re_attempt_kyc),
            action = BlockerAction.VerifyKYC,
        )

        is KYCStatus.ReKycRequired.Exhausted -> ButtonAction(
            label = stringResource(R.string.raise_a_ticket),
            action = BlockerAction.RaiseTicket,
        )
    }


@Composable
fun KYCStatus.secondaryButtonAction(showViewDetails: Boolean): ButtonAction? {
    return when (this) {
        KYCStatus.UnderReview -> ButtonAction(
            label = stringResource(id = R.string.view_details),
            action = BlockerAction.ViewKYC,
        )

        is KYCStatus.Rejected -> if (showViewDetails) {
            ButtonAction(
                label = stringResource(id = R.string.view_details),
                action = BlockerAction.ViewKYC,
            )
        } else null

        is KYCStatus.ReKycRequired.UnderReview -> ButtonAction(
            label = stringResource(id = R.string.view_details),
            action = BlockerAction.ViewKYC,
        )

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
fun UserKYCStatusSheet(
    kycStatus: KYCStatus,
    showViewDetailInRejectedState: Boolean = false,
    action: (BlockerAction) -> Unit,
) {
    val sheetController = LocalSheetCloseController.current
    ZBottomSheetContainer(
        title = kycStatus.title,
        illustrationIcon = kycStatus.illustration.first,
        illustrationType = kycStatus.illustration.second,
        contentPadding = PaddingValues(horizontal = 16.dp),
        showDivider = false,
        footer = {
            kycStatus.secondaryButtonAction(showViewDetailInRejectedState)?.let {
                ZOutlineButton(
                    modifier = Modifier.weight(1f),
                    title = it.label,
                    onClick = {
                        sheetController.close()
                        action.invoke(it.action)
                    },
                    tagId = "kyc_status_secondary_${it.label}",
                )
            }
            kycStatus.primaryButtonAction.let {
                ZPrimaryButton(
                    modifier = Modifier.weight(1f),
                    title = it.label,
                    onClick = {
                        sheetController.close()
                        action.invoke(it.action)
                    },
                    tagId = "kyc_status_primary_${it.label}",
                )
            }
        },
    ) {
        CompositionLocalProvider(
            LocalTextStyle provides ZTheme.typography.bodyRegularB2,
            LocalContentColor provides ZTheme.color.text.secondary,
        ) {
            Text(
                text = kycStatus.description,
                textAlign = TextAlign.Center,
                color = ZTheme.color.text.secondary,
            )
        }
    }
}


@ThemePreviews
@Composable
fun PreviewPendingKYCStatusSheet() {
    ZSheetPreviewContainer {
        UserKYCStatusSheet(KYCStatus.Pending) {}
    }
}

@ThemePreviews
@Composable
fun PreviewResumeKYCStatusSheet() {
    ZSheetPreviewContainer {
        UserKYCStatusSheet(KYCStatus.Resume) {}
    }
}


@ThemePreviews
@Composable
fun PreviewUnderReviewKYCStatusSheet() {
    ZSheetPreviewContainer {
        UserKYCStatusSheet(KYCStatus.UnderReview) {}
    }
}

@ThemePreviews
@Composable
fun PreviewRejectedKYCStatusSheet() {
    ZSheetPreviewContainer {
        UserKYCStatusSheet(KYCStatus.Rejected(null)) {}
    }
}

@ThemePreviews
@Composable
fun PreviewExhaustedKYCStatusSheet() {
    ZSheetPreviewContainer {
        UserKYCStatusSheet(KYCStatus.Exhausted("three")) {}
    }
}