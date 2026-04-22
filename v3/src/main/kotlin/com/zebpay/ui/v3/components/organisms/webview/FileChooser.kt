package com.zebpay.ui.v3.components.organisms.webview

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCameraPermission(
    onPermissionResult: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = onPermissionResult,
    )

    LaunchedEffect(Unit) {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED -> {
                onPermissionResult(true)
            }
            else -> {
                launcher.launch(Manifest.permission.CAMERA)
            }
        }
    }
}

/**
 * Requests location permissions for WebView geolocation support.
 * Uses ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION.
 */
@Composable
fun RequestLocationPermission(
    onPermissionResult: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val allGranted = permissions.values.any { it }
            onPermissionResult(allGranted)
        },
    )

    LaunchedEffect(Unit) {
        if (context.hasLocationPermission) {
            onPermissionResult(true)
        } else {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }
}

/**
 * Requests permission by name for the WebView permission bridge.
 * Supported permission names: CAMERA, MICROPHONE, LOCATION
 */
@Composable
fun RequestPermission(
    permissionName: String,
    onResult: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val anyGranted = permissions.values.any { it }
            onResult(anyGranted)
        },
    )

    LaunchedEffect(permissionName) {
        val name = permissionName.uppercase()
        val alreadyGranted = when (name) {
            "CAMERA" -> context.hasCameraPermission
            "MICROPHONE" -> context.hasMicrophonePermission
            "LOCATION" -> context.hasLocationPermission
            else -> {
                onResult(false)
                return@LaunchedEffect
            }
        }
        if (alreadyGranted) {
            onResult(true)
        } else {
            val permissions = when (name) {
                "CAMERA" -> arrayOf(Manifest.permission.CAMERA)
                "MICROPHONE" -> arrayOf(Manifest.permission.RECORD_AUDIO)
                "LOCATION" -> arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )
                else -> emptyArray()
            }
            if (permissions.isNotEmpty()) {
                launcher.launch(permissions)
            } else {
                onResult(false)
            }
        }
    }
}

fun createImageUri(context: Context): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
}
