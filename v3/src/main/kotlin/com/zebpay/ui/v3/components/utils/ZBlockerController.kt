package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.staticCompositionLocalOf

data class ZBlockerController(
    val flyyEnabled: Boolean,
    val isTraderCampaignEnabled: Boolean,
    val currentPoints: Int = 0,
    val getBlockerTitleAndDescription: () -> Pair<String, String>?,
    val getBannerTitleAndDescription: () -> Pair<String, String>?,
)

val LocalBlockerController = staticCompositionLocalOf<ZBlockerController> {
    ZBlockerController(
        flyyEnabled = false,
        isTraderCampaignEnabled = false,
        currentPoints = 0,
        getBannerTitleAndDescription = { null },
        getBlockerTitleAndDescription = { null },
    )
}
