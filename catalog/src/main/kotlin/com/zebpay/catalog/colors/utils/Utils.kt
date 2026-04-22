package com.zebpay.catalog.colors.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.zebpay.ui.designsystem.v3.color.ZColorScheme
import com.zebpay.ui.designsystem.v3.color.ZGradient
import com.zebpay.ui.designsystem.v3.utils.ZColorBinding
import com.zebpay.ui.designsystem.v3.utils.asZGradient
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

data class ColorMapper(
    val label: String,
    val color: Color = Color.Transparent,
    val zGradient: ZGradient = Color.Transparent.asZGradient(),
    val isZGradient: Boolean = false,
)


fun getColorSchemaMap(): MutableMap<String, MutableList<Triple<String, ColorMapper, ColorMapper>>> {
    val lightPropertyMapper: MutableMap<String, ColorMapper> = LinkedHashMap()
    val darkPropertyMapper: MutableMap<String, ColorMapper> = LinkedHashMap()
    val javaClazz = ZColorScheme::class.java.kotlin
    val companion = javaClazz.companionObjectInstance // Get Companion Object Instance
    companion?.let { companionObject ->
        javaClazz.companionObject?.memberProperties?.forEach { prop ->
            val uiColorScheme = prop.getter.call(companionObject) // Call property getter
            if (uiColorScheme != null) {
                val kColorSchemeClass = uiColorScheme::class
                val annotation = prop.findAnnotation<ZColorBinding>()
                updatePropertiesInColorMap(
                    kColorSchemeClass, uiColorScheme, labelAppend = "",
                    if (annotation?.label.equals("Light Mode"))
                        lightPropertyMapper
                    else darkPropertyMapper,
                )
            }
        }
    }

    val returnMap = mergeColorMaps(lightPropertyMapper, darkPropertyMapper)
    //print
    return returnMap
}


fun updatePropertiesInColorMap(
    kColorSchemeClass: KClass<out Any>,
    uiColorScheme: Any,
    labelAppend: String = "",
    lightPropertyMapper: MutableMap<String, ColorMapper>,
) {
    // Get properties in the order they were declared in the constructor
    val orderedProperties = kColorSchemeClass.primaryConstructor?.parameters?.mapNotNull { param ->
        kColorSchemeClass.memberProperties.find { it.name == param.name }
    } ?: kColorSchemeClass.memberProperties // Fallback to normal order if no constructor

    orderedProperties.forEach { kProp ->
        val kPropMember = kProp.getter.call(uiColorScheme) // Call property getter
        if (kPropMember != null) {
            val annotation = kProp.findAnnotation<ZColorBinding>()
            val annotationLabel = annotation?.label ?: ""
            val label = kProp.name
            val propertyLabel = "$labelAppend${label}"
            if (kPropMember is Color) {
                lightPropertyMapper[propertyLabel] = ColorMapper(
                    label = annotationLabel,
                    color = kPropMember,
                )
            } else if (kPropMember is ZGradient) {
                lightPropertyMapper[propertyLabel] = ColorMapper(
                    label = annotationLabel,
                    zGradient = kPropMember,
                    isZGradient = true,
                )
            } else {
                val kPropMemberClazz = kPropMember::class
                updatePropertiesInColorMap(
                    kPropMemberClazz,
                    kPropMember,
                    "$propertyLabel/",
                    lightPropertyMapper,
                )
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
fun mergeColorMaps(
    map1: MutableMap<String, ColorMapper>,
    map2: MutableMap<String, ColorMapper>,
): MutableMap<String, MutableList<Triple<String, ColorMapper, ColorMapper>>> {
    val mergedMap = mutableMapOf<String, MutableList<Triple<String, ColorMapper, ColorMapper>>>()

    // ✅ Collect all unique keys from both maps
    val allKeys = map1.keys + map2.keys

    for (key in allKeys) {
        val (parentKey, label) = splitKey(key)
        val color1 = map1[key] ?: Color.Transparent // Fallback if not found
        val color2 = map2[key] ?: Color.Transparent // Fallback if not found
        val triple = Triple(label, color1, color2)
        // ✅ Ensure parentKey exists in the merged map
        mergedMap.getOrPut(parentKey) { mutableListOf() }
            .add(triple as Triple<String, ColorMapper, ColorMapper>)
    }

    return mergedMap
}

// ✅ Helper function to split key into ParentKey + Label
fun splitKey(key: String): Pair<String, String> {
    val parts = key.split("/")
    return if (parts.size > 1) {
        val parentKey = parts.dropLast(1).joinToString("/") // "Text/primary"
        val label = parts.last() // "blue"
        parentKey to label
    } else {
        key to "" // If no "/", keep key as-is with an empty label
    }
}

fun String.capitalizeFirstChar(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}

fun String.camelCaseToText(): String {
    return this.replace(Regex("([a-z])([A-Z])"), "$1 $2") // Insert space before uppercase letters
        .replaceFirstChar { it.uppercaseChar() } // Capitalize first letter
}

// ✅ Convert Color to HEX Code
fun Color.toHex(): String {
    return "#${Integer.toHexString(toArgb()).uppercase()}"
}


