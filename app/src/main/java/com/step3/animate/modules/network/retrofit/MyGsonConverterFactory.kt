package com.step3.animate.modules.network.retrofit

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class MyGsonConverterFactory(private val gson: Gson) : Converter.Factory() {
    companion object {
        fun create(): MyGsonConverterFactory {
            val gson = Gson()
            return MyGsonConverterFactory(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type?, annotations: Array<Annotation?>?, retrofit: Retrofit?
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonResponseBodyConverter(gson, adapter)
    }

    override fun requestBodyConverter (
        type: Type?,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody> {
        val adapter = gson.getAdapter(TypeToken.get(type))
        return GsonRequestBodyConverter(gson, adapter)
    }
}