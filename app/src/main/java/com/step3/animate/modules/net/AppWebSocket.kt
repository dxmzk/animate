package com.bnq.pda3.module.network

import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.decodeHex
import java.util.concurrent.TimeUnit


/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class AppWebSocket: WebSocketListener() {
    private fun run() {
        val client = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()
        val request = Request.Builder()
            .url("ws://echo.websocket.org")
            .build()
        client.newWebSocket(request, this)

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher.executorService.shutdown()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        webSocket.send("Hello...")
        webSocket.send("...World!")
        webSocket.send("deadbeef".decodeHex())
        webSocket.close(1000, "Goodbye, World!")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        println("MESSAGE: $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        println("MESSAGE: " + bytes.hex())
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(1000, null)
        println("CLOSE: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        t.printStackTrace()
    }

}