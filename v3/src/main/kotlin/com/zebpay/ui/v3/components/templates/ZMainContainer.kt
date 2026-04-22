package com.zebpay.ui.v3.components.templates

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.empty.EmptyStateView
import com.zebpay.ui.v3.components.molecules.header.ZToolbar
import com.zebpay.ui.v3.components.molecules.toast.ZToast
import com.zebpay.ui.v3.components.molecules.toast.ZToastType
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.utils.LocalZAmountMasked
import com.zebpay.ui.v3.components.utils.LocalZIconSize
import com.zebpay.ui.v3.components.utils.LocalZMainContainerState
import com.zebpay.ui.v3.components.utils.ToastInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class ZSnackDuration {
    Short,
    Long,
    Indefinite
}

class ZMainContainerState(internal val scope: CoroutineScope) {

    internal var showSnackBar by mutableStateOf(false)
        private set

    internal var toastInfo: ToastInfo? = null

    private var hideJob: Job? = null


    fun showSnackBar(toastInfo: ToastInfo) {
        this.toastInfo = toastInfo
        this.showSnackBar = true
        autoHideSnackBar(toastInfo.duration)
    }

    private fun autoHideSnackBar(snackDuration: ZSnackDuration) {
        val duration = when (snackDuration) {
            ZSnackDuration.Short -> 4_000L
            ZSnackDuration.Long -> 10_000L
            ZSnackDuration.Indefinite -> -1L
        }
        if (duration > 0)
            hideJob = scope.launch(Dispatchers.IO) {
                delay(duration)
                hideSnackBar()
            }
    }

    fun hideSnackBar() {
        this.showSnackBar = false
        this.toastInfo = null
    }

}

@Composable
fun rememberZMainContainerState(
    scope: CoroutineScope = rememberCoroutineScope()
): ZMainContainerState {
    return remember {
        ZMainContainerState(scope)
    }
}

@Composable
fun ZMainContainerBox(
    modifier: Modifier = Modifier,
    state: ZMainContainerState = rememberZMainContainerState(),
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalZMainContainerState provides state) {
        Box(modifier.fillMaxSize()) {
            content()
            SnackBarHostView(state = state)
        }
    }
}


@Composable
fun BoxScope.SnackBarHostView(state: ZMainContainerState) {
    val toastInfo = state.toastInfo
    val alignment = if (toastInfo?.showOnTop == true)
        Alignment.TopCenter else Alignment.BottomCenter
    AnimatedVisibility(
        visible = state.showSnackBar,
        modifier = Modifier
            .align(alignment),
        enter = slideInVertically {
            it
        } + scaleIn() + fadeIn(),
        exit = slideOutVertically {
            it
        } + scaleOut() + fadeOut(),
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
                .zIndex(100f),
        ) {
            if (toastInfo != null) {
                ZToast(
                    title = toastInfo.title,
                    message = toastInfo.message,
                    type = toastInfo.type,
                    onClose = if (toastInfo.allowClose) {
                        { state.hideSnackBar() }
                    } else null,
                    showShadow = toastInfo.showShadow,
                )
            } else {
                state.hideSnackBar()
            }
        }
    }
}

@ThemePreviews
@Composable
fun PreviewZMainContainerBox() {
    ZebpayTheme {
        val mainContainerState = rememberZMainContainerState()
        ZMainContainerBox(state = mainContainerState) {
            ZScaffold(
                topBar = {
                    ZToolbar(
                        title = "Headline",
                        actions = {
                            ZToolbarIconAction(
                                icon = ZIcons.ic_hide.asZIcon,
                                onClick = {},
                            )
                            ZToolbarIconAction(
                                icon = ZIcons.ic_edit.asZIcon,
                                onClick = {},
                            )
                        },
                    )
                },
            ) {
                CompositionLocalProvider(
                    LocalContentColor provides ZTheme.color.icon.singleToneWhite,
                    LocalZIconSize provides 20.dp,
                ) {
                    EmptyStateView(
                        modifier = Modifier.padding(it),
                        icon = ZIcons.ic_info.asZIcon,
                        title = "Main Container Demo",
                        subTitle = "Click to see Toast on Main Container",
                        primaryCTA = "Show Toast",
                        primaryClick = {
                            mainContainerState.showSnackBar(
                                ToastInfo(
                                    title = "Send Crypto Disabled",
                                    message = "Due to activation of instant deposit",
                                    type = ZToastType.Error,
                                ),
                            )
                        },
                    )
                }
            }
        }
    }
}


