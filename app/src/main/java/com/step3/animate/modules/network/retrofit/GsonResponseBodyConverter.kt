package com.step3.animate.modules.network.retrofit

import com.google.gson.Gson
import com.google.gson.JsonIOException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonToken
import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class GsonResponseBodyConverter<T>(private val gson: Gson, private val typeAdapter: TypeAdapter<T>) :
    Converter<ResponseBody, T> {

    override fun convert(value: ResponseBody): T {
        val jsonReader = gson.newJsonReader(value.charStream())
        return value.use { value ->
            val result: T = typeAdapter.read(jsonReader)
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw JsonIOException("JSON document was not fully consumed.")
            }
            result
        }
    }

}