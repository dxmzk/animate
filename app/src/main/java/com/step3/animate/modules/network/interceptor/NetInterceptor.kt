package com.step3.animate.modules.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 *   val cache = Cache(cacheDir, 1024 * 1024)
 *   cache.evictAll()
 *   new OkHttpClient.Builder()
        .cache(cache)
        .build();
 */
class NetInterceptor: Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
//
//        if (myConfig.isInvalid()) {
//            return Response.Builder()
//                .request(chain.request())
//                .protocol(Protocol.HTTP_1_1)
//                .code(400)
//                .message("client config invalid")
//                .body("client config invalid".toResponseBody(null))
//                .build()
//        }
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header("Cache-Control", "max-age=60")
            .build()
    }
}