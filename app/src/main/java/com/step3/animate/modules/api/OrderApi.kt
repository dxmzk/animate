package com.step3.animate.modules.api

import retrofit2.Call
import retrofit2.http.*

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
interface OrderApi {

    @FormUrlEncoded
    @POST("customOrder/list")
    fun list(@Field("curPage") cur: Int): ArrayList<Call<Any>>

    @Headers(*["Token3: 131231","Token: 131231"])
    @GET("customOrder/detail")
    fun detail(@Query("id") id: Int): Call<Any>

    @FormUrlEncoded
    @Headers(*["Token3: 131231","Token: 131231"])
    @POST("customOrder/info")
    fun info(@Field("code") code: Int, @Field("user") user: Int): Call<Any>
}