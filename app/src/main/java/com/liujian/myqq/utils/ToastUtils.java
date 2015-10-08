package com.liujian.myqq.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liujian on 15/10/7.
 */
public class ToastUtils {

    public static Toast toast ;

    public static void showShortToast(Context context, String content) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(Context context, String content) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showShortToast(Context context, int rid) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, context.getString(rid), Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showLongToast(Context context, int rid)  {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, context.getString(rid), Toast.LENGTH_LONG);
        toast.show();
    }
}
