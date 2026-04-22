package com.zebpay.ui.designsystem.v3.utils

// Annotation to mark properties for dynamic reading
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
annotation class ZColorBinding(
    val label: String,
)
