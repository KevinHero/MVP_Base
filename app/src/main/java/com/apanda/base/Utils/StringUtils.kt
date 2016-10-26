package com.apanda.base.Utils

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/16
 * desc  : 字符串相关工具类
 *
 */
class StringUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * 判断字符串是否为null或长度为0

         * @param string 待校验字符串
         * *
         * @return `true`: 空 `false`: 不为空
         */
        fun isEmpty(string: CharSequence?): Boolean {
            return string == null || string.length == 0
        }

        /**
         * 判断字符串是否为null或全为空格

         * @param string 待校验字符串
         * *
         * @return `true`: null或全空格 `false`: 不为null且不全空格
         */
        fun isSpace(string: String?): Boolean {
            return string == null || string.trim { it <= ' ' }.length == 0
        }

        /**
         * null转为长度为0的字符串

         * @param string 待转字符串
         * *
         * @return string为null转为长度为0字符串，否则不改变
         */
        fun null2Length0(string: String?): String {
            return string ?: ""
        }

        /**
         * 返回字符串长度

         * @param string 字符串
         * *
         * @return null返回0，其他返回自身长度
         */
        fun length(string: CharSequence?): Int {
            return if (string == null) 0 else string.length
        }

        /**
         * 首字母大写

         * @param string 待转字符串
         * *
         * @return 首字母大写字符串
         */
        fun upperFirstLetter(string: String): String {
            if (isEmpty(string) || !Character.isLowerCase(string[0])) {
                return string
            }
            return (string[0].toInt() - 32).toChar().toString() + string.substring(1)
        }

        /**
         * 首字母小写

         * @param string 待转字符串
         * *
         * @return 首字母小写字符串
         */
        fun lowerFirstLetter(string: String): String {
            if (isEmpty(string) || !Character.isUpperCase(string[0])) {
                return string
            }
            return (string[0].toInt() + 32).toChar().toString() + string.substring(1)
        }

        /**
         * 转化为半角字符

         * @param string 待转字符串
         * *
         * @return 半角字符串
         */
        fun toDBC(string: String): String {
            if (isEmpty(string)) {
                return string
            }
            val chars = string.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i].toInt() == 12288) {
                    chars[i] = ' '
                } else if (65281 <= chars[i].toInt() && chars[i].toInt() <= 65374) {
                    chars[i] = (chars[i].toInt() - 65248).toChar()
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }

        /**
         * 转化为全角字符

         * @param string 待转字符串
         * *
         * @return 全角字符串
         */
        fun toSBC(string: String): String {
            if (isEmpty(string)) {
                return string
            }
            val chars = string.toCharArray()
            var i = 0
            val len = chars.size
            while (i < len) {
                if (chars[i] == ' ') {
                    chars[i] = 12288.toChar()
                } else if (33 <= chars[i].toInt() && chars[i].toInt() <= 126) {
                    chars[i] = (chars[i].toInt() + 65248).toChar()
                } else {
                    chars[i] = chars[i]
                }
                i++
            }
            return String(chars)
        }
    }
}