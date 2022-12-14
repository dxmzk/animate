package com.step3.animate.modules.network

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
        val ENV: AppENV = AppENV.TEST // 环境
        private val hostMap: HashMap<String, String> = HashMap() // 请求域名

        init {
            // 生产环境 -prod
            hostMap["base-prod"] = "https://sp.bnq.com.cn/web/"
            hostMap["ws-prod"] = "https://auth.bnq.com.cn/"
            hostMap["account-prod"] = "https://auth.bnq.com.cn/web/"
            hostMap["oss-prod"] = "https://oss.bnq.com.cn/api/fileUpload/uploadImageFile/"
            // 测试环境 -test
            hostMap["base-test"] = "https://sp-test.bnq.com.cn/web/"
            hostMap["ws-test"] = "https://sp-test.bnq.com.cn/"
            hostMap["account-test"] = "https://auth-test.bnq.com.cn/web/"
            hostMap["oss-test"] = "https://oss-test.bnq.com.cn/api/fileUpload/uploadImageFile/"
        }

        // 获取host
        fun getBaseUrl(host: String = "base"): String {
            val env = if (ENV == AppENV.TEST) "test" else "prod"
            return hostMap["$host-$env"] ?: ""
        }
    }
}