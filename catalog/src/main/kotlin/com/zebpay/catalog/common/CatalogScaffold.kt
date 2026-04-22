package com.zebpay.catalog.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zebpay.catalog.LocalThemeController
import com.zebpay.ui.designsystem.v3.color.ZGradientColors
import com.zebpay.ui.designsystem.v3.theme.UiTheme
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.v3.components.atoms.icon.ZIconButton
import com.zebpay.ui.v3.components.atoms.icon.asZIcon
import com.zebpay.ui.v3.components.templates.ZScaffold
import com.zebpay.ui.v3.components.templates.ZScaffoldState
import com.zebpay.ui.v3.components.templates.rememberZScaffoldState
import com.zebpay.ui.v3.components.utils.background


@Composable
fun CatalogScaffold(
    modifier: Modifier = Modifier,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    topBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {

    val toggleTheme = LocalThemeController.current
    val theme = if (toggleTheme.isDarkTheme) {
        UiTheme.Dark
    } else {
        UiTheme.Light
    }
    ZebpayTheme(theme) {
        Scaffold(
            modifier = modifier,
            topBar = topBar,
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(gradient = ZGradientColors.Primary01),
                ) {
                    ZIconButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = toggleTheme.toggle,
                        icon = if (toggleTheme.isDarkTheme) {
                            Icons.Default.LightMode.asZIcon
                        } else {
                            Icons.Default.DarkMode.asZIcon
                        },
                        size = 30.dp,
                        color = Color.White,
                    )
                }
            },
            contentWindowInsets = contentWindowInsets,
            content = content,
            containerColor = ZTheme.color.background.default,
        )
    }
}


@Composable
fun ZCatalogScaffold(
    modifier: Modifier = Modifier,
    state: ZScaffoldState = rememberZScaffoldState(),
    title: String,
    subTitle: String,
    onBackPressed: () -> Unit,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {

    val toggleTheme = LocalThemeController.current
    val theme = if (toggleTheme.isDarkTheme) {
        UiTheme.Dark
    } else {
        UiTheme.Light
    }
    ZebpayTheme(theme) {
        ZScaffold(
            modifier = modifier,
            state = state,
            topBar = {
                AppBar(title, subTitle, onBackPressed)
            },
            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(gradient = ZGradientColors.Primary01),
                ) {
                    ZIconButton(
                        modifier = Modifier.align(Alignment.Center),
                        onClick = toggleTheme.toggle,
                        icon = if (toggleTheme.isDarkTheme) {
                            Icons.Default.LightMode.asZIcon
                        } else {
                            Icons.Default.DarkMode.asZIcon
                        },
                        size = 30.dp,
                        color = Color.White,
                    )
                }
            },
            contentWindowInsets = contentWindowInsets,
            content = content,
            containerColor = ZTheme.color.background.default,
        )
    }
}

@Composable
fun CatalogScaffold(
    modifier: Modifier = Modifier,
    title: String,
    subTitle: String,
    onBackPressed: () -> Unit,
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit,
) {
    CatalogScaffold(
        modifier = modifier,
        topBar = {
            AppBar(title, subTitle, onBackPressed)
        },
        contentWindowInsets = contentWindowInsets,
        content = content,
    )
}

@Composable
fun AppBar(
    title: String,
    subTitle: String,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(85.dp),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
            ) {
                IconButton(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = {
                        onBackPressed.invoke()
                    },
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Icons.Default.ArrowBack",
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontSize = 16.sp),
                        textAlign = TextAlign.Start,
                        color = ZTheme.color.text.primary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(Modifier.size(4.dp))
                    Text(
                        text = subTitle,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        textAlign = TextAlign.Start,
                        color = ZTheme.color.text.secondary,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ZTheme.color.background.default,
        ),
    )
}
