package com.zebpay.ui.v3.components.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

fun Modifier.setTestingTag(text: String) : Modifier = this.testTag(text).semantics{
    testTagsAsResourceId = true
}