package com.zebpay.ui.v3.components.templates.sheet

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.zebpay.ui.v3.components.organisms.bottomsheet.ZBottomSheet

/**
 * Represents the different actions that can be taken to unblock a user.
 *
 * This sealed interface defines the possible actions that can be taken when a user
 * encounters a blocker. Each action is represented by a data object that implements
 * this interface.
 *
 * The possible actions are:
 * - [VerifyKYC]: Verify the user's KYC information.
 * - [ViewKYC]: View the user's KYC status.
 * - [VerifyBank]: Verify the user's bank account.
 * - [RaiseTicket]: Raise a support ticket for the user.
 * - [AddBank]: Add a new bank account for the user.
 * - [Login]: Log in the user.
 * - [GetAssistance]: Get assistance from a customer support representative.
 * - [VerifyEmail]: Verify the user's email address.
 */
sealed interface BlockerAction {

    /**
     * Verify the user's KYC information.
     *
     * This action is used when the user needs to complete or update their KYC
     * (Know Your Customer) information.
     */
    data object VerifyKYC : BlockerAction

    /**
     * View the user's KYC status.
     *
     * This action is used when the user wants to check the status of their KYC
     * verification process.
     */
    data object ViewKYC : BlockerAction

    /**
     * Verify the user's bank account.
     *
     * This action is used when the user needs to verify their bank account
     * information.
     */
    data object VerifyBank : BlockerAction

    /**
     * Raise a support ticket for the user.
     *
     * This action is used when the user needs to contact customer support for
     * assistance.
     */
    data object RaiseTicket : BlockerAction

    /**
     * Add a new bank account for the user.
     *
     * This action is used when the user wants to add a new bank account to their
     * profile.
     */
    data object AddBank : BlockerAction

    /**
     * Log in the user.
     *
     * This action is used when the user needs to log in to their account.
     */
    data object Login : BlockerAction

    /**
     * Get assistance from a customer support representative.
     *
     * This action is used when the user needs to speak to a customer support
     * representative for assistance.
     */
    data object GetAssistance : BlockerAction

    /**
     * Verify the user's email address.
     *
     * This action is used when the user needs to verify their email address.
     */
    data object VerifyEmail : BlockerAction

    /**
     * Represents the absence of any specific blocker action.
     *
     * This can be used when there is no specific action required to unblock the
     * user, or when the blocker is informational only.
     */
    data object None : BlockerAction
}

/**
 * Represents the UI blocker state for a user.
 *
 * This state determines which blocker card/sheet should be displayed to the user.
 * The blockers are checked in priority order:
 * 1. Guest user → Login blocker
 * 2. Blacklisted user → Account restriction blocker
 * 3. Email not verified → Email verification blocker
 * 4. KYC status (including ReKYC) → KYC blocker
 * 5. Bank status → Bank verification blocker
 *
 * @property isGuest Whether the user is a guest (not logged in)
 * @property isBlacklistUser Whether the user account is blacklisted/restricted
 * @property isEmailVerified Whether the user's email is verified
 * @property kycStatus Current KYC status, including ReKYC variants if [isReKycRequired] is true
 * @property bankStatus Current bank verification status
 * @property isReKycRequired Whether ReKYC is required for this user
 * @property reKycTitleAndBanner Optional Pair(title, bannerMessage) from server for ReKYC states.
 *                                Used when mapping to ReKycRequired.Pending or ReKycRequired.Rejected.
 *                                First element is title, second is banner/subtitle message.
 */
data class UiBlockerState(
    val isGuest: Boolean = true,
    val isBlacklistUser: Boolean = false,
    val isEmailVerified: Boolean = false,
    val kycStatus: KYCStatus? = null,
    val bankStatus: BankStatus = BankStatus.NotStarted,
    val isReKycRequired: Boolean = false,
    val reKycTitleAndBanner: Pair<String, String>? = null,
)

data class ButtonAction(
    val label: String,
    val action: BlockerAction,
)

@Composable
fun ZUserBlockSheet(
    showBlockerSheet: MutableState<Boolean>,
    uiState: UiBlockerState,
    action: (BlockerAction) -> Unit,
) {
    ZBottomSheet(
        isVisible = showBlockerSheet,
        modifier = Modifier,
        allowDismiss = true,
    ) {
        when {
            uiState.isGuest -> {
                GetStartedSheet(action = action)
            }

            uiState.isBlacklistUser -> {
                AccountRestrictionSheet(action = action)
            }

            uiState.isEmailVerified.not() -> {
                EmailBlockerSheet(action = action)
            }

            uiState.kycStatus != null -> {
                UserKYCStatusSheet(
                    uiState.kycStatus,
                    showViewDetailInRejectedState = false,
                    action = action,
                )
            }

            else -> {
                UserBankStatusSheet(
                    uiState.bankStatus,
                    action = action,
                )
            }
        }
    }
}


