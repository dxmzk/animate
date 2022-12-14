package com.step3.animate.modules.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.String

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class LogInterceptor: Interceptor {
    private val _TAG = "LogInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        val t1 = System.nanoTime()
        val request = chain.request()
//        Log.i(TAG, String.format("> >>>>>> Request: %s on %s%n%s", request.url, chain.connection(), request.headers))
        Log.i(_TAG, String.format(">>> Request: Method: %s, url: %s, header: %s", request.method, request.url, request.headers))
        if(request.method != "GET") {
            Log.i(_TAG, String.format(">>> Request: body: %s", request.body))
        }
        val response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(_TAG, String.format(">>> Response: %s Time: %.1fms%n", request.url, (t2 - t1) / 1e6))
        return response
    }
}