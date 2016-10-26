package com.apanda.base.Utils

import java.sql.Timestamp
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.HashMap


class DateTime {
    //	/**
    //	 * 得到给定时间到n天前的所有日期
    //	 * @param startTime
    //	 * @param day 5天前
    //	 * @return new int[]{tatal(起始),use(到目前已过天数)}
    //	 */
    //	public static Object[] beforeNDaysArr(String startTime,int day){
    //		if(startTime==null){
    //			return null;
    //		}
    //		Calendar cal = pars2Calender(startTime);
    //		cal.add(Calendar.DATE, -day);
    //		Set<String>  daySet = new TreeSet<String>();
    //		Map<String,DayInfo> dayMap = new HashMap<String, DayInfo>();
    //		for(int i=0;i<=day;i++){
    //			String tempDate = DateTime.formatDate(cal);
    //			dayMap.put(tempDate, new DayInfo(i+1, 0, "",0, DateTime.format(cal, "dd"), DateTime.getDay3(cal)));
    //			daySet.add(tempDate);
    //			cal.add(Calendar.DATE, 1);
    //		}
    //		Object[] arr = new Object[2];
    //		arr[0] = daySet;
    //		arr[1] = dayMap;
    //		return arr;
    //	}


    //	/**
    //	 * 得到给定时间到n天后的所有日期
    //	 * @param startTime
    //	 * @param day 5天后
    //	 * @return new int[]{tatal(起始),use(到目前已过天数)}
    //	 */
    //	public static Object[] afterNDaysArr(String startTime,int day){
    //		if(startTime==null){
    //			return null;
    //		}
    //		Calendar cal = pars2Calender(startTime);
    ////		cal.add(Calendar.DATE, -day);
    //		Set<String>  daySet = new TreeSet<String>();
    //		Map<String,DayInfo> dayMap = new HashMap<String, DayInfo>();
    //		for(int i=0;i<=day;i++){
    //			String tempDate = DateTime.formatDate(cal);
    //			dayMap.put(tempDate, new DayInfo(i+1, 0, "",0, DateTime.format(cal, "dd"), DateTime.getDay3(cal)));
    //			daySet.add(tempDate);
    //			cal.add(Calendar.DATE, 1);
    //		}
    //		Object[] arr = new Object[2];
    //		arr[0] = daySet;
    //		arr[1] = dayMap;
    //		return arr;
    //	}


    // ================以下是get和set方法=========================//

    var lNow = System.currentTimeMillis()
    var cNow = Calendar.getInstance()
    var dNow = Date(lNow)
    var tNow = Timestamp(lNow)
    var today = java.sql.Date(lNow)
    var now = java.sql.Time(lNow)

