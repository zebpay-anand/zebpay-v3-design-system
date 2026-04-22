package com.zebpay.catalog.typography

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.catalog.common.TitleAndBody
import com.zebpay.catalog.typography.utils.StyleType
import com.zebpay.catalog.typography.utils.extractTypographyMap
import com.zebpay.catalog.typography.utils.getLabelName
import com.zebpay.catalog.typography.utils.getTypesOfStyleRequired
import com.zebpay.catalog.typography.utils.tagDescriptionMap
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZTypography
import com.zebpay.ui.designsystem.v3.theme.ZebpayTypography
import com.zebpay.ui.designsystem.v3.utils.bold
import com.zebpay.ui.designsystem.v3.utils.medium

@Composable
fun TypographyScreen(
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
        val typographyMap = extractTypographyMap(ZebpayTypography)
        TypographyCatalog(
            typographyMap = typographyMap,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

/**
 * A composable function that displays a catalog of text styles.
 * @param typography The [ZTypography] object containing the text styles.
 * @param modifier The modifier to apply to the catalog.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TypographyCatalog(
    typographyMap: Map<String, List<Triple<String, TextStyle, Boolean>>>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {

        typographyMap.forEach { (category, styles) ->
            stickyHeader {
                TitleAndBody(
                    modifier = Modifier
                        .fillMaxWidth(),
                    title = category,
                    body = tagDescriptionMap[category] ?: "",
                )
            }
            item {
                getTypesOfStyleRequired(category).forEach {
                    val title = when (it) {
                        StyleType.BOLD -> "Bold"
                        StyleType.MEDIUM -> "Medium"
                        else -> "Regular"
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    TitleAndBodyCard(title = title) {
                        styles.forEach { (name, textStyle, isCapital) ->
                            val updateStyle = when (it) {
                                StyleType.BOLD -> textStyle.bold()
                                StyleType.MEDIUM -> textStyle.medium()
                                else -> textStyle
                            }
                            TypographyItem(
                                name = getLabelName(title, name),
                                label = getLabelName(title, name),
                                style = updateStyle,
                                isUpperCase = isCapital,
                            )
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.size(12.dp))
            }
        }
    }
}


/**
 * A composable function that displays a card with a title and body text.
 * @param title The title text to display.
 * @param body The body text to display.
 * @param modifier The modifier to apply to the card.
 */
@Composable
fun TitleAndBodyCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge,
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                thickness = 1.dp,
                color = ZTheme.color.separator.solidDefault,
            )
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleAndBodyCardPreview() {
    TitleAndBodyCard(
        title = "Bold",
    ) {
        TypographyItem(
            name = "D1/bold_32",
            label = "D1/bold_32",
            style = MaterialTheme.typography.displayLarge.bold(),
        )
        TypographyItem(
            name = "D2/bold_28",
            label = "D2/bold_28",
            style = MaterialTheme.typography.displayMedium.bold(),
        )
        TypographyItem(
            name = "D3/bold_24",
            label = "D3/bold_24",
            style = MaterialTheme.typography.displaySmall.bold(),
        )
    }
}


/**
 * A composable function that displays a single text style.
 *
 * @param name The name of the text style.
 * @param style The [TextStyle] to display.
 * @param modifier The modifier to apply to the item.
 */
@Composable
fun TypographyItem(
    name: String,
    label: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    isUpperCase: Boolean = false,
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = if (isUpperCase) label.uppercase() else label,
            style = style,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun TypographyItemPreview() {
    TypographyItem(
        name = "D1/bold_32",
        label = "D1/bold_32",
        style = MaterialTheme.typography.headlineSmall,
    )
}