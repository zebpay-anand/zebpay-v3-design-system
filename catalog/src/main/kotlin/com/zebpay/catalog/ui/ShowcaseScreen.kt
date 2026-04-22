package com.zebpay.catalog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.base.ShowcaseModule
import com.zebpay.catalog.base.ShowcaseRegistry

@Composable
fun ShowcaseScreen(
    modifier: Modifier = Modifier,
    navigateToRoute: (String) -> Unit,
) {
    val modules = ShowcaseRegistry.getModules()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Design System Catalog",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
        },
    ) { innerPadding ->
        NavigationList(
            modules = modules,
            navigateToRoute = navigateToRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

/**
 * A composable list that displays a list of navigation cards.
 *
 * @param modules The list of [NavigationDestination] objects to display.
 * @param navigateToRoute The function to call when a card is clicked.
 * @param modifier The modifier to apply to the list.
 */
@Composable
fun NavigationList(
    modules: List<ShowcaseModule>,
    navigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(modules) { module ->
            NavigationCard(
                name = stringResource(module.title),
                destination = module.route,
                onNavigate = navigateToRoute,
                modifier = Modifier,
            )
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}


/**
 * A composable card that represents a navigation destination.
 *
 * @param destination The [String] to navigate to.
 * @param onNavigate The function to call when the card is clicked.
 * @param modifier The modifier to apply to the card.
 */
@Composable
fun NavigationCard(
    name: String,
    destination: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                try {
                    onNavigate(destination)
                } catch (e: Exception) {
                    // Handle navigation error
                    println("Error navigating to ${destination}: ${e.message}")
                }
            }
            .semantics {
                contentDescription = "Navigate to ${destination} Screen"
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = name,
            )
            Icon(
                imageVector = Icons.Filled.ArrowForward,
                contentDescription = "Navigate to $name",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationCardPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        NavigationCard(
            name = "Typography",
            destination = "Typography",
            onNavigate = {},
            modifier = Modifier,
        )
    }
}