package com.zebpay.ui.v3.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
/**
 * A custom layout that arranges its children vertically, ensuring all children
 * are measured with the same width, determined by the widest child's
 * intrinsic preference.
 *
 * This approach uses intrinsic width measurement and is generally more efficient
 * than a SubcomposeLayout for this use case.
 *
 * @param modifier The modifier to be applied to this layout.
 * @param content The composable children to measure and arrange.
 */
@Composable
fun EqualWidthColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        // --- 1. Query Intrinsic Widths ---
        // Don't measure yet. First, ask each child what its preferred maximum width is.
        // This is a lightweight query that doesn't perform a full measurement pass.
        val maxWidth = measurables.maxOfOrNull {
            it.maxIntrinsicWidth(constraints.maxHeight)
        } ?: 0

        // --- 2. Measure Children with New Constraints ---
        // Now, create a new set of constraints that forces every child
        // to have the exact same width (the `maxWidth` we just found).
        val childConstraints = constraints.copy(minWidth = maxWidth, maxWidth = maxWidth)

        // Measure each child a SINGLE time with these new, identical width constraints.
        val placeables = measurables.map { it.measure(childConstraints) }

        // The total height of our layout is the sum of the heights of our children.
        val totalHeight = placeables.sumOf { it.height }

        // --- 3. Place Children ---
        // Set the size of the EqualWidthColumn layout itself.
        layout(width = maxWidth, height = totalHeight) {
            var yPosition = 0
            // Place each child vertically.
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}