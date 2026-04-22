package com.zebpay.catalog

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.zebpay.catalog.theme.CatalogTheme
import com.zebpay.catalog.ui.CatalogNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
        )
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(false) }
            CompositionLocalProvider(
                LocalThemeController provides ThemeController(isDarkTheme) {
                    isDarkTheme = !isDarkTheme
                },
            ) {
                CatalogTheme(darkTheme = isDarkTheme) {
                    CatalogNavHost(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

data class ThemeController(
    val isDarkTheme: Boolean,
    val toggle: () -> Unit,
)

val LocalThemeController = staticCompositionLocalOf<ThemeController> {
    error("No ThemeController provided")
}