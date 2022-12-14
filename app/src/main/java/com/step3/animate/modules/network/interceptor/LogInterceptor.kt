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
    private val TAG = "LogInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        val t1 = System.nanoTime()
        val request = chain.request()
        Log.i(TAG, String.format(">>>>>>>>> Request: %s on %s%n%s", request.url, chain.connection(), request.headers))
        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Log.i(TAG, String.format(">>>>>>>>> Response: %s Time %.1fms%n", request.url, (t2 - t1) / 1e6))
        return response
    }
}