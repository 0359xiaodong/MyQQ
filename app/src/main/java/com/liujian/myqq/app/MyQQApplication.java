package com.liujian.myqq.app;

import android.app.Application;

import com.liujian.myqq.R;
import com.liujian.myqq.utils.SoundUtil;

/**
 * Created by liujian on 15/10/6.
 */
public class MyQQApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SoundUtil.initSoundPool(this, R.raw.qrcode_completed);
    }
}
