package com.zebpay.ui.v3.components.organisms.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.CookieManager
import android.webkit.GeolocationPermissions
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.webkit.WebResourceErrorCompat
import androidx.webkit.WebViewClientCompat
import com.zebpay.ui.v3.BuildConfig
import com.zebpay.ui.v3.R
import timber.log.Timber


sealed interface ProgressState {
    data object Loading : ProgressState
    data object Success : ProgressState
    data object Error : ProgressState
    data object Idle : ProgressState
}

@Composable
fun rememberWebViewState(
    context: Context = LocalContext.current,
    blockingUrl: String? = null,
    webJsInterface: Pair<Any, String>? = null,
    bridge: ZWebViewBridge? = null,
    analyticsWebAppJsInterfaceImpl: Any? = null, // Deprecated: pass bridge instead; ignored
    onConsoleMessage: ((String, String) -> Unit)? = null,
    disableParentTouch: Boolean = false,
): WebViewState {
    return remember {
        WebViewState(
            context = context,
            blockingURL = blockingUrl,
            webJsInterface = webJsInterface,
            bridge = bridge,
            onConsoleMessage = onConsoleMessage,
            disableParentTouch = disableParentTouch,
        )
    }
}

class WebViewState public constructor(
    context: Context,
    blockingURL: String?,
    webJsInterface: Pair<Any, String>?,
    /**
     * Optional bridge that provides analytics events, encrypted location, and permission
     * coordination. When null, a no-op bridge is used and the WebView still renders correctly
     * but without analytics or location data.
     *
     * @see ZWebViewBridge
     */
    bridge: ZWebViewBridge? = null,
    analyticsWebAppJsInterfaceImpl: Any? = null, // Deprecated; ignored
    onConsoleMessage: ((String, String) -> Unit)? = null,
    val disableParentTouch: Boolean = false,
) {

    private var blockingUrl: String? = blockingURL
    val cookieManager: CookieManager = CookieManager.getInstance()
    var fileChooserCallback: ValueCallback<Array<Uri>>? = null

    internal var progress by mutableStateOf<ProgressState>(ProgressState.Loading)
    private var hasError = false

    /** Bridge for analytics, location, and permission coordination. */
    val bridge: ZWebViewBridge = bridge ?: NoOpWebViewBridge()

    private val chromeClient = object : WebChromeClient() {

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
            onConsoleMessage?.invoke("webview", "onConsoleMessage: ${consoleMessage?.message()}")
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?,
        ): Boolean {
            fileChooserCallback?.onReceiveValue(null)
            fileChooserCallback = filePathCallback
            onRequestFileChooser?.invoke()
            return true
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String,
            callback: GeolocationPermissions.Callback,
        ) {
            val granted = context.hasLocationPermission
            callback.invoke(origin, granted, false)
            if (!granted) {
                onRequestLocationPermission?.invoke()
            }
        }
    }

    private val client = object : WebViewClientCompat() {

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?,
        ) {
            super.onReceivedSslError(view, handler, error)
            onConsoleMessage?.invoke("webview", "onReceivedSslError: $error")
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            onConsoleMessage?.invoke("webview", "onPageStarted: $url")
            progress = ProgressState.Loading
            hasError = false
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            onConsoleMessage?.invoke("webview", "onPageFinished: $url")
            view?.evaluateJavascript(
                """
            (function() {
                document.addEventListener("keydown", function(e) {
                    if (e.key === "Enter") {
                        document.activeElement.blur();
                        if (typeof Android !== "undefined" && Android.onDonePressed) {
                            Android.onDonePressed();
                        }
                    }
                });
            })();
        """.trimIndent(),
                null,
            )
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if (!hasError) {
                        progress = ProgressState.Success
                    }
                },
                500,
            )
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceErrorCompat,
        ) {
            super.onReceivedError(view, request, error)
            onConsoleMessage?.invoke(
                "webview",
                "onReceivedError\n URL : ${request.url} \n Error : ${error.description}",
            )
            if (request.isForMainFrame) {
                onConsoleMessage?.invoke("webview", "onReceivedError : Going in error state")
                hasError = true
                progress = ProgressState.Error
            }
        }

        override fun onReceivedHttpError(
            view: WebView,
            request: WebResourceRequest,
            errorResponse: WebResourceResponse,
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            onConsoleMessage?.invoke("webview", "onReceivedHttpError: $errorResponse")
            if (request.isForMainFrame) {
                onConsoleMessage?.invoke("webview", "onReceivedHttpError : Going in error state")
                hasError = true
                progress = ProgressState.Error
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return if (request.url.toString()
                    .contains(blockingUrl.orEmpty()) && blockingUrl != null
            )
                true
            else
                super.shouldOverrideUrlLoading(view, request)
        }
    }

    lateinit var fileChooserLauncher: (Intent) -> Unit
    var onRequestFileChooser: (() -> Unit)? = null
    var onRequestLocationPermission: (() -> Unit)? = null
    var permissionRequestHandler: ((String) -> Unit)? = null
    var permissionAlreadyGrantedHandler: ((String) -> Unit)? = null

    init {
        this.bridge.onRequestPermission = { name -> permissionRequestHandler?.invoke(name) }
        this.bridge.onPermissionAlreadyGranted = { name -> permissionAlreadyGrantedHandler?.invoke(name) }
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    internal var webView = WebView(context).apply {
        setLayerType(View.LAYER_TYPE_HARDWARE, null)
        settings.apply {
            javaScriptEnabled = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            builtInZoomControls = true
            pluginState = WebSettings.PluginState.ON
            allowFileAccess = true
            allowContentAccess = true
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            setGeolocationEnabled(true)
            displayZoomControls = false
            setSupportZoom(false)
            clearCache(false)
            webViewClient = client
            webChromeClient = chromeClient

            // Optional custom JS interface (e.g. pass your ZebpayWebViewBridgeImpl here)
            webJsInterface?.let { (js, name) ->
                addJavascriptInterface(js, name)
            }

            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
        }
    }

    fun loadUrl(url: String, cookies: Map<String, String>? = null) {
        val headers: MutableMap<String, String> = HashMap()
        headers["Pragma"] = "no-cache"
        headers["Cache-Control"] = "no-cache"
        webView.apply {
            setCookies(url, cookies.orEmpty())
            loadUrl(url, headers)
        }
    }

    fun loadUrlWithCookieList(url: String, cookiesList: List<String> = emptyList()) {
        CookieManager.getInstance().apply {
            if (cookiesList.isNotEmpty()) {
                cookiesList.forEach {
                    setCookie(url, it)
                }
            } else {
                setCookie(url, "device_platform=android")
            }
        }
        webView.loadUrl(url)
    }

    fun loadUrl(url: String, authToken: String) {
        CookieManager.getInstance().apply {
            setCookie(url, "device_platform=android")
            setCookie(url, "auth_token=$authToken")
        }
        webView.loadUrl(url)
    }

    fun blockingUrl(url: String) {
        blockingUrl = url
    }

    fun evaluateJavascript(js: String, resultCallback: ValueCallback<String>? = null) {
        webView.evaluateJavascript("javascript:$js", resultCallback)
    }

    private fun setCookies(
        url: String, cookies: Map<String, String>,
        onConsoleMessage: ((String, String) -> Unit)? = null,
    ) {
        webView.apply {
            cookieManager.apply {
                removeAllCookies(null)
                flush()
                setAcceptThirdPartyCookies(webView, true)
                setAcceptCookie(true)
                cookies.entries.forEach { (key, value) ->
                    setCookie(url, "$key=$value") { success ->
                        onConsoleMessage?.invoke(
                            "webview",
                            "Set cookie: $key=$value success: $success",
                        )
                    }
                }
                flush()
            }
        }
    }
}

