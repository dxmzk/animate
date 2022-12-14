package com.step3.animate.modules.network

import android.util.Log
import androidx.annotation.Nullable
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
enum class SocketStatus {
    Open, //
    Closing, //
    Closed, //
    Canceled, //
    Connecting, //
}

class AppWebSocket : WebSocketListener() {
    private val TAG = "AppWebSocket"
    private val wsUrl: String = Config.getBaseUrl("ws")
    private var webSocket: WebSocket?
    private var status: SocketStatus
    private val client: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(50, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    companion object {
        fun start() {
            AppWebSocket();
        }
    }

    init {
        //构造request对象
        val request = Request.Builder()
            .url(wsUrl)
            .build()
        webSocket = client.newWebSocket(request, this)
        status = SocketStatus.Connecting
    }

    fun connect() {
        if (webSocket != null) {
            webSocket = client.newWebSocket(webSocket!!.request(), this)
        }
    }

    fun send(text: String, to: String = "") {
        var text = text
        if (webSocket != null && status === SocketStatus.Open) {
            text = formatMessage(text)
            webSocket!!.send(text)
            Log.i(TAG, "send =======> $text")
        }
    }

    fun cancel() {
        if (webSocket != null) {
            webSocket!!.cancel()
        }
    }

    fun close() {
        if (webSocket != null) {
            webSocket!!.close(1000, null)
        }
    }

    private fun formatMessage(text: String): String {
        val obj = JSONObject()
        obj.put("event", "notify")
        obj.put("data", text)
        return obj.toString()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        status = SocketStatus.Open
        Log.i(TAG, "AppSocket ===========> open")
        send("你个傻哈哈", "user")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Log.i(TAG, "onMessage $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.i(TAG, "onMessage ${bytes.base64()}")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, @Nullable response: Response?) {
        super.onFailure(webSocket, t, response)
        status = SocketStatus.Canceled
        Log.i(TAG, "onFailure")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        status = SocketStatus.Closing
        Log.i(TAG, "onClosing")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        status = SocketStatus.Closed
        Log.i(TAG, "onClosed")
        connect()
    }
}