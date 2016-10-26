package com.apanda.base.Utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/2
 * desc  : 时间相关工具类
 *
 */
class TimeUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         *
         * 在工具类中经常使用到工具类的格式化描述，这个主要是一个日期的操作类，所以日志格式主要使用 SimpleDateFormat的定义格式.
         * 格式的意义如下： 日期和时间模式
         *
         * 日期和时间格式由日期和时间模式字符串指定。在日期和时间模式字符串中，未加引号的字母 'A' 到 'Z' 和 'a' 到 'z'
         * 被解释为模式字母，用来表示日期或时间字符串元素。文本可以使用单引号 (') 引起来，以免进行解释。"''"
         * 表示单引号。所有其他字符均不解释；只是在格式化时将它们简单复制到输出字符串，或者在分析时与输入字符串进行匹配。
         *
         * 定义了以下模式字母（所有其他字符 'A' 到 'Z' 和 'a' 到 'z' 都被保留）：
         *
         *
         * 字母
         * 日期或时间元素
         * 表示
         * 示例
         *
         *
         * `G`
         * Era 标志符
         * Text
         * `AD`
         *
         *
         * `y`
         * 年
         * Year
         * `1996`; `96`
         *
         *
         * `M`
         * 年中的月份
         * Month
         * `July`; `Jul`; `07`
         *
         *
         * `w`
         * 年中的周数
         * Number
         * `27`
         *
         *
         * `W`
         * 月份中的周数
         * Number
         * `2`
         *
         *
         * `D`
         * 年中的天数
         * Number
         * `189`
         *
         *
         * `d`
         * 月份中的天数
         * Number
         * `10`
         *
         *
         * `F`
         * 月份中的星期
         * Number
         * `2`
         *
         *
         * `E`
         * 星期中的天数
         * Text
         * `Tuesday`; `Tue`
         *
         *
         * `a`
         * Am/pm 标记
         * Text
         * `PM`
         *
         *
         * `H`
         * 一天中的小时数（0-23）
         * Number
         * `0`
         *
         *
         * `k`
         * 一天中的小时数（1-24）
         * Number
         * `24`
         *
         *
         * `K`
         * am/pm 中的小时数（0-11）
         * Number
         * `0`
         *
         *
         * `h`
         * am/pm 中的小时数（1-12）
         * Number
         * `12`
         *
         *
         * `m`
         * 小时中的分钟数
         * Number
         * `30`
         *
         *
         * `s`
         * 分钟中的秒数
         * Number
         * `55`
         *
         *
         * `S`
         * 毫秒数
         * Number
         * `978`
         *
         *
         * `z`
         * 时区
         * General time zone
         * `Pacific Standard Time`; `PST`; `GMT-08:00`
         *
         *
         * `Z`
         * 时区
         * RFC 822 time zone
         * `-0800`
         *
         *
         *
         * HH:mm    15:44
         * h:mm a    3:44 下午
         * HH:mm z    15:44 CST
         * HH:mm Z    15:44 +0800
         * HH:mm zzzz    15:44 中国标准时间
         * HH:mm:ss    15:44:40
         * yyyy-MM-dd    2016-08-12
         * yyyy-MM-dd HH:mm    2016-08-12 15:44
         * yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
         * yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
         * EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
         * yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
         * K:mm a    3:44 下午
         * EEE, MMM d, ''yy    星期五, 八月 12, '16
         * hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
         * yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
         * EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
         * yyMMddHHmmssZ    160812154440+0800
         * yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
         * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
         *
         */
        val DEFAULT_SDF = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        /**
         * 将时间戳转为时间字符串
         *
         * 格式为用户自定义

         * @param milliseconds 毫秒时间戳
         * *
         * @param format       时间格式
         * *
         * @return 时间字符串
         */
        @JvmOverloads fun milliseconds2String(milliseconds: Long, format: SimpleDateFormat = DEFAULT_SDF): String {
            return format.format(Date(milliseconds))
        }

        /**
         * 将时间字符串转为时间戳
         *
         * 格式为用户自定义

         * @param time   时间字符串
         * *
         * @param format 时间格式
         * *
         * @return 毫秒时间戳
         */
        @JvmOverloads fun string2Milliseconds(time: String, format: SimpleDateFormat = DEFAULT_SDF): Long {
            try {
                return format.parse(time).time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return -1
        }

        /**
         * 将时间字符串转为Date类型
         *
         * 格式为用户自定义

         * @param time   时间字符串
         * *
         * @param format 时间格式
         * *
         * @return Date类型
         */
        @JvmOverloads fun string2Date(time: String, format: SimpleDateFormat = DEFAULT_SDF): Date {
            return Date(string2Milliseconds(time, format))
        }

        /**
         * 将Date类型转为时间字符串
         *
         * 格式为用户自定义

         * @param time   Date类型时间
         * *
         * @param format 时间格式
         * *
         * @return 时间字符串
         */
        @JvmOverloads fun date2String(time: Date, format: SimpleDateFormat = DEFAULT_SDF): String {
            return format.format(time)
        }

        /**
         * 将Date类型转为时间戳

         * @param time Date类型时间
         * *
         * @return 毫秒时间戳
         */
        fun date2Milliseconds(time: Date): Long {
            return time.time
        }

        /**
         * 将时间戳转为Date类型

         * @param milliseconds 毫秒时间戳
         * *
         * @return Date类型时间
         */
        fun milliseconds2Date(milliseconds: Long): Date {
            return Date(milliseconds)
        }

        /**
         * 毫秒时间戳单位转换（单位：unit）

         * @param milliseconds 毫秒时间戳
         * *
         * @param unit
         * *                      * [ConstUtils.MSEC]: 毫秒
         * *                      * [ConstUtils.SEC]: 秒
         * *                      * [ConstUtils.MIN]: 分
         * *                      * [ConstUtils.HOUR]: 小时
         * *                      * [ConstUtils.DAY]: 天
         * *
         * *
         * @return unit时间戳
         */
        private fun milliseconds2Unit(milliseconds: Long, unit: Int): Long {
            when (unit) {
                ConstUtils.MSEC, ConstUtils.SEC, ConstUtils.MIN, ConstUtils.HOUR, ConstUtils.DAY -> return milliseconds / unit
            }
            return -1
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * time1和time2格式都为format

         * @param time0  时间字符串1
         * *
         * @param time1  时间字符串2
         * *
         * @param unit
         * *                * [ConstUtils.MSEC]: 毫秒
         * *                * [ConstUtils.SEC]: 秒
         * *                * [ConstUtils.MIN]: 分
         * *                * [ConstUtils.HOUR]: 小时
         * *                * [ConstUtils.DAY]: 天
         * *
         * *
         * @param format 时间格式
         * *
         * @return unit时间戳
         */
        @JvmOverloads fun getIntervalTime(time0: String, time1: String, unit: Int, format: SimpleDateFormat = DEFAULT_SDF): Long {
            return Math.abs(milliseconds2Unit(string2Milliseconds(time0, format) - string2Milliseconds(time1, format), unit))
        }

        /**
         * 获取两个时间差（单位：unit）
         *
         * time1和time2都为Date类型

         * @param time0 Date类型时间1
         * *
         * @param time1 Date类型时间2
         * *
         * @param unit
         * *               * [ConstUtils.MSEC]: 毫秒
         * *               * [ConstUtils.SEC]: 秒
         * *               * [ConstUtils.MIN]: 分
         * *               * [ConstUtils.HOUR]: 小时
         * *               * [ConstUtils.DAY]: 天
         * *
         * *
         * @return unit时间戳
         */
        fun getIntervalTime(time0: Date, time1: Date, unit: Int): Long {
            return Math.abs(milliseconds2Unit(date2Milliseconds(time1) - date2Milliseconds(time0), unit))
        }

        /**
         * 获取当前时间

         * @return 毫秒时间戳
         */
        val curTimeMills: Long
            get() = System.currentTimeMillis()

        /**
         * 获取当前时间
         *
         * 格式为yyyy-MM-dd HH:mm:ss

         * @return 时间字符串
         */
        val curTimeString: String
            get() = date2String(Date())

        /**
         * 获取当前时间
         *
         * 格式为用户自定义

         * @param format 时间格式
         * *
         * @return 时间字符串
         */
        fun getCurTimeString(format: SimpleDateFormat): String {
            return date2String(Date(), format)
        }

        /**
         * 获取当前时间
         *
         * Date类型

         * @return Date类型时间
         */
        val curTimeDate: Date
            get() = Date()

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * time格式为format

         * @param time   时间字符串
         * *
         * @param unit
         * *                * [ConstUtils.MSEC]: 毫秒
         * *                * [ConstUtils.SEC]: 秒
         * *                * [ConstUtils.MIN]: 分
         * *                * [ConstUtils.HOUR]: 小时
         * *                * [ConstUtils.DAY]: 天
         * *
         * *
         * @param format 时间格式
         * *
         * @return unit时间戳
         */
        @JvmOverloads fun getIntervalByNow(time: String, unit: Int, format: SimpleDateFormat = DEFAULT_SDF): Long {
            return getIntervalTime(curTimeString, time, unit, format)
        }

        /**
         * 获取与当前时间的差（单位：unit）
         *
         * time为Date类型

         * @param time Date类型时间
         * *
         * @param unit
         * *              * [ConstUtils.MSEC]: 毫秒
         * *              * [ConstUtils.SEC]: 秒
         * *              * [ConstUtils.MIN]: 分
         * *              * [ConstUtils.HOUR]: 小时
         * *              * [ConstUtils.DAY]: 天
         * *
         * *
         * @return unit时间戳
         */
        fun getIntervalByNow(time: Date, unit: Int): Long {
            return getIntervalTime(curTimeDate, time, unit)
        }

        /**
         * 判断闰年

         * @param year 年份
         * *
         * @return `true`: 闰年`false`: 平年
         */
        fun isLeapYear(year: Int): Boolean {
            return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
        }
    }
}
/**
 * 将时间戳转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss

 * @param milliseconds 毫秒时间戳
 * *
 * @return 时间字符串
 */
/**
 * 将时间字符串转为时间戳
 *
 * 格式为yyyy-MM-dd HH:mm:ss

 * @param time 时间字符串
 * *
 * @return 毫秒时间戳
 */
/**
 * 将时间字符串转为Date类型
 *
 * 格式为yyyy-MM-dd HH:mm:ss

 * @param time 时间字符串
 * *
 * @return Date类型
 */
/**
 * 将Date类型转为时间字符串
 *
 * 格式为yyyy-MM-dd HH:mm:ss

 * @param time Date类型时间
 * *
 * @return 时间字符串
 */
/**
 * 获取两个时间差（单位：unit）
 *
 * time1和time2格式都为yyyy-MM-dd HH:mm:ss

 * @param time0 时间字符串1
 * *
 * @param time1 时间字符串2
 * *
 * @param unit
 * *               * [ConstUtils.MSEC]: 毫秒
 * *               * [ConstUtils.SEC]: 秒
 * *               * [ConstUtils.MIN]: 分
 * *               * [ConstUtils.HOUR]: 小时
 * *               * [ConstUtils.DAY]: 天
 * *
 * *
 * @return unit时间戳
 */
/**
 * 获取与当前时间的差（单位：unit）
 *
 * time格式为yyyy-MM-dd HH:mm:ss

 * @param time 时间字符串
 * *
 * @param unit
 * *              * [ConstUtils.MSEC]:毫秒
 * *              * [ConstUtils.SEC]:秒
 * *              * [ConstUtils.MIN]:分
 * *              * [ConstUtils.HOUR]:小时
 * *              * [ConstUtils.DAY]:天
 * *
 * *
 * @return unit时间戳
 */