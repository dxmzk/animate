package com.step3.animate.modules.net

import com.bnq.pda3.module.network.Config
import com.bnq.pda3.module.network.Network
import com.step3.animate.modules.api.*
import com.step3.animate.modules.network.retrofit.MyGsonConverterFactory
import retrofit2.Retrofit

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class RetrofitServices {
    private val builder: Retrofit.Builder

    init {
        builder = Retrofit.Builder()
            .client(Network.client())
            .addConverterFactory(MyGsonConverterFactory.create())

        create()
    }

    private fun create() {
        account()
        main()

    }

    fun account(): AccountApi {
        val url = Config.getUrl("account")
        val retrofit = builder.baseUrl(url)
            .build()
        return retrofit.create(AccountApi::class.java)
    }

    /**
    val call = RetrofitServices().main().detail(1).enqueue(object : Callback<Any>{

    override fun onFailure(call: retrofit2.Call<Any>, t: Throwable) {
    t.printStackTrace()
    }

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
    fun main(): MainApi {
        val url = Config.getUrl("base")
        val retrofit = builder.baseUrl(url)
            .build()
        return retrofit.create(MainApi::class.java)
    }
}