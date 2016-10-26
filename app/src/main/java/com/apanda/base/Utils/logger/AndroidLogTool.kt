package com.apanda.base.Utils.logger

import android.util.Log

class AndroidLogTool : LogTool {
    override fun d(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun e(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun w(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun i(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun v(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun wtf(tag: String, message: String) {
        Log.wtf(tag, message)
    }
}
