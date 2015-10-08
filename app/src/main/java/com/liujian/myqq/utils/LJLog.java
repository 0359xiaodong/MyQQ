package com.liujian.myqq.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by sh.liujian on 2015-9-29.
 */
public class LJLog {

    private static boolean logEnable = false;

    private static String customTagPrefix = "LJTAG";

    public static void setLJLogStatus(boolean enableLog) {
        logEnable = enableLog;
    }

    public static void setLJLogTag(String tag) {
        customTagPrefix = tag;
    }

    private static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = customTagPrefix.length() == 0 ? tag : customTagPrefix + " : " + tag;
        return tag;
    }

    public static void v(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.v(tag, content);
    }

    public static void d(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.d(tag, content);
    }

    public static void i(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.i(tag, content);
    }

    public static void w(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.w(tag, content);
    }

    public static void e(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.e(tag, content);
    }

    public static void wtf(String content) {
        if (!logEnable)
            return;
        String tag = generateTag(getCallerStackTraceElement());
        Log.wtf(tag, content);
    }
}
