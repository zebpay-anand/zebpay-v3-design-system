package com.zebpay.ui.v3.components.templates

import android.app.Activity
import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsControllerCompat
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import com.zebpay.ui.v3.components.atoms.header.ZToolbarIconAction
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.header.ZToolbar
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.templates.scaffold.ZScaffoldContent
import com.zebpay.ui.v3.components.utils.LocalBlurController
import com.zebpay.ui.v3.components.utils.TopToastInfo
import com.zebpay.ui.v3.components.utils.ZBlurController
import com.zebpay.ui.v3.components.utils.conditionalBlur
import kotlinx.coroutines.*


class ZScaffoldState(
    internal val scope: CoroutineScope,
) {

    internal var showTopToast by mutableStateOf(false)
        private set

    internal var topToastInfo: TopToastInfo? = null

    internal var blurredBitmap by mutableStateOf<ImageBitmap?>(null)

    internal var showBlurBackground by mutableStateOf(false)
        private set

    private var hideJob: Job? = null

    fun toggleBlurBackground(toggle: Boolean) {
        showBlurBackground = toggle
        if (toggle.not()) {
            blurredBitmap = null
        }
    }

    fun showTopToast(toastInfo: TopToastInfo) {
        this.topToastInfo = toastInfo
        this.showTopToast = true
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
        this.showTopToast = false
        scope.launch {
            delay(500) // Delay for 1000 seconds
            topToastInfo = null
        }
        this.hideJob?.cancel()
    }
}

@Composable
fun rememberZScaffoldState(
    scope: CoroutineScope = rememberCoroutineScope(),
): ZScaffoldState {
    return remember {
        ZScaffoldState(scope)
    }
}

@Composable
fun ZScaffold(
    modifier: Modifier = Modifier,
    state: ZScaffoldState = rememberZScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = ZTheme.color.background.default,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    bottomPadding: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    SetSystemBarsColor(
        statusBarColor = containerColor,
        statusBarDarkIcons = ZTheme.isDarkTheme.not(),
        navBarColor = Color.White,
        navBarDarkIcons = ZTheme.isDarkTheme.not(),
    )
    CompositionLocalProvider(
        LocalBlurController provides ZBlurController {
            state.toggleBlurBackground(it)
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .conditionalBlur(
                    applyBlur = state.showBlurBackground,
                    radius = 20.dp,
                ),
        ) {
            Scaffold(
                modifier = modifier,
                topBar = topBar,
                bottomBar = bottomBar,
                floatingActionButton = floatingActionButton,
                floatingActionButtonPosition = floatingActionButtonPosition,
                containerColor = containerColor,
                contentColor = contentColor,
                contentWindowInsets = contentWindowInsets,
                content = {
                    ZScaffoldContent(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                paddingValues = PaddingValues(
                                    top = it.calculateTopPadding(),
                                    bottom = if (bottomPadding) it.calculateBottomPadding() else 0.dp,
                                ),
                            ),
                        state = state,
                        content = content,
                    )
                },
            )
        }
    }
}


@Composable
fun SetSystemBarsColor(
    statusBarColor: Color,
    statusBarDarkIcons: Boolean,
    navBarColor: Color,
    navBarDarkIcons: Boolean,
) {
    val activity = LocalContext.current
    if (activity !is Activity)
        return
    val window = activity.window

    // Set status bar and navigation bar background colors
    window.statusBarColor = statusBarColor.toArgb()
    window.navigationBarColor = navBarColor.toArgb()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val controller = window.insetsController
        controller?.setSystemBarsAppearance(
            (if (statusBarDarkIcons) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0) or
                    (if (navBarDarkIcons) WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS else 0),
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
        )
    } else {
        val controllerCompat = WindowInsetsControllerCompat(window, window.decorView)
        controllerCompat.isAppearanceLightStatusBars = statusBarDarkIcons

        // Only API 27+ supports light navigation bar icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            controllerCompat.isAppearanceLightNavigationBars = navBarDarkIcons
        }
    }
}


@ThemePreviews
@Composable
private fun PreviewZScaffold() {
    val state = rememberZScaffoldState()
    ZebpayTheme {
        ZScaffold(
            state = state,
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

        }
    }
}