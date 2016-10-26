package com.apanda.base.Utils.logger

class Settings {

    private var methodCount = 2
    var isShowThreadInfo = true
        private set
    private var methodOffset = 0
    private var logTool: LogTool? = null

    /**
     * Determines how logs will printed
     */
    private var logLevel = LogLevel.FULL

    fun hideThreadInfo(): Settings {
        isShowThreadInfo = false
        return this
    }

    /**
     * Use [.methodCount]
     */
    @Deprecated("")
    fun setMethodCount(methodCount: Int): Settings {
        return methodCount(methodCount)
    }

    fun methodCount(methodCount: Int): Settings {
        var methodCount = methodCount
        if (methodCount < 0) {
            methodCount = 0
        }
        this.methodCount = methodCount
        return this
    }

    /**
     * Use [.logLevel]
     */
    @Deprecated("")
    fun setLogLevel(logLevel: LogLevel): Settings {
        return logLevel(logLevel)
    }

    fun logLevel(logLevel: LogLevel): Settings {
        this.logLevel = logLevel
        return this
    }

    /**
     * Use [.methodOffset]
     */
    @Deprecated("")
    fun setMethodOffset(offset: Int): Settings {
        return methodOffset(offset)
    }

    fun methodOffset(offset: Int): Settings {
        this.methodOffset = offset
        return this
    }

    fun logTool(logTool: LogTool): Settings {
        this.logTool = logTool
        return this
    }

    fun getMethodCount(): Int {
        return methodCount
    }

    fun getLogLevel(): LogLevel {
        return logLevel
    }

    fun getMethodOffset(): Int {
        return methodOffset
    }

    fun getLogTool(): LogTool {
        if (logTool == null) {
            logTool = AndroidLogTool()
        }
        return logTool
    }
}
