package com.liujian.myqq.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.liujian.myqq.R;
import com.liujian.myqq.globel.GlobeConfig;

/**
 * Created by liujian on 15/10/6.
 */
public class LauncherActivity extends Activity implements View.OnClickListener {

    public final int ACTION_CHECK = 0, ACTION_DELAY = 1;

    @ViewInject(R.id.fl_btn_login)
    private Button btnLogin;
    @ViewInject(R.id.fl_btn_register)
    private Button btnRegister;
    @ViewInject(R.id.fl_llt_container)
    private LinearLayout lltContainer;
    @ViewInject(R.id.fl_ivw_back)
    private ImageView ivwBack;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_CHECK:
                    if (GlobeConfig.globeUser.isLogin()) {
                        sendEmptyMessageDelayed(ACTION_DELAY, 1000);
                    } else {
                        lltContainer.setVisibility(View.VISIBLE);
                        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_to_center);
                        Animation transAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_to_set);
                        lltContainer.startAnimation(transAnimation);
                        ivwBack.startAnimation(scaleAnimation);
                    }
                    break;
                case ACTION_DELAY:
                    Intent intent = new Intent(LauncherActivity.this, QQMainActivity.class);
                    startActivity(intent);
                    LauncherActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_launcher);
        ViewUtils.inject(this);
        initAction();
        handler.sendEmptyMessageDelayed(ACTION_CHECK, 800);
    }

    private void initAction() {
        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fl_btn_login:
                intent = new Intent(this, LoginActivity.class);
                this.finish();
                break;
            case R.id.fl_btn_register:
                intent = new Intent(this, RegisterActivity.class);
                break;
        }
        startActivity(intent);
    }
}
