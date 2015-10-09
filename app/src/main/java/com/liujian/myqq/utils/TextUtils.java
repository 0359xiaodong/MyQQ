package com.liujian.myqq.utils;

import android.widget.EditText;

/**
 * Created by liujian on 15/10/7.
 */
public class TextUtils {

    public static boolean empty(String source) {
        if (source == null || source.trim().length() == 0) return true;
        return false;
    }

    /**
     * 判断editText是否合法
     *
     * @param editText
     * @return 不为空true
     */
    public static boolean verifyText(EditText editText) {
        return empty(editText.getText().toString());
    }

    public static boolean verifyText(EditText... editTexts) {
        for (EditText edt : editTexts) {
            if (empty(edt.getText().toString())) {
                return false;
            }
        }
        return false;
    }

    public static boolean verifyText(EditText editText, int length) {
        return verifyText(editText.getText().toString(), length);
    }

    public static boolean verifyText(EditText editText, int min, int max) {
        return verifyText(editText.getText().toString(), min, max);
    }

    public static boolean verifyText(String content, int length) {
        return content.trim().length() == length;
    }

    public static boolean verifyText(String content, int min, int max) {
        return min <= content.trim().length() && content.trim().length() <= max;
    }
}
