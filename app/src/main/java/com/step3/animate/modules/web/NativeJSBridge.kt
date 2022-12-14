package com.step3.animate.modules.web

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 * Native.showToast(toast);
 */
class NativeJSBridge(private val context: Context) {

    /**
     * 消息toast
     * msg：消息； mode: 0-短；1-长
     */
    @JavascriptInterface
    fun toast(msg: String, mode: Int = Toast.LENGTH_SHORT) {
        if (msg.isNotEmpty()) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            Log.i("WebBridge", msg)
        }
    }

    @JavascriptInterface
    fun deviceInfo() {

    }
}