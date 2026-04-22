package com.zebpay.catalog.misc

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.ui.v3.components.atoms.misc.ZIndicator
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews
import kotlin.math.roundToInt

@Composable
fun ZMiscScreen(
    @StringRes title: Int,
    @StringRes subTitle: Int,
    modifier: Modifier = Modifier,
    onNavBack: () -> Unit,
) {
    ZebpayTheme {
        CatalogScaffold(
            modifier = modifier,
            title = stringResource(title),
            subTitle = stringResource(subTitle),
            onBackPressed = onNavBack,
        ) { innerPadding ->
            ZPMiscShowcase(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun ZPMiscShowcase(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        var totalDots by remember { mutableIntStateOf(5) }
        var selectedIndex by remember { mutableIntStateOf(0) }
        var dotSizeFloat by remember { mutableFloatStateOf(6f) }
        var activeDotWidthFloat by remember { mutableFloatStateOf(30f) }
        var spacingFloat by remember { mutableFloatStateOf(4f) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ZIndicator(
                totalDots = totalDots,
                selectedIndex = selectedIndex,
                dotSize = dotSizeFloat.roundToInt().dp,
                activeDotWidth = activeDotWidthFloat.roundToInt().dp,
                spacing = spacingFloat.roundToInt().dp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = totalDots.toString(),
                    onValueChange = {
                        totalDots = it.toIntOrNull() ?: 0
                    },
                    label = { Text("Total Dots") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            selectedIndex =
                                (selectedIndex - 1).takeIf { it >= 0 } ?: (totalDots - 1)
                        },
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Previous")
                    }
                    Button(
                        onClick = {
                            selectedIndex = (selectedIndex + 1) % totalDots
                        },
                    ) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "Next")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Dot Size", fontWeight = FontWeight.Bold)
                    Text(text = dotSizeFloat.roundToInt().toString() + "dp")
                }
                Slider(
                    value = dotSizeFloat,
                    onValueChange = { dotSizeFloat = it },
                    valueRange = 2f..20f,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Active Dot Width", fontWeight = FontWeight.Bold)
                    Text(text = activeDotWidthFloat.roundToInt().toString() + "dp")
                }
                Slider(
                    value = activeDotWidthFloat,
                    onValueChange = { activeDotWidthFloat = it },
                    valueRange = 10f..50f,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "Spacing", fontWeight = FontWeight.Bold)
                    Text(text = spacingFloat.roundToInt().toString() + "dp")
                }
                Slider(
                    value = spacingFloat,
                    onValueChange = { spacingFloat = it },
                    valueRange = 0f..10f,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }

    }
}


@ThemePreviews
@Composable
fun PreviewZPMiscShowcase() {
    ZebpayTheme {
        Column(modifier = Modifier.background(color = ZTheme.color.background.default)) {
            ZPMiscShowcase()
        }
    }
}