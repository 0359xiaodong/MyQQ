package com.liujian.myqq.app;

import android.app.Application;

import com.liujian.myqq.R;
import com.liujian.myqq.data.User;
import com.liujian.myqq.globel.Constant;
import com.liujian.myqq.globel.GlobeConfig;
import com.liujian.myqq.utils.DeviceUtils;
import com.liujian.myqq.utils.LJLog;
import com.liujian.myqq.utils.SharePreferenceUtil;
import com.liujian.myqq.utils.SoundUtil;

/**
 * Created by liujian on 15/10/6.
 */
public class MyQQApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LJLog.setLJLogStatus(true);
        GlobeConfig.device_id = DeviceUtils.getDeviceId(this);
        SoundUtil.initSoundPool(this, R.raw.qrcode_completed);

        restoreUserInfo();
    }

    private void restoreUserInfo() {
        try {
            User user = (User) SharePreferenceUtil.getInstance().getObjectValue(getApplicationContext(), Constant.KEY_USER_INFO);
            GlobeConfig.globeUser.parseUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
