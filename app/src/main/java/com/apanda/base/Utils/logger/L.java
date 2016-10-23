package com.apanda.base.Utils.logger;

/**
 * L is a wrapper of {@link android.util.Log}
 * But more pretty, simple and powerful
 */
public final class L {

    public static boolean isDebug = true;
    private static final String DEFAULT_TAG = "weicheche";
    private static Printer printer = new LoggerPrinter();

    //no instance
    private L() {
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in L as TAG
     */
    public static Settings init(String tag) {
        printer = new LoggerPrinter();
        return printer.init(tag);
    }

    public static void clear() {
        printer.clear();
        printer = null;
    }

    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(String message, Object... args) {
        if (isDebug)
            printer.d(message, args);
    }

    public static void e(String message, Object... args) {
        if (isDebug)
            printer.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        if (isDebug)
            printer.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
                    if (isDebug)
            printer.i(message, args);
    }

    public static void v(String message, Object... args) {
        if (isDebug)
            printer.v(message, args);
    }

    public static void w(String message, Object... args) {
        if (isDebug)
            printer.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        if (isDebug)
            printer.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        if (isDebug)
            printer.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        if (isDebug)
            printer.xml(xml);
    }

}
