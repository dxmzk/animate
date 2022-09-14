package com.step3.animate.modules.web

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient

/**
 * Author: Meng
 * Date: 2022/09/14
 * Desc:
 */
class MyWebViewClient: WebViewClient() {
    private val TAG = "MyWebViewClient"
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        val host = Uri.parse(url).host
        Log.i(TAG, host.toString())
        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
//            startActivity(this)
        }
        return true
    }

}