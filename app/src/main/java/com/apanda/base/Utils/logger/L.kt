package com.apanda.base.Utils.logger

/**
 * L is a wrapper of [android.util.Log]
 * But more pretty, simple and powerful
 */
object L {

    var isDebug = true
    private val DEFAULT_TAG = "weicheche"
    private var printer: Printer? = LoggerPrinter()

    /**
     * It is used to change the tag

     * @param tag is the given string which will be used in L as TAG
     */
    @JvmOverloads fun init(tag: String = DEFAULT_TAG): Settings {
        printer = LoggerPrinter()
        return printer!!.init(tag)
    }

    fun clear() {
        printer!!.clear()
        printer = null
    }

    fun t(tag: String): Printer {
        return printer!!.t(tag, printer!!.settings.methodCount)
    }

    fun t(methodCount: Int): Printer {
        return printer!!.t(null, methodCount)
    }

    fun t(tag: String, methodCount: Int): Printer {
        return printer!!.t(tag, methodCount)
    }

    fun d(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.d(message, *args)
    }

    fun e(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.e(null, message, *args)
    }

    fun e(throwable: Throwable, message: String, vararg args: Any) {
        if (isDebug)
            printer!!.e(throwable, message, *args)
    }

    fun i(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.i(message, *args)
    }

    fun v(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.v(message, *args)
    }

    fun w(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.w(message, *args)
    }

    fun wtf(message: String, vararg args: Any) {
        if (isDebug)
            printer!!.wtf(message, *args)
    }

    /**
     * Formats the json content and print it

     * @param json the json content
     */
    fun json(json: String) {
        if (isDebug)
            printer!!.json(json)
    }

    /**
     * Formats the json content and print it

     * @param xml the xml content
     */
    fun xml(xml: String) {
        if (isDebug)
            printer!!.xml(xml)
    }

}//no instance
/**
 * It is used to get the settings object in order to change settings

 * @return the settings object
 */
