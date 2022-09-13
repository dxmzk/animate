package com.bnq.pda3.module.network

/**
 * Author: Meng
 * Date: 2022/09/13
 * Desc:
 */

enum class AppENV {
    PROD, TEST
}

class Config {
    companion object {
        val env: AppENV = AppENV.TEST

        private val prod: HashMap<String, String> = HashMap()
        private val test: HashMap<String, String> = HashMap()

        init {
            prod["base"] = "https://sp.bnq.com.cn/web/"
            prod["account"] = "https://auth.bnq.com.cn/web/"
            prod["oss"] = "https://oss.bnq.com.cn/api/fileUpload/uploadImageFile/"

            test["base"] = "https://sp-test.bnq.com.cn/web/"
            test["account"] = "https://auth-test.bnq.com.cn/web/"
            test["oss"] = "https://oss-test.bnq.com.cn/api/fileUpload/uploadImageFile/"
        }

        fun getUrl(host: String = "base"): String? {
            val envMap = if (env == AppENV.PROD) prod else test
            return envMap[host]
        }
    }
}