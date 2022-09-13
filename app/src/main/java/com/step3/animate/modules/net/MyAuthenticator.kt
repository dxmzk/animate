package com.bnq.pda3.module.network

import okhttp3.*
import okio.IOException

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */
class MyAuthenticator: Authenticator {
    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") != null) {
            return null // Give up, we've already attempted to authenticate.
        }

        println("Authenticating for response: $response")
        println("Challenges: ${response.challenges()}")
        val credential = Credentials.basic("your name", "password")
        return response.request.newBuilder()
            .header("Authorization", credential)
            .build()
    }
}