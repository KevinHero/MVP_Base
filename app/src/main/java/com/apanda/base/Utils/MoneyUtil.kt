package com.apanda.base.Utils

import java.text.DecimalFormat

/**
 * des:金钱
 * Created by xsf
 * on 2016.06.11:48
 */
object MoneyUtil {
    fun MoneyFomatWithTwoPoint(d: Double): String {
        val df = DecimalFormat("#.##")
        return df.format(d)
    }
}
