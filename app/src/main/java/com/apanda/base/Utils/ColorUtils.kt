package com.apanda.base.Utils

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2015/8/4
 * 描    述：
 * 修订历史：
 * ================================================
 */

import android.graphics.Color

import java.util.Random

object ColorUtils {

    fun randomColor(): Int {
        val random = Random()
        val red = random.nextInt(150) + 50
        val green = random.nextInt(150) + 50
        val blue = random.nextInt(150) + 50
        return Color.rgb(red, green, blue)
    }
}