    companion object {


        /**
         * 一天的MilliSecond数
         */
        private val DAY_MILLI = 24 * 60 * 60 * 1000.toLong()

        /**
         * 日期格式常量 Exp:1970-01-01
         */
        val DATE_FORMAT_LINE = "yyyy-MM-dd"


        /**
         * 得到最大天数
         * @param time
         * *
         * @return
         */
        fun getMaxNum(time: String): Int {
            return pars2Calender(time)!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        }


        /**
         * 得到星期
         * 如三，二
         * @param c
         * *
         * @return
         */
        fun getDay3(c: Calendar?): String {
            if (c != null) {
                var i = c.get(Calendar.DAY_OF_WEEK)
                i = i - 1
                if (i == 1) {
                    return "一"
                } else if (i == 2) {
                    return "二"
                } else if (i == 3) {
                    return "三"
                } else if (i == 4) {
                    return "四"
                } else if (i == 5) {
                    return "五"
                } else if (i == 6) {
                    return "六"
                } else if (i == 0) {
                    return "日"
                } else if (i == 7) {
                    return "日"
                }
            }
            return ""
        }

        /**
         * 得到星期
         * 如周三，周二
         * @param c
         * *
         * @return
         */
        fun getDay2(c: Calendar?): String {
            if (c != null) {
                var i = c.get(Calendar.DAY_OF_WEEK)
                i = i - 1
                if (i == 1) {
                    return "周一"
                } else if (i == 2) {
                    return "周二"
                } else if (i == 3) {
                    return "周三"
                } else if (i == 4) {
                    return "周四"
                } else if (i == 5) {
                    return "周五"
                } else if (i == 6) {
                    return "周六"
                } else if (i == 0) {
                    return "周日"
                } else if (i == 7) {
                    return "周日"
                }
            }
            return ""
        }

        /**
         * 得到星期
         * 如周三，周二
         * @return
         */
        fun getDayOfWeek(time: String): String {
            val c = pars2Calender(time)
            if (c != null) {
                var i = c.get(Calendar.DAY_OF_WEEK)
                i = i - 1
                if (i == 1) {
                    return "周一"
                } else if (i == 2) {
                    return "周二"
                } else if (i == 3) {
                    return "周三"
                } else if (i == 4) {
                    return "周四"
                } else if (i == 5) {
                    return "周五"
                } else if (i == 6) {
                    return "周六"
                } else if (i == 0) {
                    return "周日"
                } else if (i == 7) {
                    return "周日"
                }
            }
            return ""
        }


        //	/**
        //	 * 得到星期
        //	 * 如周三，周二
        //	 * @param c
        //	 * @return
        //	 */
        //	public static String getDay3(Context context,String timestamp) {
        //		Calendar c = pars2Calender(timestamp);
        //		if (c != null) {
        //			int i = c.get(Calendar.DAY_OF_WEEK);
        //			i = i-1;
        //			if(i==1){
        //				return context.getResources().getString(R.string.str_Mon );
        //			}else if(i==2){
        //				return context.getResources().getString(R.string.str_Tues );
        //			}else if(i==3){
        //				return context.getResources().getString(R.string.str_Wed );
        //			}else if(i==4){
        //				return context.getResources().getString(R.string.str_Thur );
        //			}else if(i==5){
        //				return context.getResources().getString(R.string.str_Fri );
        //			}else if(i==6){
        //				return context.getResources().getString(R.string.str_Sat );
        //			}else if(i==0){
        //				return context.getResources().getString(R.string.str_Sun );
        //			}else if(i==7){
        //				return context.getResources().getString(R.string.str_Sun );
        //			}
        //		}
        //		return "";
        //	}

        //	/**
        //	 * 得到星期
        //	 * 如周三，周二
        //	 * @param c
        //	 * @return
        //	 */
        //	public static String getDay3(String timestamp) {
        //		Calendar c = pars2Calender(timestamp);
        //		if (c != null) {
        //			int i = c.get(Calendar.DAY_OF_WEEK);
        //			i = i-1;
        //			if(i==1){
        //				return "周一";
        //			}else if(i==2){
        //				return "周二";
        //			}else if(i==3){
        //				return "周三";
        //			}else if(i==4){
        //				return "周四";
        //			}else if(i==5){
        //				return "周五";
        //			}else if(i==6){
        //				return "周六";
        //			}else if(i==0){
        //				return "周日";
        //			}else if(i==7){
        //				return "周日";
        //			}
        //		}
        //		return "";
        //	}

        /*
	 * private DateTime(long lNow, Calendar cNow, Date dNow, Timestamp tNow,
	 * java.sql.Date today, Time now) { this.lNow = lNow; this.cNow = cNow;
	 * this.dNow = dNow; this.tNow = tNow; this.today = today; this.now = now; }
	 */

        /**
         * 根据标准日期将其转为 只有小时与分钟
         * 如2014-01-08 17:04:49 转化为 17:04
         * @param timestamp
         * *
         * @return
         */
        fun getHourAndMinFromTimestamp(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""
            val start = timestamp.indexOf(" ")
            val end = timestamp.lastIndexOf(":")

            try {
                timestamp = timestamp.substring(start + 1, end)
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }

        /**
         * 根据标准日期将其转为 只有月与日
         * 如2014-01-08 17:04:49 转化为01-08
         * @param timestamp
         * *
         * @return
         */
        fun getMonAndDayFromTimestamp(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""
            val start = timestamp.indexOf("-")
            val end = timestamp.indexOf(" ")

            try {
                timestamp = timestamp.substring(start + 1, end)
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }

        /**
         * 根据标准日期将其转为月日星期
         * 如2014-01-08 17:04:49 转化为01月08日 周三
         * @param timestamp
         * *
         * @return
         */
        fun getMonDayWeekFromTimestamp(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""

            val c = pars2Calender(timestamp)
            //		int w =getDay(c);

            val start = timestamp.indexOf("-")
            val end = timestamp.indexOf(" ")

            try {
                timestamp = timestamp.substring(start + 1, end)
                timestamp = timestamp.replace("-", "月")
                timestamp = timestamp + "日" + "　" + getDay2(c)
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }


        /**
         * 根据标准日期将其转为月日星期
         * 如2014-01-08 17:04:49 转化为2014年01月08日 周三
         * @param timestamp
         * *
         * @return
         */
        fun getMonDayWeekFromTimestamp2(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""

            val c = pars2Calender(timestamp)
            //		int w =getDay(c);


            val start = timestamp.indexOf("-")
            val end = timestamp.indexOf(" ")

            val year = timestamp.substring(0, start)
            try {
                timestamp = timestamp.substring(start + 1, end)
                timestamp = timestamp.replace("-", "月")
                timestamp = timestamp + "日" + "　" + getDay2(c)
                timestamp = year + "年" + timestamp
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }

        /**
         * 根据标准日期将其转为年月日
         * 如2014-01-08 17:04:49 转化为2014年01月08日
         * @param timestamp
         * *
         * @return
         * *
         * * Ts timestamp
         * * YMD year month day
         */
        fun convertTsToYMD(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""

            val start = timestamp.indexOf("-")
            val end = timestamp.indexOf(" ")

            val year = timestamp.substring(0, start)
            try {
                timestamp = timestamp.substring(start + 1, end)
                timestamp = timestamp.replace("-", "月")
                timestamp = timestamp + "日"
                timestamp = year + "年" + timestamp
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }


        /**
         * 根据标准日期将其转为年月日
         * 如2014-01-08 17:04:49 转化为2014-01-08
         * @param timestamp
         * *
         * @return
         */
        fun getDateFromTimestamp(timestamp: String?): String {
            var timestamp: String? = timestamp ?: return ""
//		int start = timestamp.indexOf("-");
            val end = timestamp.indexOf(" ")

            try {
                timestamp = timestamp.substring(0, end)
            } catch (e: Exception) {
                e.printStackTrace()
                return "出错啦！"
            }

            return timestamp
        }


        /**
         * 得到年

         * @param c
         * *
         * @return
         */
        fun getYear(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.YEAR)
            } else {
                return Calendar.getInstance().get(Calendar.YEAR)
            }
        }

        /**
         * 得到月份 注意，这里的月份依然是从0开始的

         * @param c
         * *
         * @return
         */
        fun getMonth(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.MONTH)
            } else {
                return Calendar.getInstance().get(Calendar.MONTH)
            }
        }

        /**
         * 得到月份如 1月，2月

         * @param c
         * *
         * @return
         */
        fun getMonth2(c: Calendar?): String {
            var str = ""
            if (c != null) {
                str = getMonthString(c.get(Calendar.MONTH) + 1)
            } else {
                str = getMonthString(Calendar.getInstance().get(Calendar.MONTH) + 1)
            }
            return str
        }

        /**

         * @param month
         * *
         * @return
         */
        fun getMonthString(month: Int): String {
            var monthStr = ""
            if (month == 1) {
                monthStr = "1月"
            } else if (month == 2) {
                monthStr = "2月"
            } else if (month == 3) {
                monthStr = "3月"
            } else if (month == 4) {
                monthStr = "4月"
            } else if (month == 5) {
                monthStr = "5月"
            } else if (month == 6) {
                monthStr = "6月"
            } else if (month == 7) {
                monthStr = "7月"
            } else if (month == 8) {
                monthStr = "8月"
            } else if (month == 9) {
                monthStr = "9月"
            } else if (month == 10) {
                monthStr = "10月"
            } else if (month == 11) {
                monthStr = "11月"
            } else if (month == 12) {
                monthStr = "12月"
            }
            return monthStr
        }

        /**
         * 得到日期

         * @param c
         * *
         * @return
         */
        fun getDate(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.DATE)
            } else {
                return Calendar.getInstance().get(Calendar.DATE)
            }
        }

