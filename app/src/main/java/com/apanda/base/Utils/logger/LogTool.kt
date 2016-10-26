package com.apanda.base.Utils.logger

interface LogTool {
    fun d(tag: String, message: String)

    fun e(tag: String, message: String)

    fun w(tag: String, message: String)

    fun i(tag: String, message: String)

    fun v(tag: String, message: String)

    fun wtf(tag: String, message: String)
}