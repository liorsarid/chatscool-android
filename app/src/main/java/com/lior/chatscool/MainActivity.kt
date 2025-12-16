package com.lior.chatscool

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Edge-to-edge, אבל אנחנו מנהלים Insets בעצמנו
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // צבע ל-Status Bar (וגם כדי שלא יהיה שקוף/לבן)
        window.statusBarColor = getColor(R.color.status_bar_color)

        // אייקונים לבנים ב-Status Bar
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false

        setContentView(R.layout.activity_main)

        val root: View = findViewById(R.id.root)
        webView = findViewById(R.id.webView)

        // Padding אוטומטי לפי Status Bar + Navigation Bar
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val statusBars = insets.getInsets(Type.statusBars())
            val navBars = insets.getInsets(Type.navigationBars())
            v.setPadding(0, statusBars.top, 0, navBars.bottom)
            WindowInsetsCompat.CONSUMED
        }

        // WebView setup
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()

        val s = webView.settings
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.databaseEnabled = true
        s.cacheMode = WebSettings.LOAD_DEFAULT
        s.useWideViewPort = true
        s.loadWithOverviewMode = true
        s.mediaPlaybackRequiresUserGesture = false
        s.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        webView.loadUrl("https://www.chats.cool")
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (this::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
