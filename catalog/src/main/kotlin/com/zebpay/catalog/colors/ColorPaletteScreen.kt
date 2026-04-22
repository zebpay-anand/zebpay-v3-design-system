package com.zebpay.catalog.colors

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zebpay.catalog.colors.utils.capitalizeFirstChar
import com.zebpay.catalog.colors.utils.getColorSchemaMap
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.NameLightDarkModeRow
import com.zebpay.catalog.common.TitleHeader
import com.zebpay.catalog.common.ZColorRow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorPaletteScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
) {
    CatalogScaffold(
        modifier = modifier,
        title = stringResource(title),
        subTitle = stringResource(subTitle),
        onBackPressed = onNavBack,
    ) { innerPadding ->
        val colorSchemaMap = getColorSchemaMap()
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            NameLightDarkModeRow(
                header1 = "Name",
                header2 = "Light Mode",
                header3 = "Dark Mode",
            )
            LazyColumn(modifier = Modifier.weight(1f)) {
                colorSchemaMap.keys.forEach {header->
                    stickyHeader {
                        TitleHeader(
                            title = header.capitalizeFirstChar(),
                        )
                    }
                    items(colorSchemaMap[header] ?: arrayListOf()) { colorTriple ->
                        ZColorRow(colorTriple.first, colorTriple.second, colorTriple.third)
                    }
                }
            }
        }
    }
}

