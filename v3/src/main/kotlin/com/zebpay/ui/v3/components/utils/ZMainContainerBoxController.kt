package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.v3.components.atoms.icon.ZIcon
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.molecules.toast.ZToastType
import com.zebpay.ui.v3.components.resource.ZIcons
import com.zebpay.ui.v3.components.templates.ZMainContainerState
import com.zebpay.ui.v3.components.templates.ZSnackDuration

val LocalZMainContainerState = staticCompositionLocalOf<ZMainContainerState> {
    error("ZBox State is not set.")
}


data class ToastInfo(
    val title: String,
    val message: String? = null,
    val type: ZToastType = ZToastType.Info,
    val showShadow: Boolean = true,
    val maxMessageLine: Int = 5,
    val prefixIcon: ZIcon = ZIcons.ic_info.asZIcon,
    val allowClose: Boolean = true,
    val showOnTop: Boolean = true,
    val duration: ZSnackDuration = ZSnackDuration.Long,
)


data class TopToastInfo(
    val message: String,
    val type: ZToastType = ZToastType.Info,
    val maxMessageLine: Int = 1,
    val duration: ZSnackDuration = ZSnackDuration.Long,
    val showClose: Boolean = false,
    val isProgress: Boolean = false,
    val ctaText: String = "",
    val hideOnAction: Boolean = true,
    val toastAction:(()->Unit)?=null,
)