package com.livestock.health

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val CAMERA_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView)

        setupWebView()
        loadApp()
        requestCameraPermission()
    }

    private fun setupWebView() {
        val settings = webView.settings

        // Enable JavaScript
        settings.javaScriptEnabled = true

        // Enable localStorage / DOM Storage
        settings.domStorageEnabled = true

        // Enable database storage
        settings.databaseEnabled = true

        // Allow file access
        settings.allowFileAccess = true
        settings.allowContentAccess = true

        // Allow mixed content (needed for camera)
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

        // Media / Camera
        settings.mediaPlaybackRequiresUserGesture = false

        // Zoom
        settings.setSupportZoom(true)
        settings.builtInZoomControls = false
        settings.displayZoomControls = false

        // Viewport
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true

        // Cache (offline support)
        settings.cacheMode = WebSettings.LOAD_DEFAULT

        // Set WebViewClient to handle navigation within the app
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false  // Let WebView handle all URLs
            }
        }

        // Set WebChromeClient for camera, alerts, etc.
        webView.webChromeClient = object : WebChromeClient() {

            // Handle camera permission requests from JavaScript
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.let {
                    it.grant(it.resources)
                }
            }

            // Handle JS alert()
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                result?.confirm()
                return true
            }

            // Handle JS confirm()
            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
                val builder = android.app.AlertDialog.Builder(this@MainActivity)
                builder.setMessage(message)
                builder.setPositiveButton("OK") { _, _ -> result?.confirm() }
                builder.setNegativeButton("キャンセル") { _, _ -> result?.cancel() }
                builder.show()
                return true
            }

            // Handle file chooser (for image upload)
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<android.net.Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                // For camera capture
                val intent = fileChooserParams?.createIntent()
                if (intent != null) {
                    try {
                        startActivityForResult(intent, 1)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                return true
            }
        }
    }

    private fun loadApp() {
        // Load the HTML file from assets (works 100% offline)
        webView.loadUrl("file:///android_asset/www/livestock_health_v5.html")
    }

    private fun requestCameraPermission() {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGranted.toTypedArray(), CAMERA_PERMISSION_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            // Reload after permission grant to activate camera features
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                webView.reload()
            }
        }
    }

    // Handle back button — navigate within WebView history
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
