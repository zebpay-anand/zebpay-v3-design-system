package com.zebpay.catalog.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.zebpay.catalog.base.ShowcaseRegistry

@Composable
fun CatalogNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {


    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = "Home",
    ) {
        // Home screen with module list
        composable("Home") {
            ShowcaseScreen { route ->
                navController.navigate(
                    route,
                    navOptions = navOptions {
                        launchSingleTop = true
                    },
                )
            }
        }
        // Dynamically create composable from registered modules
        ShowcaseRegistry.getModules().forEach { module ->
            composable(module.route) {
                module.content {
                    navController.navigateUp()
                }
            }
        }
    }
}