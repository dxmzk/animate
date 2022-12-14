package com.step3.animate.modules.network

import com.step3.animate.modules.api.AccountApi
import com.step3.animate.modules.api.OrderApi
import com.step3.animate.modules.network.retrofit.MyGsonConverterFactory
import retrofit2.Retrofit

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
    val call = RetrofitServices().main().detail(1).enqueue(object : Callback<Any>{
        override fun onFailure(call: retrofit2.Call<Any>, t: Throwable) {}

        override fun onResponse(call: retrofit2.Call<Any>, response: retrofit2.Response<Any>) {
            if (response.isSuccessful) {
                for ((name, value) in response.headers()) {
                    println("$name: $value")
                }
                println(response.body().toString())
            }
            println(call.request().url)
        }
    })
 */
class RetrofitServices {
    private val builder: Retrofit.Builder = Retrofit.Builder()
        .client(Network.client())
        .addConverterFactory(MyGsonConverterFactory.create())

    init {
        account()
        home()
    }

    // 账号相关接口
    fun account(): AccountApi {
        val url = Config.getBaseUrl("account")
        val retrofit = builder.baseUrl(url)
            .build()
        return retrofit.create(AccountApi::class.java)
    }

    // 接口集1
    fun home(): OrderApi {
        val url = Config.getBaseUrl("base")
        val retrofit = builder.baseUrl(url)
            .build()
        return retrofit.create(OrderApi::class.java)
    }
}