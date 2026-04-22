package com.zebpay.catalog.typography.utils

import androidx.compose.ui.text.TextStyle
import com.zebpay.ui.designsystem.v3.theme.ZTypography
import com.zebpay.ui.designsystem.v3.utils.TextStyleBinding
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findAnnotation


fun extractTypographyMap(typography: ZTypography): Map<
        String,
        List<
                Triple<
                        String,
                        TextStyle, Boolean,
                        >,
                >,
        > {
    return ZTypography::class.members
        .filterIsInstance<KProperty1<ZTypography, TextStyle>>() // Only TextStyle properties
        .mapNotNull { prop ->
            val annotation = prop.findAnnotation<TextStyleBinding>()
            annotation?.let { tag ->
                Triple(tag.tag, tag.label, prop.get(typography)) to tag.isCapital
            }
        }
        .groupBy(
            { it.first.first },
            { Triple(it.first.second, it.first.third, it.second) },
        ) // Group by annotation tag
}

val tagDescriptionMap = hashMapOf(
    "Display" to "Large, eye-catching headlines for hero sections or key highlights.",
    "Headline" to "Primary headers in sections or screens, slightly smaller than \"Display\".",
    "Body" to "General-purpose text for content-heavy areas and details in Card.",
    "CTA" to "All Button Texts",
)

enum class StyleType {
    BOLD,
    MEDIUM,
    REGULAR
}

fun getTypesOfStyleRequired(title: String): List<StyleType> {
    return when (title) {
        "Body" -> arrayListOf(StyleType.BOLD, StyleType.MEDIUM, StyleType.REGULAR)
        "CTA" -> arrayListOf(StyleType.BOLD)
        else -> arrayListOf(StyleType.BOLD, StyleType.REGULAR)
    }
}

fun getLabelName(variant: String, currentLabel: String): String {
    return if ("Regular" in currentLabel) {
        currentLabel.replace("Regular", variant)
    } else {
        currentLabel
    }
}
