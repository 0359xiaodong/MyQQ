package com.liujian.myqq.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * SharePreference常量类
 */

public class SharePreferenceUtil {
    private static final String TAG = "SharePreferenceUtil";
    public static final String projectKey = "prj_base";
    private static SharePreferenceUtil instance;
    private SharedPreferences settings;
    public static String ERROR_INFO = null;

    private SharePreferenceUtil() {

    }

    public static SharePreferenceUtil getInstance() {
        if (instance == null) {
            instance = new SharePreferenceUtil();
        }
        return instance;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(projectKey, Context.MODE_PRIVATE);
    }

    public String getStringValue(Context context, String key) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        if (key != null /*&& "uid".equals(key)*/) {
            return settings.getString(key, null);
        }
        return settings.getString(key, "");
    }

    public int getIntValue(Context context, String key) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        return settings.getInt(key, 0);
    }

    public void setStringValue(Context context, String key, String value) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        Editor editor = settings.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setIntValue(Context context, String key, int value) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public boolean getBooleanValue(Context context, String key) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        return settings.getBoolean(key, true);
    }

    public void setBooleanValue(Context context, String key, boolean isFirstLogin) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        Editor editor = settings.edit();
        editor.putBoolean(key, isFirstLogin);
        editor.commit();
    }

    public long getLongValue(Context context, String key) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        return settings.getLong(key, 0);
    }

    public void setLongValue(Context context, String key, long flag) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        Editor editor = settings.edit();
        editor.putLong(key, flag);
        editor.commit();
    }


    /**
     * SharedPreferences保存对象
     *
     * @param context
     * @param key
     * @param object
     */
    public void setObjectValue(Context context, String key, Object object) {
        if (settings == null)
            settings = getSharedPreferences(context);
        String objectBase64 = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            objectBase64 = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Editor editor = settings.edit();
        editor.putString(key, objectBase64);
        editor.commit();
    }

    /**
     * SharedPreferences取得对象
     *
     * @param context
     * @param key
     * @return
     */
    public Object getObjectValue(Context context, String key) {
        if (settings == null)
            settings = getSharedPreferences(context);
        Object object = null;
        try {
            String objectBase64 = settings.getString(key, "");
            if (TextUtils.isEmpty(objectBase64)) {
                throw new NullPointerException("get an empty string accroding key" + key);
            }
            byte[] base64Bytes = Base64.decode(objectBase64.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            object = ois.readObject();
        }
        catch (Exception e) {// 发生异常情况下清空对应缓存
            removeKey(context, key);
            ERROR_INFO = e.toString();
            // Log.e(TAG, e.toString());
        }
        return object;
    }

    public void removeKey(Context context, String key) {
        if (settings == null) {
            settings = getSharedPreferences(context);
        }
        if (settings.contains(key)) {
            settings.edit().remove(key).commit();
        }

    }

    public void clearSharePreference() {
        settings.edit().clear().commit();
    }

}
