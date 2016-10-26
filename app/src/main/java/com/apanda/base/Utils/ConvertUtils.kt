package com.apanda.base.Utils

import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/13
 * desc  : 转换相关工具类
 *
 */
object ConvertUtils {

    internal val hexDigits = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    /**
     * 每1个byte转为2个hex字符
     *
     * 例如：
     * bytes2HexString(new byte[] { 0, (byte) 0xa8 }) returns 00A8

     * @param src byte数组
     * *
     * @return 16进制大写字符串
     */
    fun bytes2HexString(src: ByteArray): String {
        val res = CharArray(src.size shl 1)
        var i = 0
        var j = 0
        while (i < src.size) {
            res[j++] = hexDigits[src[i].ushr(4) and 0x0f]
            res[j++] = hexDigits[src[i] and 0x0f]
            i++
        }
        return String(res)
    }

    /**
     * 每2个hex字符转为1个byte
     *
     * 例如：
     * hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }

     * @param hexString 十六进制字符串
     * *
     * @return byte数组
     */
    fun hexString2Bytes(hexString: String): ByteArray {
        val len = hexString.length
        if (len % 2 != 0) {
            throw IllegalArgumentException("长度不是偶数")
        }
        val hexBytes = hexString.toUpperCase().toCharArray()
        val res = ByteArray(len.ushr(1))
        var i = 0
        while (i < len) {
            res[i shr 1] = (hex2Dec(hexBytes[i]) shl 4 or hex2Dec(hexBytes[i + 1])).toByte()
            i += 2
        }
        return res
    }

    /**
     * 单个hex字符转为10进制

     * @param hexChar hex单个字节
     * *
     * @return 0..15
     */
    private fun hex2Dec(hexChar: Char): Int {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0'
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10
        } else {
            throw IllegalArgumentException()
        }
    }

    /**
     * charArr转byteArr

     * @param chars 待转的char数组
     * *
     * @return byte数组
     */
    fun chars2Bytes(chars: CharArray): ByteArray {
        val len = chars.size
        val bytes = ByteArray(len)
        for (i in 0..len - 1) {
            bytes[i] = chars[i].toByte()
        }
        return bytes
    }

    /**
     * byteArr转charArr

     * @param bytes 待转的byte数组
     * *
     * @return char数组
     */
    fun bytes2Chars(bytes: ByteArray): CharArray {
        val len = bytes.size
        val chars = CharArray(len)
        for (i in 0..len - 1) {
            chars[i] = (bytes[i] and 0xff).toChar()
        }
        return chars
    }

    /**
     * 将输入流转为字节数组

     * @param is 输入流
     * *
     * @return 字节数组
     */
    fun inputStream2Bytes(`is`: InputStream?): ByteArray? {
        if (`is` == null) return null
        try {
            val os = ByteArrayOutputStream()
            val b = ByteArray(ConstUtils.KB)
            var len: Int
            while ((len = `is`.read(b)) != -1) {
                os.write(b, 0, len)
            }
            return os.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            FileUtils.closeIO(`is`)
        }
    }

    /**
     * 将字节数组转为输入流

     * @param bytes 字节数组
     * *
     * @return 输入流
     */
    fun bytes2InputStream(bytes: ByteArray): InputStream {
        return ByteArrayInputStream(bytes)
    }

    /**
     * 指定编码将输入流转为字符串

     * @param is          输入流
     * *
     * @param charsetName 编码格式
     * *
     * @return 字符串
     */
    fun inputStream2String(`is`: InputStream?, charsetName: String): String? {
        if (`is` == null) return null
        val reader: BufferedReader
        try {
            val sb = StringBuilder()
            if (StringUtils.isSpace(charsetName)) {
                reader = BufferedReader(InputStreamReader(`is`))
            } else {
                reader = BufferedReader(InputStreamReader(`is`, charsetName))
            }
            var line: String
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\r\n")// windows系统换行为\r\n，Linux为\n
            }
            // 要去除最后的换行符
            return sb.delete(sb.length - 2, sb.length).toString()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            FileUtils.closeIO(`is`)
        }
    }

    /**
     * 指定编码将字符串转为输入流

     * @param string      字符串
     * *
     * @param charsetName 编码格式
     * *
     * @return 输入流
     */
    fun string2InputStream(string: String?, charsetName: String): InputStream? {
        if (string == null || StringUtils.isSpace(charsetName)) return null
        try {
            return ByteArrayInputStream(string.toByteArray(charset(charsetName)))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }

    }
}
