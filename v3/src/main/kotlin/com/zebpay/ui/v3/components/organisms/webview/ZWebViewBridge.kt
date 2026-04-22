package com.zebpay.ui.v3.components.organisms.webview

/**
 * Bridge interface between the [WebViewContainer] / [WebViewState] and any host-app analytics,
 * attribution or location service.
 *
 * Implement this interface in your app (e.g. by wrapping `ZebpayWebViewBridgeImpl`) and pass the
 * instance to [rememberWebViewState] so the WebView can report permission grants and supply an
 * encrypted location string to the embedded web page.
 *
 * If no bridge is provided, a no-op default is used and the WebView still works — it just won't
 * fire analytics events or supply location data to JavaScript.
 *
 * ## Host-app integration example
 * ```kotlin
 * val myBridge = remember { MyAppWebViewBridge(context) }  // implements ZWebViewBridge
 *
 * val state = rememberWebViewState(
 *     bridge = myBridge,
 *     webJsInterface = myBridge to "Zebpay",  // add as JS interface if needed
 * )
 * WebViewContainer(state = state, ...)
 * ```
 */
interface ZWebViewBridge {

    /**
     * Called by [WebViewState] when the host grants a runtime permission.
     * The bridge should invoke `onPermissionGrant(name, encryptedLocation)` on the web page.
     */
    var onPermissionAlreadyGranted: ((permissionName: String) -> Unit)?

    /**
     * Called by [WebViewState] when the host requests a runtime permission from the OS.
     */
    var onRequestPermission: ((permissionName: String) -> Unit)?

    /**
     * Returns an encrypted representation of the device's current location, or an empty string
     * if location is unavailable or not granted.
     */
    fun getEncryptedLocation(): String

    /**
     * Start any location polling or related background work tied to the WebView lifecycle.
     * Called on [Lifecycle.Event.ON_RESUME] and when the WebView enters composition.
     */
    fun startLocationPolling()

    /**
     * Stop any location polling or related background work.
     * Called on [Lifecycle.Event.ON_PAUSE] and when the WebView leaves composition.
     */
    fun stopLocationPolling()
}

/**
 * Default no-op implementation used when no [ZWebViewBridge] is supplied.
 * The WebView works normally but without analytics or encrypted location support.
 */
internal class NoOpWebViewBridge : ZWebViewBridge {
    override var onPermissionAlreadyGranted: ((String) -> Unit)? = null
    override var onRequestPermission: ((String) -> Unit)? = null
    override fun getEncryptedLocation(): String = ""
    override fun startLocationPolling() {}
    override fun stopLocationPolling() {}
}
