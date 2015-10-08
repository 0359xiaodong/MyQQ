package com.liujian.myqq.activity;

import android.os.Bundle;
import android.view.View;

import com.lidroid.xutils.ViewUtils;
import com.liujian.myqq.R;

/**
 * Created by liujian on 15/10/8.
 */
public class WebViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_webview, mUIHelper.getContainer(), true);
        ViewUtils.inject(this, v);
        mUIHelper.setTitleBarVisible(false);
    }
}
