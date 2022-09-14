package com.step3.animate.ui.activity

import android.content.res.Configuration
import android.os.Bundle
import android.util.Base64
import android.view.KeyEvent
import android.webkit.WebView
import android.widget.Toast
import com.step3.animate.R
import com.step3.animate.modules.base.AppActivity
import com.step3.animate.modules.web.MyWebChromeClient
import com.step3.animate.modules.web.MyWebViewClient
import com.step3.animate.modules.web.NativeJSBridge

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 * chrome://inspect/#devices
 */
class WebActivity: AppActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_web)

        initView()
    }

    fun initView() {
        webView = findViewById<WebView>(R.id.app_web_view)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(NativeJSBridge(applicationContext), "Native")
        webView.webViewClient = MyWebViewClient()
        webView.webChromeClient = MyWebChromeClient()

        webView.loadUrl("http://192.168.253.67:8091")

    }

    fun html() {
        val unencodedHtml =
            "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;"
        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        webView.loadData(encodedHtml, "text/html", "base64")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}