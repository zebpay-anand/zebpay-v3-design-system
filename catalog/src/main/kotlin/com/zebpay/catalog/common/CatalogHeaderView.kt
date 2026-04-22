package com.zebpay.catalog.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.ui.v3.components.atoms.seperator.ZHorizontalDivider
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.utils.ThemePreviews


/**
 * A composable function that displays a title and body text in a column, with full width.
 * @param title The title text to display.
 * @param body The body text to display.
 * @param modifier The modifier to apply to the column.
 */
@Composable
fun TitleAndBody(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )
    }
}

/**
 * A composable function that displays a title in a column, with full width.
 * @param title The title text to display.
 * @param modifier The modifier to apply to the column.
 */
@Composable
fun TitleHeader(
    title: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .fillMaxWidth()
                .height(48.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth(),
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
        )
    }
}

/**
 * A composable function that displays a row with three equally divided text views: Name, Light Mode, and Dark Mode.
 *
 * @param header1 The name to display.
 * @param header2 The light mode value to display.
 * @param header3 The dark mode value to display.
 * @param modifier The modifier to apply to the row.
 */
@Composable
fun NameLightDarkModeRow(
    header1: String,
    header2: String,
    header3: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Name
            Text(
                text = header1,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                color = ZTheme.color.text.secondary,
            )

            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp, color = ZTheme.color.separator.solidDefault,
            )
            // Light Mode
            Text(
                text = header2,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = ZTheme.color.text.secondary,
            )
            VerticalDivider(
                modifier = Modifier.fillMaxHeight(),
                thickness = 1.dp, color = ZTheme.color.separator.solidDefault,
            )
            // Dark Mode
            Text(
                text = header3,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                color = ZTheme.color.text.secondary,
            )
        }
        ZHorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NameLightDarkModeRowPreview() {
    NameLightDarkModeRow(
        header1 = "Name",
        header2 = "Light Mode",
        header3 = "Dark Mode",
    )
}

@ThemePreviews
@Composable
fun TitleAndBodyPreview() {
    TitleAndBody(
        title = "Display",
        body = "Large, eye-catching headlines for hero sections or key highlights.",
    )
}

@Preview(showBackground = true)
@Composable
fun TitleHeaderPreview() {
    TitleHeader(
        title = "Header Sticky",
    )
}