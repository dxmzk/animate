package com.bnq.pda3.module.network.retrofit

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.Buffer
import retrofit2.Converter
import java.io.OutputStreamWriter
import java.nio.charset.Charset

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class GsonRequestBodyConverter<T>(private val gson: Gson, private val typeAdapter: TypeAdapter<T>) :
    Converter<T, RequestBody> {
    private val MEDIA_TYPE: MediaType = "application/json; charset=UTF-8".toMediaType()
    private val UTF_8 = Charset.forName("UTF-8")

    override fun convert(value: T): RequestBody {
        val buffer = Buffer()
        val writer = OutputStreamWriter(buffer.outputStream(), UTF_8)
        val jsonWriter = gson.newJsonWriter(writer)
        typeAdapter.write(jsonWriter, value)
        jsonWriter.close()
        return buffer.readByteString().toRequestBody(MEDIA_TYPE)
    }

}