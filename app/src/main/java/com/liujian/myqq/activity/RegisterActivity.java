package com.liujian.myqq.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.liujian.myqq.R;

/**
 * Created by liujian on 15/10/6.
 */
public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_login, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
    }
}
