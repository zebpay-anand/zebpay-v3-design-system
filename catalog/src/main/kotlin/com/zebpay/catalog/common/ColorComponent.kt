package com.zebpay.catalog.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.colors.utils.ColorMapper
import com.zebpay.catalog.colors.utils.camelCaseToText
import com.zebpay.catalog.colors.utils.toHex
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.v3.components.utils.background


/**
 * A composable function that displays a row with three parts:
 * 1. A text label.
 * 2. A container with a color box and a text label.
 * 3. A color code.
 *
 * @param name The name to display in the first part.
 * @param lightModeColor The color to display in the light mode container.
 * @param darkModeColor The color to display in the dark mode container.
 * @param modifier The modifier to apply to the row.
 */
@Composable
fun ZColorRow(
    name: String,
    lightModeColor: ColorMapper,
    darkModeColor: ColorMapper,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Name
            Text(
                text = name.camelCaseToText(),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            // Light Mode Container
            ZColorContainer(
                colorMapper = lightModeColor,
                modifier = Modifier.weight(1f),
            )

            // Dark Mode Container
            ZColorContainer(
                colorMapper = darkModeColor,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

/**
 * A composable function that displays a color box with a text label and color code.
 * @param color The color to display.
 * @param modifier The modifier to apply to the container.
 */
@Composable
fun ZColorContainer(
    colorMapper: ColorMapper,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(ZTheme.shapes.full)
                .then(
                    if (colorMapper.isZGradient) {
                        Modifier.background(
                            gradient = colorMapper.zGradient,
                            shape = ZTheme.shapes.small,
                        )
                    } else {
                        Modifier.background(color = colorMapper.color, shape = ZTheme.shapes.medium)
                    },
                ),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = colorMapper.label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(4.dp))

        if (colorMapper.isZGradient) {
            colorMapper.zGradient.asColorList().forEach {
                Text(
                    text = it.toHex(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            Text(
                text = colorMapper.color.toHex(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ZColorRowPreview() {
    ZColorRow(
        name = "Text",
        lightModeColor = ColorMapper(
            label = "Red/03",
            color = Color.Red,
        ),
        darkModeColor = ColorMapper(
            label = "Red/03",
            color = Color.Red,
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun ColorContainerPreview() {
    ZColorContainer(
        colorMapper = ColorMapper(
            label = "Red/03",
            color = Color.Red,
        ),
    )
}

