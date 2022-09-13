package com.bnq.pda3.module.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
interface AccountApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<Any>>
}