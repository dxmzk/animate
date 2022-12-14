package com.step3.animate.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec


/**
 * Author: Meng
 * Date: 2022/12/14
 * Desc:
 */
class DesUtils {

    private val DES = "DES" // 加密方式 DES

    // 填充模式 DES/CBC/PKCS5Padding
    private val PKCS_5_PADDING = "DES/CBC/PKCS5Padding"

    val CHARSET_UTF8 = "UTF-8" // 字符编码 UTF-8

    /**
     * 使用指定的key对字符串加密
     *
     * @param data 待加密字符串
     * @param key 密钥
     * @return 加密后的数据
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun encrypt(data: String, key: String): String? {
        var bt: ByteArray? = ByteArray(0)
        var encodeData: String? = ""
        try {
            bt = encrypt(
                data.toByteArray(charset(CHARSET_UTF8)),
                key.toByteArray(charset(CHARSET_UTF8))
            )
            //字节转化为16进制字符串
            encodeData = String(Base64.getEncoder().encode(bt)) //byteToHexString(bt);
        } catch (e: Exception) {
            //logger.error("异常",e);
        }
        return encodeData
    }

    @Throws(Exception::class)
    private fun encrypt(data: ByteArray, key: ByteArray): ByteArray? {
//        val sr = SecureRandom()
        val dks = DESKeySpec(key)
        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(DES)
        val securekey: SecretKey = keyFactory.generateSecret(dks)
        val cipher: Cipher = Cipher.getInstance(PKCS_5_PADDING)
        cipher.init(Cipher.ENCRYPT_MODE, securekey, IvParameterSpec(dks.getKey()))
        return cipher.doFinal(data)
    }

    /**
     *
     * 将byte数组转换成16进制字符串
     *
     * @param bytes byte数组
     * @return 16进制字符串
     */
    fun byteToHexString(bytes: ByteArray): String? {
        val sb = StringBuffer(bytes.size)
        var sTemp: String
        for (i in bytes.indices) {
            sTemp = Integer.toHexString(0xFF and bytes[i].toInt())
            if (sTemp.length < 2) {
                sb.append(0)
            }
            sb.append(sTemp.uppercase(Locale.getDefault()))
        }
        return sb.toString()
    }

    /**
     *
     * @param data
     * @param key
     * @return
     */
    fun decrypt(data: String?, key: String): String? {
        var decryptString = ""
        try {
            val bt = decrypt(hexStringToBytes(data), key.toByteArray(charset(CHARSET_UTF8)))
            decryptString = String(bt, charset(CHARSET_UTF8))
        } catch (e: Exception) {
            //logger.error("异常",e);
        }
        return decryptString
    }

    /**
     * 解密
     * @param data byte数组
     * @param key 加密key
     * @return 解密后的byte数组
     * @throws Exception 异常
     */
    @Throws(Exception::class)
    private fun decrypt(data: ByteArray, key: ByteArray): ByteArray {
//        val sr = SecureRandom()
        val dks = DESKeySpec(key)
        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(DES)
        val securekey: SecretKey = keyFactory.generateSecret(dks)

        // Cipher对象实际完成解密操作
        val cipher: Cipher = Cipher.getInstance(PKCS_5_PADDING)
        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, IvParameterSpec(dks.getKey()))
        return cipher.doFinal(data)
    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str 待转字符串
     * @return 转后的byte数组
     */
    fun hexStringToBytes(str: String?): ByteArray {
        if (str == null || str.trim { it <= ' ' } == "") {
            return ByteArray(0)
        }
        val bytes = ByteArray(str.length / 2)
        for (i in 0 until str.length / 2) {
            val subStr = str.substring(i * 2, i * 2 + 2)
            bytes[i] = subStr.toInt(16).toByte()
        }
        return bytes
    }

}