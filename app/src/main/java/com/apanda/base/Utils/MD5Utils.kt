package com.apanda.base.Utils

import java.io.File
import java.io.FileInputStream
import java.security.DigestInputStream
import java.security.MessageDigest

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/10/11
 * 描    述：MD5加密工具类
 * 修订历史：
 * ================================================
 */
object MD5Utils {

    /**
     * 获取字符串的 MD5
     */
    fun encode(str: String): String {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(str.toByteArray(charset("UTF-8")))
            val messageDigest = md5.digest()
            val hexString = StringBuilder()
            for (b in messageDigest) {
                hexString.append(String.format("%02X", b))
            }
            return hexString.toString().toLowerCase()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    /**
     * 获取文件的 MD5
     */
    fun encode(file: File): String? {
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            val inputStream = FileInputStream(file)
            val digestInputStream = DigestInputStream(inputStream, messageDigest)
            //必须把文件读取完毕才能拿到md5
            val buffer = ByteArray(4096)
            while (digestInputStream.read(buffer) > -1) {
            }
            val digest = digestInputStream.messageDigest
            digestInputStream.close()
            val md5 = digest.digest()
            val sb = StringBuilder()
            for (b in md5) {
                sb.append(String.format("%02X", b))
            }
            return sb.toString().toLowerCase()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }
}
