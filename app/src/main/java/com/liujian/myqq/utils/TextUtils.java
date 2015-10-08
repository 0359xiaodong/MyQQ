package com.liujian.myqq.utils;

/**
 * Created by liujian on 15/10/7.
 */
public class TextUtils {

    public static boolean empty(String source) {
        if (source == null || source.trim().length() == 0) return true;
        return false;
    }
}
