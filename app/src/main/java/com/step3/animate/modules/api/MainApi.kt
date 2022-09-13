package com.bnq.pda3.module.api

import retrofit2.Call
import retrofit2.http.*

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
interface MainApi {

    @FormUrlEncoded
    @POST("customOrder/list")
    fun list(@Field("curPage") cur: Int): ArrayList<Call<Any>>

    @GET("customOrder/detail")
    fun detail(@Query("id") id: Int): Call<Any>
}