package com.zebpay.ui.designsystem.v3.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.zebpay.ui.designsystem.v3.color.ZColorScheme
import com.zebpay.ui.designsystem.v3.color.ZColorScheme.Companion.ZDarkColorScheme
import com.zebpay.ui.designsystem.v3.color.ZColorScheme.Companion.ZLightColorScheme

@Immutable
data class ZNewStyle(
    val typography: ZTypography,
    val shape: Shapes,
    val colors: ZColorScheme,
    val isDarkTheme: Boolean,
)

val LocalZTheme: ProvidableCompositionLocal<ZNewStyle> = staticCompositionLocalOf {
    ZNewStyle(
        typography = ZebpayTypography,
        shape = ZShapes,
        colors = ZLightColorScheme,
        isDarkTheme = false
    )
}

@Immutable
data class UserUtilitiesData(val isInternationUser: Boolean = false)
val UserLocalProvider: ProvidableCompositionLocal<UserUtilitiesData> = compositionLocalOf { UserUtilitiesData() }

object ZTheme {

    val color: ZColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalZTheme.current.colors

    val typography: ZTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalZTheme.current.typography

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalZTheme.current.shape

    val isDarkTheme: Boolean
        @Composable
        @ReadOnlyComposable
        get() = LocalZTheme.current.isDarkTheme

}


sealed interface UiTheme {
    data object System : UiTheme

    data object Light : UiTheme

    data object Dark : UiTheme

    val code: Int
        get() = when (this) {
            Dark -> 2
            Light -> 1
            System -> 0
        }

    companion object {
        fun fromCode(code: Int): UiTheme = when (code) {
            1 -> Light
            2 -> Dark
            else -> System
        }
    }
}


@Composable
fun ZebpayTheme(
    uiTheme: UiTheme = UiTheme.System,
    isInternationUser: Boolean=false,
    content: @Composable () -> Unit,
) {
    val isInDarkMode = when (uiTheme) {
        UiTheme.Dark -> true
        UiTheme.Light -> false
        UiTheme.System -> isSystemInDarkTheme()
    }
    val zebpayTheme = ZNewStyle(
        typography = ZebpayTypography,
        shape = ZShapes,
        colors = if (isInDarkMode)
            ZDarkColorScheme
        else
            ZLightColorScheme,
        isDarkTheme = isInDarkMode,
    )
    CompositionLocalProvider(
        LocalZTheme provides zebpayTheme,
        UserLocalProvider provides remember(isInternationUser) { UserUtilitiesData(isInternationUser) }
    ) {
        MaterialTheme(
            content = content,
        )
    }
}

