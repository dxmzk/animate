package com.step3.animate.modules.web

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient

/**
 * Author: Meng
 * Date: 2022/09/14
 * Desc:
 */
class MyWebChromeClient: WebChromeClient() {

    override fun onConsoleMessage(message: ConsoleMessage): Boolean {
        Log.d("MyWebChromeClient", "Line: ${message.lineNumber()}; ${message.message()}")
        return true
    }
}