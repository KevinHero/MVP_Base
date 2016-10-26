package com.apanda.base.base.daynightmodeutils

import android.content.Context
import android.content.SharedPreferences

/**
 * 夜间模式辅助类
 */
object ChangeModeHelper {
    val MODE_DAY = 1

    val MODE_NIGHT = 2
    private val Mode = "mode"
    fun setChangeMode(ctx: Context, mode: Int) {
        val sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE)
        sp.edit().putInt(Mode, mode).commit()
    }

    fun getChangeMode(ctx: Context): Int {
        val sp = ctx.getSharedPreferences("config_mode", Context.MODE_PRIVATE)
        return sp.getInt(Mode, MODE_DAY)
    }
}
