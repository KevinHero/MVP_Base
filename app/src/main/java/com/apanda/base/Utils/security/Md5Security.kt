package com.apanda.base.Utils.security

import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * MD5加密类
 */
object Md5Security {
    /**
     * md5加密
     * @param info
     * *
     * @return
     */
    fun getMD5(info: String): String {
        try {
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(info.toByteArray(charset("UTF-8")))
            val encryption = md5.digest()

            val strBuf = StringBuffer()
            for (i in encryption.indices) {
                if (Integer.toHexString(0xff and encryption[i]).length == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff and encryption[i]))
                } else {
                    strBuf.append(Integer.toHexString(0xff and encryption[i]))
                }
            }

            return strBuf.toString()
        } catch (e: NoSuchAlgorithmException) {
            return ""
        } catch (e: UnsupportedEncodingException) {
            return ""
        }

    }
}
