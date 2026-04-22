package com.zebpay.ui.v3.components.templates.scaffold

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.zebpay.ui.v3.components.molecules.toast.ZTopToast
import com.zebpay.ui.v3.components.templates.ZScaffoldState


@Composable
fun ZScaffoldContent(
    modifier: Modifier = Modifier,
    state: ZScaffoldState,
    content: @Composable (PaddingValues) -> Unit,
){

    var toastHeightPx by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    // Convert height in px to Dp with animation
    val animatedOffset by animateDpAsState(
        targetValue = with(density) { toastHeightPx.toDp() }.takeIf { state.showTopToast } ?: 0.dp,
        label = "ToastOffsetAnimation"
    )
    val animatedTopPadding by animateDpAsState(targetValue = animatedOffset, label = "TopPaddingAnimation")
    Box(modifier = modifier){
        Box(modifier = Modifier.fillMaxSize().padding(top = animatedTopPadding)) {
            content(PaddingValues(0.dp))
        }
        TopSnackBarHostView(modifier = Modifier
            .onGloballyPositioned { layoutCoordinates ->
            toastHeightPx = layoutCoordinates.size.height
        }, state = state)
    }
}


@Composable
private fun TopSnackBarHostView(
    modifier: Modifier = Modifier,
    state: ZScaffoldState,
){
    val toastInfo = state.topToastInfo
    AnimatedVisibility(
        visible = state.showTopToast,
        modifier = modifier.zIndex(100f),
        enter = slideInVertically { -it },
        exit = slideOutVertically { -it },
    ) {
        if(toastInfo!=null) {
            ZTopToast(
                modifier = Modifier.zIndex(100f),
                message = toastInfo.message ,
                type = toastInfo.type,
                showClose = toastInfo.showClose,
                ctaText = toastInfo.ctaText,
                isProgress = toastInfo.isProgress,
                maxMessageLine = toastInfo.maxMessageLine,
                onClick = {
                    toastInfo.toastAction?.invoke()
                    if(toastInfo.hideOnAction) {
                        state.hideSnackBar()
                    }
                }
            )
        }
    }
}