@SuppressLint("ClickableViewAccessibility")
@Composable
private fun WebViewComponent(
    onLoading: @Composable BoxScope.() -> Unit,
    onError: @Composable BoxScope.() -> Unit,
    state: WebViewState,
    modifier: Modifier = Modifier,
) {

    val alpha by animateFloatAsState(
        targetValue = if (state.progress is ProgressState.Success) 1f else 0f,
        label = "webView_alpha",
    )

    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = state.progress is ProgressState.Loading,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut(),
        ) {
            onLoading()
        }
        AnimatedVisibility(
            visible = state.progress is ProgressState.Error,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
        ) {
            onError()
        }
        AndroidView(
            factory = { context ->
                state.webView.apply {
                    // Detach from any existing parent to avoid "The specified child already has a parent" crashes
                    (parent as? ViewGroup)?.removeView(this)
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                    )
                    if (state.disableParentTouch) {
                        setOnTouchListener { v, event ->
                            v.parent.requestDisallowInterceptTouchEvent(state.disableParentTouch)
                            state.disableParentTouch.not()
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha),
        )
    }
}

@Composable
fun WebViewContainer(
    onLoading: @Composable BoxScope.() -> Unit,
    onError: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    state: WebViewState = rememberWebViewState(
        disableParentTouch = false,
    ),
) {

    var showChooser by remember { mutableStateOf(false) }
    var showLocationPermissionRequest by remember { mutableStateOf(false) }
    var showPermissionRequest by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current
    var cameraImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        state.onRequestFileChooser = {
            showChooser = true
        }
        state.onRequestLocationPermission = {
            showLocationPermissionRequest = true
        }
        state.permissionRequestHandler = {
            showPermissionRequest = it
        }
        state.permissionAlreadyGrantedHandler = { permissionName ->
            val escaped = permissionName.replace("'", "\\'")
            val encLoc = if (permissionName.uppercase() == "LOCATION") state.bridge.getEncryptedLocation() else ""
            Timber.d("getEncryptedLocation escaped :- $escaped encLoc:- $encLoc")
            state.webView.post {
                state.webView.evaluateJavascript(
                    "(function(){ if (typeof onPermissionGrant === 'function') { onPermissionGrant('$escaped', '$encLoc'); } })();",
                    null,
                )
            }
        }
    }

    // Start location polling when WebView enters composition
    DisposableEffect(Unit) {
        state.bridge.startLocationPolling()
        onDispose {
            state.bridge.stopLocationPolling()
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    state.bridge.startLocationPolling()
                    state.webView.evaluateJavascript(
                        "window.dispatchEvent(new Event('permissionMayHaveChanged'))",
                        null,
                    )
                }
                Lifecycle.Event.ON_PAUSE -> {
                    state.bridge.stopLocationPolling()
                }
                else -> { /* no-op */ }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val uris = when {
                data?.clipData != null -> {
                    Array(data.clipData!!.itemCount) { i -> data.clipData!!.getItemAt(i).uri }
                }

                data?.data != null -> arrayOf(data.data!!)
                cameraImageUri != null -> arrayOf(cameraImageUri!!)
                else -> null
            }

            state.fileChooserCallback?.onReceiveValue(uris)
            state.fileChooserCallback = null
            cameraImageUri = null
        }

    if (showLocationPermissionRequest) {
        RequestLocationPermission { granted ->
            showLocationPermissionRequest = false
            if (granted) {
                state.bridge.startLocationPolling()
                state.webView.evaluateJavascript(
                    "window.dispatchEvent(new Event('permissionMayHaveChanged'))",
                    null,
                )
            }
        }
    }

    showPermissionRequest?.let { permissionName ->
        RequestPermission(permissionName = permissionName) { granted ->
            showPermissionRequest = null
            if (granted) {
                if (permissionName.uppercase() == "LOCATION") {
                    state.bridge.startLocationPolling()
                }
                val escaped = permissionName.replace("'", "\\'")
                val encLoc = if (permissionName.uppercase() == "LOCATION") state.bridge.getEncryptedLocation() else ""
                Timber.d("getEncryptedLocation escaped :- $escaped encLoc:- $encLoc")
                state.webView.evaluateJavascript(
                    "(function(){ if (typeof onPermissionGrant === 'function') { onPermissionGrant('$escaped', '$encLoc'); } })();",
                    null,
                )
                state.webView.evaluateJavascript(
                    "window.dispatchEvent(new Event('permissionMayHaveChanged'))",
                    null,
                )
            } else if (activity != null && !activity.shouldShowPermissionRationaleFor(permissionName)) {
                Toast.makeText(
                    context,
                    context.getString(R.string.c_permission_enable_in_settings),
                    Toast.LENGTH_LONG,
                ).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
            }
        }
    }

    if (showChooser) {
        RequestCameraPermission { granted ->
            showChooser = false
            if (granted) {
                cameraImageUri = createImageUri(context)

                val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
                    putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri)
                }

                val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    type = "image/*"
                }

                val chooserIntent = Intent(Intent.ACTION_CHOOSER).apply {
                    putExtra(Intent.EXTRA_INTENT, galleryIntent)
                    putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(captureIntent))
                }

                launcher.launch(chooserIntent)
            } else {
                state.fileChooserCallback?.onReceiveValue(null)
                state.fileChooserCallback = null
            }
        }
    }

    LaunchedEffect(Unit) {
        state.fileChooserLauncher = { intent -> launcher.launch(intent) }
    }

    WebViewComponent(
        onLoading = onLoading,
        onError = onError,
        state = state,
        modifier = modifier,
    )
}
