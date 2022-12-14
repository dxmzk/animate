package com.step3.animate.modules.network

import com.step3.animate.modules.network.interceptor.LogInterceptor
import com.step3.animate.modules.network.interceptor.NetInterceptor
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Author: Meng
 * Date: 2022/09/05
 * Desc:
 * https://github.com/square/okhttp/tree/master/samples/guide/src/main/java/okhttp3/recipes
 */
class Network {

    companion object {
        private val MEDIA_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()
        private val MEDIA_PNG = "image/png".toMediaType()
        private lateinit var client: OkHttpClient

        fun client(): OkHttpClient {
            return create()
        }

        private fun create(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .callTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(NetInterceptor())
                .addInterceptor(LogInterceptor())
                .build()
        }
    }

    fun requestDemo() {
        request("base", "", "")
    }

    fun request(host: String, url: String, method: String) {

        val request = when (method.lowercase()) {
            "post" -> post(host, url)
            "file" -> file()
            else -> get(host, url)
        }
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    for ((name, value) in response.headers) {
                        println("$name: $value")
                    }
                    println(response.body!!.string())
                }
            }
        })
    }

    private fun get(host: String, url: String): Request {
        val path = Config.getBaseUrl(host) + url
        return Request.Builder()
            .url(path)
            .header("User-Agent", "OkHttp Headers.java")
            .addHeader("Accept", "application/json; q=0.5")
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()


    }

    private fun post(host: String, url: String): Request {
        val body = FormBody.Builder()
            .add("search", "Jurassic Park")
            .build()
        val path = Config.getBaseUrl(host) + url
        return Request.Builder()
            .url(path)
            .post(body)
            .build()
    }

    private fun file(): Request {
        val file = File("README.md")

        return Request.Builder()
            .url("https://api.github.com/markdown/raw")
            .post(file.asRequestBody(MEDIA_MARKDOWN))
            .build()
    }

    private fun image() {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("title", "Square Logo")
            .addFormDataPart(
                "image", "logo-square.png",
                File("docs/images/logo-square.png").asRequestBody(MEDIA_PNG)
            )
            .build()

        val request = Request.Builder()
            .header("Authorization", "Client-ID IMGUR_CLIENT_ID")
            .url("https://api.imgur.com/3/image")
            .post(requestBody)
            .build()
    }
}