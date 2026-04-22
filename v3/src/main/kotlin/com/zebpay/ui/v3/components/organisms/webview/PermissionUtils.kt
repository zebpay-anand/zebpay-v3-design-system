package com.zebpay.ui.v3.components.organisms.webview

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * True if ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION is granted.
 */
internal val Context.hasLocationPermission: Boolean
    get() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

/**
 * True if CAMERA permission is granted.
 */
internal val Context.hasCameraPermission: Boolean
    get() = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED

/**
 * True if RECORD_AUDIO permission is granted.
 */
internal val Context.hasMicrophonePermission: Boolean
    get() = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
            PackageManager.PERMISSION_GRANTED

/**
 * Returns false when the system "Don't ask again" flag is set for the given permission name
 * (CAMERA, LOCATION, MICROPHONE), meaning we should direct the user to Settings.
 */
internal fun Activity.shouldShowPermissionRationaleFor(permissionName: String): Boolean {
    val manifest = when (permissionName.uppercase()) {
        "CAMERA" -> Manifest.permission.CAMERA
        "MICROPHONE" -> Manifest.permission.RECORD_AUDIO
        "LOCATION" -> Manifest.permission.ACCESS_FINE_LOCATION
        else -> return true
    }
    return shouldShowRequestPermissionRationale(manifest)
}
