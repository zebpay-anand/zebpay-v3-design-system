package com.zebpay.catalog.inputfield

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.zebpay.catalog.common.CatalogScaffold
import com.zebpay.ui.designsystem.v3.theme.ZTheme
import com.zebpay.ui.designsystem.v3.theme.ZebpayTheme
import com.zebpay.ui.v3.components.atoms.label.ZLabel
import com.zebpay.ui.v3.components.molecules.field.ZOutlineInputField
import com.zebpay.ui.v3.components.molecules.field.ZVisualTransformationType

@Composable
fun ZInputFieldScreen(
    title: Int, subtitle: Int, onNavBack: () -> Unit,
    modifier: Modifier =
        Modifier,
) {
    // Test cases for different scenarios
    var usdtValue by remember { mutableStateOf("2,340.64359423") } // USDT - 8 decimal places
    var inrValue by remember { mutableStateOf("1,234.56") } // INR - 2 decimal places
    var btcValue by remember { mutableStateOf(TextFieldValue("0.12345678")) } // BTC - 8 decimal places
    var normalValue by remember { mutableStateOf(TextFieldValue("1,234.56")) } // Normal - 2 decimal places
    var phoneNumber by remember { mutableStateOf(TextFieldValue("9557492885")) }

    // Edge case test scenarios
    var largeNumberTest by remember { mutableStateOf("1234567890123.12345678") } // Large number test
    var smallDecimalTest by remember { mutableStateOf("0.00000001") } // Very small number
    var singleDigitTest by remember { mutableStateOf("5") } // Single digit
    var zeroTest by remember { mutableStateOf("0.00") } // Zero with decimals
    var maxDecimalTest by remember { mutableStateOf("999.99999999") } // Max decimals for international
    var maxDecimalLocalTest by remember { mutableStateOf("999.99") } // Max decimals for local
    var commaOnlyTest by remember { mutableStateOf("1,000") } // No decimal, only comma
    var decimalOnlyTest by remember { mutableStateOf("123.456") } // Only decimal, no comma
    var noFormattingTest by remember { mutableStateOf("1000000") } // No formatting applied yet
    var edgeDeletionTest by remember { mutableStateOf("2,345,542.9381") } // For testing decimal deletion
    var commaDeletionTest by remember { mutableStateOf("1,233,312") } // For testing comma deletion with cursor after comma
    var decimalMergeTest by remember { mutableStateOf("1,323.124") } // For testing decimal merge behavior
    var decimalMergeTest2 by remember { mutableStateOf("1.98") } // For testing simple decimal merge
    var commaOnCursorTest by remember { mutableStateOf("12,345") } // For testing comma deletion when cursor on comma
    var commaBackspaceSimpleTest by remember { mutableStateOf("1,234") } // For testing simple comma backspace navigation

    ZebpayTheme {
        CatalogScaffold(
            modifier = modifier,
            title = stringResource(title),
            subTitle = stringResource(subtitle),
            onBackPressed = onNavBack,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // USDT - International currency with 8 decimal places
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = usdtValue,
                    onValueChange = {
                        Log.d("ZInputFieldScreen", "USDT Value Changed: $it")
                        usdtValue = it },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00000000") },
                    label = { ZLabel(label = "USDT Amount (8 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "International currency - Up to 8 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = {
                        ZLabel(label = "₮")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // INR - Local currency with 2 decimal places
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = inrValue,
                    onValueChange = { inrValue = it },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00") },
                    label = { ZLabel(label = "INR Amount (2 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Local currency - Up to 2 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = {
                        ZLabel(label = "₹")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // BTC - International crypto with 8 decimal places (TextFieldValue)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = btcValue,
                    onValueChange = { btcValue = it },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00000000") },
                    label = { ZLabel(label = "BTC Amount (8 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Cryptocurrency - Up to 8 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = {
                        ZLabel(label = "₿")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // USD - Local/Normal currency with 2 decimal places (TextFieldValue)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = normalValue,
                    onValueChange = { normalValue = it },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00") },
                    label = { ZLabel(label = "INR Amount (2 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Standard currency - Up to 2 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = {
                        ZLabel(label = "$")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Phone number - No formatting
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "Enter phone number") },
                    label = { ZLabel(label = "Phone Number (No formatting)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Regular input - No special formatting applied",
                            maxLine = 2,
                        )
                    },
                    leading = {
                        ZLabel(label = "+")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                )

                // === COMPREHENSIVE TEST CASES ===

                // Large Number Test (International)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = largeNumberTest,
                    onValueChange = {
                        largeNumberTest = it
                        Log.d("TestCase", "Large Number: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "Enter large number") },
                    label = { ZLabel(label = "Large Number Test (8 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1,234,567,890,123.12345678 - Large number formatting",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "💰") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Very Small Decimal Test (International)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = smallDecimalTest,
                    onValueChange = {
                        smallDecimalTest = it
                        Log.d("TestCase", "Small Decimal: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00000001") },
                    label = { ZLabel(label = "Small Decimal Test (8 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 0.00000001 - Very small decimal handling",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "🔬") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Single Digit Test (Local)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = singleDigitTest,
                    onValueChange = {
                        singleDigitTest = it
                        Log.d("TestCase", "Single Digit: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "5") },
                    label = { ZLabel(label = "Single Digit Test (2 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 5 - Single digit, no formatting needed",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "1️⃣") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Zero with Decimals Test (Local)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = zeroTest,
                    onValueChange = {
                        zeroTest = it
                        Log.d("TestCase", "Zero Test: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "0.00") },
                    label = { ZLabel(label = "Zero Test (2 decimals)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 0.00 - Zero handling with decimals",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "0️⃣") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Max Decimals International Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = maxDecimalTest,
                    onValueChange = {
                        maxDecimalTest = it
                        Log.d("TestCase", "Max Decimal Intl: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "999.99999999") },
                    label = { ZLabel(label = "Max Decimals International (8)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 999.99999999 - Maximum 8 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "📊") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Max Decimals Local Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = maxDecimalLocalTest,
                    onValueChange = {
                        maxDecimalLocalTest = it
                        Log.d("TestCase", "Max Decimal Local: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "999.99") },
                    label = { ZLabel(label = "Max Decimals Local (2)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 999.99 - Maximum 2 decimal places",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "📈") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Comma Only Test (No Decimal)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commaOnlyTest,
                    onValueChange = {
                        commaOnlyTest = it
                        Log.d("TestCase", "Comma Only: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1,000") },
                    label = { ZLabel(label = "Comma Only Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1,000 - Whole numbers with commas only",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "🔢") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Decimal Only Test (No Comma)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = decimalOnlyTest,
                    onValueChange = {
                        decimalOnlyTest = it
                        Log.d("TestCase", "Decimal Only: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "123.456") },
                    label = { ZLabel(label = "Decimal Only Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 123.456 - Numbers with decimals but no commas",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "🎯") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // No Formatting Applied Yet Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = noFormattingTest,
                    onValueChange = {
                        noFormattingTest = it
                        Log.d("TestCase", "No Formatting: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1000000") },
                    label = { ZLabel(label = "Auto-Format Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1000000 → 1,000,000 - Auto formatting on type",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "✨") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Edge Case: Decimal Deletion Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = edgeDeletionTest,
                    onValueChange = {
                        edgeDeletionTest = it
                        Log.d("TestCase", "Edge Deletion: $it")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "2,345,542.9381") },
                    label = { ZLabel(label = "Decimal Deletion Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: Delete '.' to remove entire decimal part (.9381)",
                            maxLine = 2,
                        )
                    },
                    leading = { ZLabel(label = "🗑️") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Edge Case: Comma Deletion Test (Cursor after comma)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commaDeletionTest,
                    onValueChange = {
                        commaDeletionTest = it
                        Log.d("TestCase", "Comma After Deletion: '$it'")
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1,233,312") },
                    label = { ZLabel(label = "Case 2B: Comma Backspace Navigation") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1,233,|312 → BACKSPACE → 1,233|,312 (cursor jumps left of comma)",
                            maxLine = 3,
                        )
                    },
                    leading = { ZLabel(label = "⌫") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Edge Case: Decimal Merge Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = decimalMergeTest,
                    onValueChange = {
                        Log.d("TestCase", "Decimal Merge: old='$decimalMergeTest' → new='$it'")
                        decimalMergeTest = it
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1,323.124") },
                    label = { ZLabel(label = "Decimal Merge Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1,323.124 → delete '.' → should become 1,323,124 (merge decimal)",
                            maxLine = 3,
                        )
                    },
                    leading = { ZLabel(label = "🔗") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Edge Case: Simple Decimal Merge Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = decimalMergeTest2,
                    onValueChange = {
                        Log.d("TestCase", "Simple Decimal Merge: old='$decimalMergeTest2' → new='$it'")
                        decimalMergeTest2 = it
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1.98") },
                    label = { ZLabel(label = "Simple Decimal Merge Test") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1.98 → delete '.' → should become 198 (simple merge)",
                            maxLine = 3,
                        )
                    },
                    leading = { ZLabel(label = "🔀") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                // Edge Case: Comma On Cursor Test
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commaOnCursorTest,
                    onValueChange = {
                        Log.d("TestCase", "Comma On Cursor: old='$commaOnCursorTest' → new='$it'")
                        commaOnCursorTest = it
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1,234") },
                    label = { ZLabel(label = "Case 2A: Comma Forward Delete") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 12|,345 → DELETE → 12|345 (removes comma, cursor stays)",
                            maxLine = 3,
                        )
                    },
                    leading = { ZLabel(label = "📍") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )

                Spacer(modifier = Modifier.height(16.dp))

                // Case 2B Simple: Comma Backspace Navigation (Simple Case)
                ZOutlineInputField(
                    modifier = Modifier.fillMaxWidth(),
                    value = commaBackspaceSimpleTest,
                    onValueChange = {
                        Log.d("TestCase", "Simple Comma Backspace: old='$commaBackspaceSimpleTest' → new='$it'")
                        commaBackspaceSimpleTest = it
                    },
                    textStyle = ZTheme.typography.bodyRegularB3,
                    supportTextStyle = ZTheme.typography.bodyRegularB4,
                    placeholder = { ZLabel(label = "1,234") },
                    label = { ZLabel(label = "Case 2B Simple: Comma Backspace (Simple)") },
                    isError = false,
                    textBottom = {
                        ZLabel(
                            label = "Test: 1,|234 → BACKSPACE → 1|,234 (cursor jumps left of comma)",
                            maxLine = 3,
                        )
                    },
                    leading = { ZLabel(label = "⬅️") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                    visualTransformation = ZVisualTransformationType.Price(2),

                    )
            }
        }
    }
}