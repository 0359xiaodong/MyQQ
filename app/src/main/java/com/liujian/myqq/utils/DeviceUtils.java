package com.liujian.myqq.utils;

import android.app.Activity;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by liujian on 15/10/11.
 */
public class DeviceUtils {

    private static String deviceId = null;

    public static void hideSoftInputKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),
                    0);
        }
    }

    public static String getDeviceId(Context context) {
        if (deviceId == null) {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = TelephonyMgr.getDeviceId();
        }
        return deviceId;
    }
}
