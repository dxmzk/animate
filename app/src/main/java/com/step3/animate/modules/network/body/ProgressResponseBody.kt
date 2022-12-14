package com.step3.animate.modules.network.body

import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*


/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
OkHttpClient client = new OkHttpClient.Builder()
    .addNetworkInterceptor(chain -> {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
            .build();
    })
    .build();
 */
class ProgressResponseBody(val responseBody: ResponseBody, val progressListener: ProgressListener) :
    ResponseBody() {
    private lateinit var bufferedSource: BufferedSource

    override fun contentLength(): Long {
        return responseBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return responseBody.contentType()
    }

    override fun source(): BufferedSource {
        if (bufferedSource == null) {
            bufferedSource = source(responseBody.source()).buffer()
        }
        return bufferedSource;
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead = sink.let { super.read(it, byteCount) }
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                progressListener.update(totalBytesRead, responseBody.contentLength(),bytesRead == -1L)
                return bytesRead
            }
        }
    }

    interface ProgressListener {
        fun update(bytesRead: Long, contentLength: Long, done: Boolean)
    }
}