        /**
         * 得到星期

         * @param c
         * *
         * @return
         */
        fun getDay(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.DAY_OF_WEEK)
            } else {
                return Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
            }
        }

        /**
         * 得到小时

         * @param c
         * *
         * @return
         */
        fun getHour(tatalSecond: Int): Int {
            return tatalSecond / 3600
        }

        /**
         * 得到小时

         * @param c
         * *
         * @return
         */
        fun getHour(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.HOUR_OF_DAY)
            } else {
                return Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            }
        }

        /**
         * 得到分钟

         * @param c
         * *
         * @return
         */
        fun getMinute(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.MINUTE)
            } else {
                return Calendar.getInstance().get(Calendar.MINUTE)
            }
        }

        /**
         * 得到秒

         * @param c
         * *
         * @return
         */
        fun getSecond(c: Calendar?): Int {
            if (c != null) {
                return c.get(Calendar.SECOND)
            } else {
                return Calendar.getInstance().get(Calendar.SECOND)
            }
        }

        /**
         * 得到指定或者当前时间前n天的Calendar

         * @param c
         * *
         * @param n
         * *
         * @return
         */
        fun beforeNDays(c: Calendar?, n: Int): Calendar {
            // 偏移量，给定n天的毫秒数
            val offset = n * 24 * 60 * 60 * 1000.toLong()
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }
            calendar!!.timeInMillis = calendar.timeInMillis - offset
            return calendar
        }

        /**
         * 得到指定或者当前时间前n天的日期
         * @param c
         * *
         * @param n
         * *
         * @return
         */
        fun beforeNDays_v2(date: String, n: Int): String {
            var date = date
            if (date.indexOf(" ") <= 0) {
                date = date + " 00:00:00"
            }
            // 偏移量，给定n天的毫秒数
            val calendar = pars2Calender(date)
            calendar!!.add(Calendar.DAY_OF_MONTH, n)
            return formatDate(calendar)
        }

        /**
         * 得到当前时间前n天的Calendar
         * @param c
         * *
         * @param n
         * *
         * @return
         */
        fun beforeNDays(n: Int): Calendar {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, n)
            return calendar
        }

        /**
         * 得到当前时间前或后n天的String 如2014-03-06
         * @param n 前为负，如-2表前两天
         * *
         * @return
         */
        fun beforeNDays2Str(n: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, n)
            val str = formatDate(calendar)
            return str
        }

        /**
         * 得到当前时间前几秒的时间，或后几秒的时间
         * @param n 前为负，如-60*60表60分钟前的时间
         * *
         * @return
         */
        fun beforeSecond(n: Int): String {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, n)
            val str = formatTime(calendar)
            return str
        }

        /**
         * 得到time前几秒的时间，或后几秒的时间
         * @param n 前为负，如-60*60表60分钟前的时间
         * *
         * @return
         */
        fun beforeSecond(time: String, n: Int): String {
            //		Calendar calendar = Calendar.getInstance();
            val calendar = pars2Calender(time)
            calendar!!.add(Calendar.SECOND, n)
            val str = formatTime(calendar)
            return str
        }


        /**
         * 得到指定或者当前时间后n天的Calendar

         * @param c
         * *
         * @param n
         * *
         * @return
         */
        fun afterNDays(c: Calendar?, n: Int): Calendar {
            // 偏移量，给定n天的毫秒数
            val offset = n * 24 * 60 * 60 * 1000.toLong()
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }

            calendar!!.timeInMillis = calendar.timeInMillis + offset
            return calendar
        }

        /**
         * 将2014-01-07 14:33:43 转成14：33
         * @param TimeString
         * *
         * @return
         */
        fun TimeStringToHourMIn(TimeString: String): String {
            val space = TimeString.indexOf(" ")
            val last = TimeString.lastIndexOf(":")
            var time = ""
            try {
                time = TimeString.substring(space + 1, last)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return time
        }

        /**
         * 昨天

         * @param c
         * *
         * @return
         */
        fun yesterday(c: Calendar?): Calendar {
            val offset = 1 * 24 * 60 * 60 * 1000.toLong()
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }

            calendar!!.timeInMillis = calendar.timeInMillis - offset
            return calendar
        }

        /**
         * 明天

         * @param c
         * *
         * @return
         */
        fun tomorrow(c: Calendar?): Calendar {
            val offset = 1 * 24 * 60 * 60 * 1000.toLong()
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }

            calendar!!.timeInMillis = calendar.timeInMillis + offset
            return calendar
        }

        /**
         * 得到指定或者当前时间前offset毫秒的Calendar

         * @param c
         * *
         * @param offset
         * *
         * @return
         */
        fun before(c: Calendar?, offset: Long): Calendar {
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }

            calendar!!.timeInMillis = calendar.timeInMillis - offset
            return calendar
        }

        /**
         * 得到指定或者当前时间前offset毫秒的Calendar

         * @param c
         * *
         * @param offset
         * *
         * @return
         */
        fun after(c: Calendar?, offset: Long): Calendar {
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }

            calendar!!.timeInMillis = calendar.timeInMillis - offset
            return calendar
        }

        /**
         * 日期格式化

         * @param c
         * *
         * @param pattern
         * *
         * @return
         */
        fun format(c: Calendar?, pattern: String?): String {
            var pattern = pattern
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }
            if (pattern == null || pattern == "") {
                pattern = "yyyy年MM月dd日 HH:mm:ss"
            }
            val sdf = SimpleDateFormat(pattern)

            return sdf.format(calendar!!.time)
        }

        /**
         * 日期格式化成2014-03-06 16:21:22
         * @param c
         * *
         * @return
         */
        fun formatTime(c: Calendar?): String {
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }
            val pattern = "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat(pattern)
            return sdf.format(calendar!!.time)
        }

        /**
         * 日期格式化成2014-03-06
         * @param c
         * *
         * @return
         */
        fun formatDate(c: Calendar?): String {
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }
            val pattern = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(pattern)
            return sdf.format(calendar!!.time)
        }

        /**
         * 日期格式化成2014年03月
         * @param c
         * *
         * @return
         */
        fun formatMonth(c: Calendar?): String {
            var calendar: Calendar? = null
            if (c != null) {
                calendar = c
            } else {
                calendar = Calendar.getInstance()
            }
            val pattern = "yyyy年MM月"
            val sdf = SimpleDateFormat(pattern)
            return sdf.format(calendar!!.time)
        }

        /**
         * 计算两个时期的秒数
         * @return
         */
        fun differToSecond(date1: Date, date2: Date): Long {

            return 1
        }

        /**
         * Date类型转换到Calendar类型

         * @param d
         * *
         * @return
         */
        fun Date2Calendar(d: Date): Calendar {
            val c = Calendar.getInstance()
            c.time = d
            return c
        }

        /**
         * Calendar类型转换到Date类型

         * @param c
         * *
         * @return
         */
        fun Calendar2Date(c: Calendar): Date {
            return c.time
        }

        /**
         * Date类型转换到Timestamp类型

         * @param d
         * *
         * @return
         */
        fun Date2Timestamp(d: Date): Timestamp {
            return Timestamp(d.time)
        }

        /**
         * Calendar类型转换到Timestamp类型

         * @param c
         * *
         * @return
         */
        fun Calendar2Timestamp(c: Calendar): Timestamp {
            return Timestamp(c.timeInMillis)
        }

        /**
         * Timestamp类型转换到Calendar类型

         * @param ts
         * *
         * @return
         */
        fun Timestamp2Calendar(ts: Timestamp): Calendar {
            val c = Calendar.getInstance()
            c.time = ts
            return c
        }

        /**
         * 得到当前时间的字符串表示 格式2010-02-02 12:12:12
         * @return
         */
        val timeString: String
            get() = format(Calendar.getInstance(), "yyyy-MM-dd HH:mm:ss")


        /**
         * 得到当前时间的字符串表示 格式20140502_121212
         * @return
         */
        val timeString2: String
            get() {
                val time = format(Calendar.getInstance(), "yyyyMMddHHmmss")
                return time
            }
        /**
         * 当前时间 格式12:29
         * @return
         */
        val timeStr1229: String
            get() {
                val time = format(Calendar.getInstance(), "HH:mm")
                return time
            }

        /**
         * 当前时间 格式12:29
         * @return
         */
        fun getTimeStr1229(Time: String): String {
            val c = pars2Calender(Time)
            val time = format(c, "HH:mm")
            return time
        }

        /**
         * 当前时间 格式12:29:00
         * @return
         */
        fun getTimeStr122901(Time: String): String {
            val c = pars2Calender(Time)
            val time = format(c, "HH:mm:ss")
            return time
        }

        /**
         * 当前时间 格式12时29分
         * @return
         */
        val timeStr12shi29fen: String
            get() {
                val time = format(Calendar.getInstance(), "HH时mm分")
                return time
            }

        /**
         * 得到下个钟时间
         */
        val nextHour: Calendar
            get() {
                val c = Calendar.getInstance()
                c.add(Calendar.HOUR_OF_DAY, 1)
                c.set(Calendar.MINUTE, 0)
                c.set(Calendar.SECOND, 0)
                return c
            }
        /**
         * 得到下个1/2刻
         */
        val nextHalf: Calendar
            get() {
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                var min = c.get(Calendar.MINUTE)
                val sec = c.get(Calendar.SECOND)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val mon = c.get(Calendar.MONTH) + 1
                if (min < 30) {
                    min = 30
                } else if (min >= 30) {
                    min = 0
                    c.add(Calendar.HOUR_OF_DAY, 1)
                }
                c.set(Calendar.MINUTE, min)
                c.set(Calendar.SECOND, 0)
                return c
            }
        /**
         * 得到下个1/4刻
         */
        val nextquarter: Calendar
            get() {
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                var min = c.get(Calendar.MINUTE)
                val sec = c.get(Calendar.SECOND)
                val day = c.get(Calendar.DAY_OF_MONTH)
                val mon = c.get(Calendar.MONTH) + 1
                if (min < 15) {
                    min = 15
                } else if (min >= 15 && min < 30) {
                    min = 30
                } else if (min >= 30 && min < 45) {
                    min = 45
                } else if (min >= 45) {
                    min = 0
                    c.add(Calendar.HOUR_OF_DAY, 1)
                }
                c.set(Calendar.MINUTE, min)
                c.set(Calendar.SECOND, 0)
                return c
            }

        /**
         * 得到某一天的周起始天，周结束天
         * @param date
         * *
         * @param startDayOfWeek 用户定义的每周开始天，1表星期天，2表星期一
         * *
         * @return 开始：arr[0],结束：arr[1]yyyy-MM-dd
         */
        fun getDateOfWeekStartAndEnd(date: String, startDayOfWeek: Int): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(2)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            //		c.get(Calendar.d)
            val d = c!!.get(Calendar.DAY_OF_WEEK)//周日是开始
            println(d)
            var dif = 0
            if (startDayOfWeek > d) {
                dif = d + (7 - startDayOfWeek)
            } else {
                dif = d - startDayOfWeek
            }

            println(dif)
            c.add(Calendar.DATE, -dif)


            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, 6)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            return arr
        }

        /**
         * 得到某一天,上一周的周起始天，周结束天
         * @param date
         * *
         * @param startDayOfWeek 用户定义的每周开始天，1表星期天，2表星期一
         * *
         * @return 开始：arr[0],结束：arr[1]yyyy-MM-dd
         */
        fun getDateOfWeekStartAndEndForLastWeek(date: String, startDayOfWeek: Int): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(2)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            //		c.get(Calendar.d)
            val d = c!!.get(Calendar.DAY_OF_WEEK)//周日是开始
            println(d)
            var dif = 0
            if (startDayOfWeek > d) {
                dif = d + (7 - startDayOfWeek)
            } else {
                dif = d - startDayOfWeek
            }

            println(dif)
            c.add(Calendar.DATE, -dif)

            c.add(Calendar.DATE, -7)

            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, 6)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            return arr
        }

        /**
         * 得到某一天,下一周的周起始天，周结束天
         * @param date
         * *
         * @param startDayOfWeek 用户定义的每周开始天，1表星期天，2表星期一
         * *
         * @return 开始：arr[0],结束：arr[1]yyyy-MM-dd
         */
        fun getDateOfWeekStartAndEndForNextWeek(date: String, startDayOfWeek: Int): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(2)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            //		c.get(Calendar.d)
            val d = c!!.get(Calendar.DAY_OF_WEEK)//周日是开始
            println(d)
            var dif = 0
            if (startDayOfWeek > d) {
                dif = d + (7 - startDayOfWeek)
            } else {
                dif = d - startDayOfWeek
            }

            println(dif)
            c.add(Calendar.DATE, -dif)

            c.add(Calendar.DATE, 7)

            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, 6)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            return arr
        }

        /**
         * 得天每周的开始日期 如2014--7-14
         * @param date
         * *
         * @param startDayOfWeek 用户定义的每周开始天，1表星期天，2表星期一
         */
        fun getDateOfWeekStart(startDayOfWeek: Int): String {
            val c = Calendar.getInstance()
            val d = c.get(Calendar.DAY_OF_WEEK)//周日是开始
            //		System.out.println(d );
            var dif = 0
            if (startDayOfWeek > d) {
                dif = d + (7 - startDayOfWeek)
            } else {
                dif = d - startDayOfWeek
            }
            c.add(Calendar.DATE, -dif)

            return formatDate(c)
        }

        /**
         * 得到某一天,该天的第一天与最后一天的日期
         * @param date
         * *
         * @return 开始：arr[0],结束：arr[1],yyyy-MM-dd
         */
        fun getDateOfMonthStartAndEnd(date: String): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(3)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            val day = c!!.get(Calendar.DAY_OF_MONTH)
            val min = c.getActualMinimum(Calendar.DAY_OF_MONTH)
            val max = c.getActualMaximum(Calendar.DAY_OF_MONTH)
            c.add(Calendar.DATE, -(day - min))
            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, max - min)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            arr[2] = max + ""
            return arr
        }

        /**
         * 得到某一天,该天上个月的第一天与最后一天的日期
         * @param date
         * *
         * @return 开始：arr[0],结束：arr[1],yyyy-MM-dd
         */
        fun getDateOfMonthStartAndEndLastMonth(date: String): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(3)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            c!!.add(Calendar.MONTH, -1)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val min = c.getActualMinimum(Calendar.DAY_OF_MONTH)
            val max = c.getActualMaximum(Calendar.DAY_OF_MONTH)
            c.add(Calendar.DATE, -(day - min))
            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, max - min)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            arr[2] = max + ""
            return arr
        }

        /**
         * 得到某一天,该天下个月的第一天与最后一天的日期
         * @param date
         * *
         * @return 开始：arr[0],结束：arr[1],yyyy-MM-dd
         */
        fun getDateOfMonthStartAndEndNextMonth(date: String): Array<String> {
            var date = date
            val arr = arrayOfNulls<String>(3)
            if (date.indexOf(" ") < 0) {
                date = date + " 00:00:00"
            }
            val c = DateTime.pars2Calender(date)
            c!!.add(Calendar.MONTH, 1)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val min = c.getActualMinimum(Calendar.DAY_OF_MONTH)
            val max = c.getActualMaximum(Calendar.DAY_OF_MONTH)
            c.add(Calendar.DATE, -(day - min))
            var time = DateTime.format(c, "yyyy-MM-dd")
            arr[0] = time
            c.add(Calendar.DATE, max - min)
            time = DateTime.format(c, "yyyy-MM-dd")
            arr[1] = time
            arr[2] = max + ""
            return arr
        }


        /**
         * 得到1分钟后的时间,格式：2014-04-02 12:12:12
         * @return 2014-04-02 12:12:12
         */
        //		String time = date.substring(date.indexOf(" ")+1, date.length());
        //		log("测试提醒：提醒时间为："+time);
        val time1MinuteLater: String
            get() {
                val c = Calendar.getInstance()
                c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 60)
                val time = DateTime.formatTime(c)
                return time
            }

        /**
         * 得到1分钟后的时间,格式：12:12:12
         * @return 12:12:12
         */
        //		log("测试提醒：提醒时间为："+time);
        val time1MinuteLater2: String
            get() {
                val c = Calendar.getInstance()
                c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 60)
                val date = DateTime.formatTime(c)
                val time = date.substring(date.indexOf(" ") + 1, date.length)
                return time
            }

        /**
         * 得到n秒后的时间,格式：12:12:12
         * @return 12:12:12
         */
        fun getTime_nSecondLater(n: Int): String {
            val c = Calendar.getInstance()
            c.set(Calendar.SECOND, c.get(Calendar.SECOND) + 20)
            val date = DateTime.formatTime(c)
            val time = date.substring(date.indexOf(" ") + 1, date.length)
            return time
        }

        /**
         * 得到当前时间的字符串表示 格式2010-02-02

         * @return
         */
        val dateString: String
            get() = format(Calendar.getInstance(), "yyyy/MM/dd")

        /**
         * 标准日期格式字符串解析成Calendar对象

         * @param s
         * *
         * @return
         */
        fun pars2Calender(s: String): Calendar? {
            val ts = Timestamp.valueOf(s)
            return Timestamp2Calendar(ts)
        }

        /**
         * 标准日期格式字符串解析成Calendar对象
         * @param s
         * *
         * @return
         */
        fun pars2CalenderCanNull(s: String): Calendar {
            var cal: Calendar
            try {
                val ts = Timestamp.valueOf(s)
                cal = Timestamp2Calendar(ts)
            } catch (e: IllegalArgumentException) {
                cal = Calendar.getInstance()
            }

            return cal
        }

        /**
         * 标准日期字符串解析成Calendar对象，时间为00:00:00
         * @param date
         * *
         * @return
         */
        fun pars2Calender2(date: String): Calendar {
            val ts = Timestamp.valueOf(date + " 00:00:00")
            return Timestamp2Calendar(ts)
        }

        /**
         * 将秒数转为00小时00分00秒 格式
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime(totalSecond: Long): String {
            val hours = totalSecond / 3600
            val minute = totalSecond % 3600 / 60
            val second = totalSecond % 3600 % 60

            var retValue = ""
            if (hours > 0)
                retValue += hours + "小时"

            if (minute > 0)
                retValue += minute + "分"

            retValue += second + "秒"

            return retValue
        }

        /**
         * 将秒数转为00小时00分 格式 (不带秒)
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime3(totalSecond: Long): String {
            val days = totalSecond / 86400
            val hours = totalSecond % 86400 / 3600
            val minute = totalSecond % 86400 % 3600 / 60
            //		long second = ((totalSecond%86400) % 3600) % 60;

            var retValue = ""

            if (days > 0) {
                retValue += days + "天"
            }
            if (hours > 0) {
                retValue += hours + "小时"
            }

            if (minute > 0)
                retValue += minute + "分"

            if (retValue == "") {

                retValue += "<1分"
            }

            return retValue
        }
        //	/**
        //	 * 将秒数转为00时00分 格式 (不带秒)
        //	 * @param totalSecond
        //	 * @return
        //	 */
        //	public static String calculateTime5(Context context,long totalSecond) {
        //		long days = totalSecond / 86400;
        //		long hours = (totalSecond%86400) / 3600;
        //		long minute = ((totalSecond%86400) % 3600) / 60;
        ////		long second = ((totalSecond%86400) % 3600) % 60;
        //
        //		String retValue = "";
        //
        //		if(days>0){
        //			retValue += days + context.getResources().getString(R.string.str_Day);
        //		}
        //		if(hours > 0){
        //			retValue += hours + context.getResources().getString(R.string.str_hour_short);
        //		}
        //
        //		if(minute > 0)
        //			retValue += minute + context.getResources().getString(R.string.str_minute_short);
        //
        //		if(retValue.equals("")){
        //
        //			retValue += "<1"+context.getResources().getString(R.string.str_minute_short);
        //		}
        //
        //		return retValue;
        //	}
        //	/**
        //	 * 将秒数转为00时00分 格式 (不带秒)
        //	 * @param totalSecond
        //	 * @return
        //	 */
        //	public static String calculateTime5(long totalSecond) {
        //		long days = totalSecond / 86400;
        //		long hours = (totalSecond%86400) / 3600;
        //		long minute = ((totalSecond%86400) % 3600) / 60;
        ////		long second = ((totalSecond%86400) % 3600) % 60;
        //
        //		String retValue = "";
        //
        //		if(days>0){
        //			retValue += days + "天";
        //		}
        //		if(hours > 0){
        //			retValue += hours + "时";
        //		}
        //
        //		if(minute > 0)
        //			retValue += minute + "分";
        //
        //		if(retValue.equals("")){
        //
        //			retValue += "<1分";
        //		}
        //
        //		return retValue;
        //	}

        /**
         * 将秒数转为00:00分 格式 (不带秒)
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime4(totalSecond: Long): String {
            val hours = totalSecond % 86400 / 3600
            val minute = totalSecond % 86400 % 3600 / 60
            //		long second = ((totalSecond%86400) % 3600) % 60;
            var retValue = ""
            if (hours > 0) {
                retValue += hours + ":"
            }

            if (minute < 10) {
                retValue += "0" + minute
            } else {
                retValue += minute
            }
            return retValue
        }

        /**
         * 将秒数转为00:00:00格式
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime2(totalSecond: Long): String {
            val hours = totalSecond / 3600
            val minute = totalSecond % 3600 / 60
            val second = totalSecond % 3600 % 60

            var retValue = ""
            if (hours > 0) {
                //			retValue += hours + "：";
                if (hours > 9) {
                    retValue += hours + ":"
                } else {
                    retValue += "0$hours:"
                }
            } else {
                retValue += "00:"
            }

            if (minute > 0) {
                if (minute > 9) {
                    retValue += minute + ":"
                } else {
                    retValue += "0$minute:"
                }
            } else {
                retValue += "00:"
            }

            if (second > 9) {
                retValue += second
            } else {
                retValue += "0" + second
            }

            return retValue
        }

        /**
         * 将秒数转为00时00分00秒格式
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime6(totalSecond: Long): String {
            val hours = totalSecond / 3600
            val minute = totalSecond % 3600 / 60
            val second = totalSecond % 3600 % 60

            var retValue = ""
            if (hours > 0) {
                //			retValue += hours + "：";
                //			if(hours>9){
                retValue += hours + "时"
                //			}else{
                //				retValue += hours + ":";
                //			}
            }

            if (minute > 0) {
                //			if(minute>9){
                retValue += minute + "分"
                //			}else{
                //				retValue += "0"+minute + ":";
                //			}
            } else {
                if (hours > 0) {
                    retValue += "0分"
                }
            }

            if (second > 0) {
                retValue += second + "秒"
            }
            if (hours == 0 && minute == 0 && second == 0) {
                retValue = "0秒"
            }


            return retValue
        }

        /**
         * 将秒数转为00:00秒格式(不带时)
         * @param totalSecond
         * *
         * @return
         */
        fun calculateTime7(totalSecond: Long): String {
            val hours = totalSecond / 3600
            val minute = totalSecond % 3600 / 60
            val second = totalSecond % 3600 % 60

            var retValue = ""

            if (minute > 0) {
                //			if(minute>9){
                retValue += minute + ":"
                //			}else{
                //				retValue += "0"+minute + ":";
                //			}
            } else {
                retValue += "0:"
            }

            if (second > 0) {
                if (second > 9) {
                    retValue += second
                } else {
                    retValue += "0" + second
                }
            } else {
                retValue += "00"
            }
            return retValue
        }

        /**
         * 比较两个日期的大小,（大）即日期靠后
         * @param DATE1
         * *
         * @param DATE2
         * *
         * @return 1时date1比date2更后
         */
        fun compare_date(DATE1: String, DATE2: String): Int {
            val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")//日期格式控制
            try {
                val dt1 = df.parse(DATE1)
                val dt2 = df.parse(DATE2)
                if (dt1.time > dt2.time) {
                    return 1
                } else if (dt1.time < dt2.time) {
                    return -1
                } else {
                    return 0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return 0
        }

        /**
         * 日期与现在比较，
         * @param DATE1
         * *
         * @return 1表time大于现在，0相等，-1小于现在
         */
        fun compareNow(time: String): Int {
            val df = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")//日期格式控制
            try {
                val dt1 = df.parse(time)
                val dt2 = Calendar.getInstance().time
                if (dt1.time > dt2.time) {
                    return 1
                } else if (dt1.time < dt2.time) {
                    return -1
                } else {
                    return 0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return 0
        }

        /**
         * 将字符串转换成日期类型
         * @param dateStr    日期字符串
         * *
         * @param styleStr    样式字符串,如:yyy-MM-dd HH:mm:ss
         * *
         * @return
         */
        fun toDate(dateStr: String, styleStr: String): Date {
            var date: Date? = null
            val sdf = SimpleDateFormat(styleStr)
            try {
                date = sdf.parse(dateStr)
            } catch (pe: ParseException) {
                pe.printStackTrace()
                date = null
            }

            return date
        }

        /**
         * 求两个日期间隔的天数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         * * 求两个日期间隔的天数
         */
        fun getBetweenDayNumber(startDate: Date, endDate: Date): Int {
            var dayNumber: Long = 0
            dayNumber = (endDate.time - startDate.time) / DateTime.DAY_MILLI

            return dayNumber.toInt()
        }

        /**
         * 求两个日期间隔的天数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun getBetweenDayNumber(startDateStr: String, endDate: Date): Int {
            var dayNumber: Long = 0
            val sdf = SimpleDateFormat(DateTime.DATE_FORMAT_LINE)
            try {
                val startDate = sdf.parse(startDateStr)
                dayNumber = (endDate.time - startDate.time) / DateTime.DAY_MILLI
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return dayNumber.toInt()
        }

        /**
         * 求两个日期间隔的小时
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun getBetweenDayNumberByTime(startDate: Date, endDate: Date): Int {
            var dayNumber: Long = 0
            dayNumber = (endDate.time - startDate.time) / (60 * 1000)
            return dayNumber.toInt()
        }


        /**
         * 求两个日期间隔的天数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         * * 求两个日期间隔的天数
         */
        fun cal_daysBetween(startDate: Date, endDate: Date): Int {
            var dayNumber: Long = 0
            dayNumber = (endDate.time - startDate.time) / DateTime.DAY_MILLI
            return dayNumber.toInt()
        }

        /**
         * 求两个日期间隔的天数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun cal_daysBetween(startDateStr: String, endDate: Date): Int {
            var dayNumber: Long = 0
            val sdf = SimpleDateFormat(DateTime.DATE_FORMAT_LINE)
            try {
                val startDate = sdf.parse(startDateStr)
                dayNumber = (endDate.time - startDate.time) / DateTime.DAY_MILLI
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return dayNumber.toInt()
        }

        /**
         * 求两个日期间隔的天数
         * @param startDateStr
         * *
         * @param endDateStr 2014-03-14
         * *
         * @return
         */
        fun cal_daysBetween(startDateStr: String, endDateStr: String): Int {
            var dayNumber: Long = 0
            val sdf = SimpleDateFormat(DateTime.DATE_FORMAT_LINE)
            try {
                val startDate = sdf.parse(startDateStr)
                val endDate = sdf.parse(endDateStr)
                dayNumber = (endDate.time - startDate.time) / DateTime.DAY_MILLI
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return dayNumber.toInt()
        }

        /**
         * 两个日期间的天数，格式任意，位置任意
         * @param start 可以是2014-03-06 也可以2014-03-06 00:12:00格式
         * *
         * @param end
         * *
         * @return {2014-03-12,2014-03-13}
         */
        fun cal_daysBetween_v2(start: String?, end: String?): Int {
            var start = start
            var end = end
            if (start == null || end == null) {
                return 0
            }
            start = start.replace("T", " ")
            end = end.replace("T", " ")
            val startInt = start.indexOf(" ")
            val endInt = end.indexOf(" ")
            if (startInt > 0) {
                start = start.substring(0, startInt)
            }
            if (endInt > 0) {
                end = end.substring(0, endInt)
            }
            val diff = compare_date(start + " 00:00:00", end + " 00:00:00")

            return diff
        }

        /**
         * 得到指定日期间的日期集合
         * @param startDateStr 可以是2014-03-06 也可以2014-03-06 00:12:00格式
         * *
         * @param endDateStr
         * *
         * @return {2014-03-12,2014-03-13}
         */
        fun cal_daysMapBetween(start: String?, end: String?): HashMap<String, Int>? {
            var start = start
            var end = end
            if (start == null && end == null) {
                return null
            }
            val startInt = start!!.indexOf(" ")
            val endInt = end!!.indexOf(" ")
            if (startInt > 0) {
                start = start.substring(0, startInt)
            }
            if (endInt > 0) {
                end = end.substring(0, endInt)
            }
            val map = HashMap<String, Int>()
            val flag = compare_date(start + " 00:00:00", end + " 00:00:00")
            if (flag > 0) {
                val temp = end
                end = start
                start = temp
            } else if (flag == 0) {
                map.put(start, 0)
                return map
            }

            val diff = DateTime.cal_daysBetween(start, end)
            val cal = DateTime.pars2Calender(start + " 00:00:00")
            map.put(start, 0)
            for (i in 0..diff - 1) {
                cal!!.add(Calendar.DATE, 1)
                map.put(DateTime.formatDate(cal), 0)
            }

            return map
        }

        /**
         * 求两个日期间隔的小时
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun cal_hoursBetween(startDate: Date, endDate: Date): Int {
            var dayNumber: Long = 0
            dayNumber = (endDate.time - startDate.time) / (60 * 1000)
            return dayNumber.toInt()
        }


        /**
         * 得到两个日期间的秒数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun cal_secBetween(startDate: Date, endDate: Date): Int {
            var dayNumber: Long = 0
            try {
                dayNumber = (endDate.time - startDate.time) / 1000
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dayNumber.toInt()
        }

        /**
         * 得到两个日期间的秒数
         * @param startDateStr
         * *
         * @param endDateStr
         * *
         * @return
         */
        fun cal_secBetween(startDate: String, endDate: String): Int {
            var dayNumber: Long = 0
            try {
                dayNumber = (pars2Calender(endDate)!!.time.time - pars2Calender(startDate)!!.time.time) / 1000
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return dayNumber.toInt()
        }


        /**
         * TODO 得到天数数组（没测试）
         * @param startTime
         * *
         * @param deadline
         * *
         * @return new int[]{tatal(起始),use(到目前已过天数)}
         */
        fun getDayBetween(startTime: String?, deadline: String?): IntArray? {
            if (startTime == null || deadline == null) {
                return null
            }
            val cal = Calendar.getInstance()
            val today = cal.time
            val start = DateTime.pars2Calender(startTime)!!.time
            val dead = DateTime.pars2Calender(deadline)!!.time

            val use = DateTime.getBetweenDayNumber(start, today)
            val tatal = DateTime.getBetweenDayNumber(start, dead)
            return intArrayOf(tatal, use)
        }

        /**
         * @param
         */
        fun todayWeek(): String {
            val str = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")//字符串数组
            val rightNow = Calendar.getInstance()
            val day = rightNow.get(rightNow.DAY_OF_WEEK)//获取时间
            //		System.out.println("今天是"+str[day-1]);//通过数组把周几输出
            return str[day - 1]
        }
    }


}
/**
 * 默认构造方法
 */
